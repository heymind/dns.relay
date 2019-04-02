package dev.idx0;

import java.io.IOException;

public class DNSMessageResourceRecord {
    private String Name;

    /**
     * Set the value of QuestionType
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setQuestionType(int questionType) {
        QuestionType = questionType;
        return this;
    }

    private int QuestionType;

    public DNSMessageResourceRecord(DNSMessageResourceRecord other) {
        this.Name = other.Name;
        this.QuestionType = other.QuestionType;
        this.QuestionClass = other.QuestionClass;
        this.TimeToLive = other.TimeToLive;
        this.ResourceDataLength = other.ResourceDataLength;
        this.ResourceData = other.ResourceData;
    }

    /**
     * Gets Name
     *
     * @return value of Name
     */
    public String getName() {
        return Name;
    }

    /**
     * Set the value of Name
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setName(String name) {
        Name = name;
        return this;
    }

    /**
     * Gets TimeToLive
     *
     * @return value of TimeToLive
     */
    public long getTimeToLive() {
        return TimeToLive;
    }

    /**
     * Set the value of TimeToLive
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setTimeToLive(long timeToLive) {
        TimeToLive = timeToLive;
        return this;
    }

    /**
     * Gets ResourceDataLength
     *
     * @return value of ResourceDataLength
     */
    public int getResourceDataLength() {
        return this.ResourceData.length;
    }


    /**
     * Gets ResourceData
     *
     * @return value of ResourceData
     */
    public byte[] getResourceData() {
        return ResourceData;
    }

    /**
     * Set the value of ResourceData
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setResourceData(byte[] resourceData) {
        ResourceData = resourceData;
        return this;
    }

    /**
     * Gets QuestionClass
     *
     * @return value of QuestionClass
     */
    public int getQuestionClass() {
        return QuestionClass;
    }

    /**
     * Set the value of QuestionClass
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setQuestionClass(int questionClass) {
        QuestionClass = questionClass;
        return this;
    }

    private int QuestionClass;
    private long TimeToLive;
    private int ResourceDataLength;
    private byte[] ResourceData;

    public DNSMessageResourceRecord(ExtendedDataInputStream in) throws IOException {
        this
                .setName(in.readDomainName())
                .setQuestionType((in.readUnsignedShort()))
                .setQuestionClass((in.readUnsignedShort()))
                .setTimeToLive(in.readInt() & 0xFFFF_FFFFL);
        this.ResourceDataLength = in.readUnsignedShort();
        ResourceData = new byte[ResourceDataLength];
        in.read(ResourceData);
    }

    public void serializeToStream(ExtendedDataOutputStream out) throws IOException {
        out.writeDomainName(this.getName());
        out.writeShort((short) this.getQuestionType());
        out.writeShort((short) this.getQuestionClass());
        out.writeInt((int) this.getTimeToLive());
        out.writeShort((short) this.getResourceDataLength());
        out.write(this.getResourceData());
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        serializeToStream(out);
        return out.toByteArray();
    }

    /**
     * Gets QuestionType
     *
     * @return value of QuestionType
     */
    public int getQuestionType() {
        return QuestionType;
    }
}
