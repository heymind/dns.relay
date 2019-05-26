package dev.idx0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.*;

@RequiredArgsConstructor
@Log
class Relay {
    private final InetSocketAddress upstream;
    private int IDGeneratorCurrent = 0;
    private Map<Integer, IDAndAddr> sources = new HashMap<>();
    private Timer timer = new Timer();
    private Queue<DNSPackage> timeoutQueryResponses = new ArrayDeque<>();

    private int makeUniqueID() {
        if (IDGeneratorCurrent == Integer.MAX_VALUE) {
            IDGeneratorCurrent = -1;
        }
        return IDGeneratorCurrent++;

    }

    DatagramPacket handleTimeoutQuery() throws IOException {
        DNSPackage response;
        synchronized (timeoutQueryResponses) {
            response = timeoutQueryResponses.poll();
        }
        if (response != null) {

            return response.toDatagramPacket();
        }
        return null;
    }

    DNSPackage handle(DNSPackage pack) {
        if (pack.getAddr().equals(upstream)) {
            DNSMessage msg = pack.getMsg();
            IDAndAddr source = sources.get(msg.getHeader().getID());
            sources.remove(msg.getHeader().getID());

            msg.getHeader().setID(source.getID());
            return new DNSPackage(source.getAddr(), msg);
        } else {
            int id = makeUniqueID();
            DNSMessage msg = pack.getMsg();
            IDAndAddr idAndAddr = new IDAndAddr(msg.getHeader().getID(), pack.getAddr());
            sources.put(id, idAndAddr);
            msg.getHeader().setID(id);

            timer.schedule(new TimerTask() {
                public void run() {

                    if (sources.get(id) != null) {
                        log.warning(String.format("%s:%d 的查询超时了 ID = %d",
                                idAndAddr.getAddr().getAddress(),
                                idAndAddr.getAddr().getPort(),
                                idAndAddr.getID()));
                        DNSMessage dnsMessage = new DNSMessage();
                        DNSHeader dnsHeader = new DNSHeader();
                        dnsHeader
                                .setID(idAndAddr.getID())
                                .setOPCode(DNSHeader.OPCODE_QUERY)
                                .setResponseCode(DNSHeader.RCODE_FORMAT_REFUSED)
                                .setTruncationFlag(true)
                                .setQueryFlag(false)
                                .setQuestionCount(1);
                        dnsMessage.setHeader(dnsHeader);
                        dnsMessage.setQuestions(msg.getQuestions());
                        synchronized (timeoutQueryResponses) {
                            timeoutQueryResponses.add(new DNSPackage(pack.getAddr(), dnsMessage));
                        }

                    }
                }
            }, 5000);
            return new DNSPackage(upstream, msg);
        }

    }

    @Data
    @AllArgsConstructor
    static private class IDAndAddr {
        private final int ID;
        private final InetSocketAddress addr;
    }
}
