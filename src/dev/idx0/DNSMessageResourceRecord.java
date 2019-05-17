package dev.idx0;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;

@Data
@Accessors(chain = true)
public class DNSMessageResourceRecord {
    private String Name;
    private int QuestionType;
    private int QuestionClass;
    private long TimeToLive;
    private int ResourceDataLength;
    private byte[] ResourceData;



    public DNSMessageResourceRecord setResourceData(byte[] resourceData) {
        ResourceData = resourceData;
        ResourceDataLength = resourceData.length;
        return this;
    }





    DNSMessageResourceRecord(){}
    DNSMessageResourceRecord(ExtendedDataInputStream in) throws IOException {

        setName(in.readDomainName());
        setQuestionType((in.readUnsignedShort()));
        setQuestionClass((in.readUnsignedShort()));
        setTimeToLive(in.readInt() & 0xFFFF_FFFFL);
        ResourceDataLength = in.readUnsignedShort();
        ResourceData = new byte[ResourceDataLength];
        in.read(ResourceData);
    }

    void serializeToStream(ExtendedDataOutputStream out) throws IOException {
        out.writeDomainName(getName());
        out.writeShort((short) getQuestionType());
        out.writeShort((short) getQuestionClass());
        out.writeInt((int) getTimeToLive());
        out.writeShort((short) getResourceDataLength());
        out.write(getResourceData());
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        serializeToStream(out);
        return out.toByteArray();
    }

}
