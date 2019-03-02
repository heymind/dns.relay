package dev.idx0;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ExtendedDataOutputStream extends DataOutputStream {

    private ByteArrayOutputStream byteArrayOutputStream;

    private ExtendedDataOutputStream(ByteArrayOutputStream stream){
        super(stream);
        this.byteArrayOutputStream = stream;
    }
    public ExtendedDataOutputStream() {
        this(new ByteArrayOutputStream(96));
    }
    public void writeDomainName(String domainName) throws IOException {
        for (String part : domainName.split("\\.")) {
            this.writeByte(part.length());
            this.writeChars(part);
        }
    }
    public  byte[] toByteArray() {
        return this.byteArrayOutputStream.toByteArray();
    }
}
