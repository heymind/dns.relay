package dev.idx0;

import java.io.IOException;

public class DNSMessageResourceRecord {
    private String Name;
    private DNSQuestionType QuestionType;

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
     * Gets QuestionType
     *
     * @return value of QuestionType
     */
    public DNSQuestionType getQuestionType() {
        return QuestionType;
    }

    /**
     * Set the value of QuestionType
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setQuestionType(DNSQuestionType questionType) {
        QuestionType = questionType;
        return this;
    }

    /**
     * Gets QuestionClass
     *
     * @return value of QuestionClass
     */
    public DNSQuestionClass getQuestionClass() {
        return QuestionClass;
    }

    /**
     * Set the value of QuestionClass
     *
     * @return DNSMessageResourceRecord
     */
    public DNSMessageResourceRecord setQuestionClass(DNSQuestionClass questionClass) {
        QuestionClass = questionClass;
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

    private DNSQuestionClass QuestionClass;
    private long TimeToLive;
    private int ResourceDataLength;
    private byte[] ResourceData;

    public DNSMessageResourceRecord(byte[] buf) throws IOException {
        ExtendedDataInputStream in = new ExtendedDataInputStream(buf);
        this
                .setName(in.readDomainName())
                .setQuestionType(DNSQuestionType.getByCode(in.readUnsignedShort()))
                .setQuestionClass(DNSQuestionClass.getByCode(in.readUnsignedShort()))
                .setTimeToLive(in.readInt() & 0xFFFF_FFFFL);
        this.ResourceDataLength = in.readUnsignedShort();
        ResourceData = new byte[in.available()];
        in.read(ResourceData);
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        out.writeDomainName(this.getName());
        out.writeShort((short) this.getQuestionType().getType());
        out.writeShort((short) this.getQuestionClass().getQclass());
        out.writeInt((int) this.getTimeToLive());
        out.writeShort((short) this.getResourceDataLength());
        out.write(this.getResourceData());

        return out.toByteArray();
    }
}
