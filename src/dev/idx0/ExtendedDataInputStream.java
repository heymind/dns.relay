package dev.idx0;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ExtendedDataInputStream extends DataInputStream {

    public ExtendedDataInputStream(byte[] buf) {
        super(new ByteArrayInputStream(buf));
    }

    private String readDomainName(String domainName) throws IOException {
        try {
            int partLength = this.readUnsignedByte();
            if (partLength < 0) return domainName;
            byte[] part = new byte[partLength];
            int readLength = this.read(part);
            assert partLength == readLength;
            return this.readDomainName((domainName == null ? "" : domainName + ".") + new String(part));
        } catch  (AssertionError ex) {

            throw new IOException("[ExtendedDataInputStream.IOException] Wrong package format.",ex);
        }

    }

    public String readDomainName() throws IOException{
        return this.readDomainName(null);
    }

}
