package dev.idx0;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log
public class Server {
    private InetSocketAddress listen, upstream;
    private Relay relay;
    protected Cache cache = new Cache();
    private Hosts hosts;

    Server(InetSocketAddress listen, InetSocketAddress upstream, Hosts hosts) {
        this.listen = listen;
        this.upstream = upstream;
        this.relay = new Relay(upstream);
        this.hosts = hosts;
    }

    List<DNSMessageResourceRecord> lookUpHosts(DNSQuestion question) {
        if (question.getQuestionType() == DNSQuestion.QUESTION_TYPE_A) {
            log.fine("本次查询只有一个Question,且 Question Type 是 A，尝试从hosts中寻找。");
            String ipAddress = hosts.get(question.getName());
            if (ipAddress != null) {
                log.info("从hosts里找到了！");
                DNSMessageResourceRecord rr = new DNSMessageResourceRecord();
                DNSAResourceData dnsaResourceData = new DNSAResourceData(ipAddress);

                rr.setQuestionClass(question.getQuestionClass());
                rr.setName(question.getName());
                rr.setQuestionType(question.getQuestionType());
                rr.setResourceData(dnsaResourceData.toByteArray());

                return Collections.singletonList(rr);
            } else {
                log.fine("无法从hosts里找，放弃本地解析。");
            }
        } else {
            log.fine("本次查询只有一个Question,但 Question Type 不是 A，放弃本地解析。");
        }
        return Collections.emptyList();
    }

    private DNSMessage handleQueryRequest(DNSMessage message) {
        log.info(String.format("ID:%d ~ %s",message.getHeader().getID(), message.getHeader()));
        for (DNSQuestion question : message.getQuestions()) {
            log.info(String.format("Query: %s - %s", question.getQuestionTypeName(), question.getName()));
        }
        if (message.getQuestions().size() == 1) {
            log.fine("本次查询只有一个Question,尝试本地解析...");
            List<DNSMessageResourceRecord> RRs = lookUpHosts(message.getQuestions().get(0));
            if(RRs.isEmpty()){
                log.fine("尝试查询缓存");
                RRs = cache.get(message.getQuestions().get(0));
            }
            if (!RRs.isEmpty()) {
                DNSMessage responseMessage = new DNSMessage();
                DNSHeader header = new DNSHeader();
                header.setQueryFlag(false)
                        .setID(message.getHeader().getID())
                        .setOPCode(DNSHeader.OPCODE_QUERY)
                        .setResponseCode(DNSHeader.RCODE_NO_ERROR)
                        .setQuestionCount(1)
                        .setAnswerRecordCount(RRs.size());
                responseMessage.setHeader(header);
                responseMessage.setQuestions(message.getQuestions());
                responseMessage.setAnswerRecords(RRs);
                return responseMessage;
            }



        }
        return null;
    }


    DatagramPacket handleRequest(DatagramPacket request) throws IOException {
        DNSPackage dnsPackage = new DNSPackage(
                request.getAddress(),
                request.getPort(),
                request.getData()
        );
        DNSMessage message = dnsPackage.getMsg();
        if (message.getHeader().isQueryFlag()) {
            log.info(String.format("收到一条来自 %s:%d 的查询", request.getAddress().toString(), request.getPort()));
            DNSMessage responseMessage = handleQueryRequest(message);
            if (responseMessage != null) {
                byte[] data = responseMessage.toByteArray();
                return new DatagramPacket(data, data.length, dnsPackage.getAddr().getAddress(), dnsPackage.getAddr().getPort());
            }

            log.info("准备发往upstream查询。");

        } else {
            log.info("收到upstream返回的响应，原路返回。");
            dnsPackage.getMsg().getAnswerRecords().forEach(rr -> cache.set(rr));
        }
        return relay.handle(dnsPackage).toDatagramPacket();

    }

    public void start() {

        try (DatagramSocket socket = new DatagramSocket(listen.getPort())) {
            while (true) {
                DatagramPacket response = relay.handleTimeoutQuery();
                if(response != null){
                    socket.send(response);
                }
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(request);


                    response = handleRequest(request);
                    socket.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
