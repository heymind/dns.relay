package dev.idx0;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DNSPackage {
    private InetSocketAddress addr;
    private DNSMessage msg;

    DNSPackage(InetSocketAddress addr,DNSMessage msg){
        this.addr = addr;
        this.msg = msg;
    }
    DNSPackage(InetSocketAddress addr,byte[] buf) throws IOException {
        this(addr,new DNSMessage(buf));
    }

    /**
     * Gets addr
     *
     * @return value of addr
     */
    public InetSocketAddress getAddr() {
        return addr;
    }

    /**
     * Set the value of addr
     *
     * @return DNSPackage
     */
    public DNSPackage setAddr(InetSocketAddress addr) {
        this.addr = addr;
        return this;
    }

    /**
     * Gets msg
     *
     * @return value of msg
     */
    public DNSMessage getMsg() {
        return msg;
    }

    /**
     * Set the value of msg
     *
     * @return DNSPackage
     */
    public DNSPackage setMsg(DNSMessage msg) {
        this.msg = msg;
        return this;
    }
}
