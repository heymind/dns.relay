package dev.idx0;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;

public class Server {
    private InetSocketAddress listen, upstream;
    private Relay relay;
    protected Cache cache;

    Server(InetSocketAddress listen, InetSocketAddress upstream, Cache cache) {
        this.listen = listen;
        this.upstream = upstream;
        this.relay = new Relay(upstream);
        this.cache = cache;
    }

    public void start() {
        DNSPackage rev_pack, pack;
        try (DatagramSocket socket = new DatagramSocket(listen.getPort())) {
            while (true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(request);
                    rev_pack = new DNSPackage(new InetSocketAddress(request.getAddress(), request.getPort()), request.getData());
                    pack = null;
                    // Cache only handle the query that only has one question !
                    DNSMessage msg = rev_pack.getMsg();
                    if (msg.getHeader().isQueryFlag()) {
                        if (msg.getHeader().getQuestionCount() == 1) {
                            List<DNSMessageResourceRecord> RRs = cache.get(msg.getQuestions().get(0));
                            if (RRs.size() > 0) {
                                // cache hit
                                DNSHeader header = new DNSHeader();
                                header
                                        .setQueryFlag(false)
                                        .setID(msg.getHeader().getID())
                                        .setOPCode(DNSHeader.OPCODE.QUERY)
                                        .setResponseCode(DNSHeader.RCODE.NoError)
                                        .setQuestionCount(1)
                                        .setAnswerRecordCount(RRs.size());


                            } // else cache miss, relay this query.
                        }
                        // else relay this query
                    } else {
                        for (DNSMessageResourceRecord rr : rev_pack.getMsg().getAnswerRecords()) {
                            cache.set(rr);
                        }
                    }
                    if (pack == null) pack = relay.handle(rev_pack);


                    byte[] data = pack.getMsg().toByteArray();
                    DatagramPacket response = new DatagramPacket(data, data.length, pack.getAddr().getAddress(), pack.getAddr().getPort());
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
