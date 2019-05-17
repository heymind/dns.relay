package dev.idx0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;


@Data
@Accessors(chain = true)
@AllArgsConstructor
class DNSPackage {
    private InetSocketAddress addr;
    private DNSMessage msg;


    DNSPackage(InetAddress host, int port, byte[] buf) throws IOException {
        this(new InetSocketAddress(host, port), new DNSMessage(buf));
    }

    public DatagramPacket toDatagramPacket() throws IOException {
        byte[] data = msg.toByteArray();
        return new DatagramPacket(data, data.length, getAddr().getAddress(), getAddr().getPort());
    }

}
