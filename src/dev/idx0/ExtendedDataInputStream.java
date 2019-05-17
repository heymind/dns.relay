package dev.idx0;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ExtendedDataInputStream extends DataInputStream {
    private Map<Integer, String> previousDomains = new HashMap<>();

    public ExtendedDataInputStream(byte[] buf) {
        super(new PositionByteArrayInputStream(buf));
    }

    private String readDomainName(String domainName) throws IOException {
        try {
            int partLength = this.readUnsignedByte();
            if (partLength >= 0b11000000) { // MAGIC~ 这是一个压缩的报文，后面跟着是个指针
                int point = ((partLength & 0b00111111) << 8) + this.readUnsignedByte();
                return this.previousDomains.get(point);

            }
            if (partLength == 0) return domainName;
            byte[] part = new byte[partLength];
            int readLength = this.read(part);
            assert partLength == readLength;
            return this.readDomainName((domainName == null ? "" : domainName + ".") + new String(part));
        } catch (AssertionError ex) {

            throw new IOException("[ExtendedDataInputStream.IOException] Wrong package format.", ex);
        }

    }

    String readDomainName() throws IOException {
        int pos = ((PositionByteArrayInputStream) (this.in)).getPosition();
        String domain = this.readDomainName(null);
        this.previousDomains.put(pos, domain);
        return domain;
    }

}
