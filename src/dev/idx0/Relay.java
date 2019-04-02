package dev.idx0;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class IDAndAddr{
    private int ID;
    private InetSocketAddress addr;

    public IDAndAddr(int ID, InetSocketAddress addr) {
        this.ID = ID;
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDAndAddr idAndAddr = (IDAndAddr) o;
        return ID == idAndAddr.ID &&
                Objects.equals(addr, idAndAddr.addr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, addr);
    }

    /**
     * Gets ID
     *
     * @return value of ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the value of ID
     *
     * @return IDAndAddr
     */
    public IDAndAddr setID(int ID) {
        this.ID = ID;
        return this;
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
     * @return IDAndAddr
     */
    public IDAndAddr setAddr(InetSocketAddress addr) {
        this.addr = addr;
        return this;
    }
}
public class Relay {
    private InetSocketAddress upstream;
    private int IDGeneratorCurrent = 0;
    private Map<Integer, IDAndAddr> sources = new HashMap<>();
    Relay(InetSocketAddress upstream){
        this.upstream =upstream;
    }
    protected int makeUniqueID(){
        return IDGeneratorCurrent++;
    }
    public DNSPackage handle(DNSPackage pack){
        if(pack.getAddr().equals(upstream)){
            DNSMessage msg = pack.getMsg();
            IDAndAddr source = sources.get(msg.getHeader().getID());
            msg.getHeader().setID(source.getID());
            return new DNSPackage(source.getAddr(),msg);
        } else {
            int id = makeUniqueID();
            DNSMessage msg = pack.getMsg();
            sources.put(id,new IDAndAddr(msg.getHeader().getID(),pack.getAddr()));
            msg.getHeader().setID(id);
            return new DNSPackage(upstream,msg);
        }

    }
}
