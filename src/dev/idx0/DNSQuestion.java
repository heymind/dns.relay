package dev.idx0;


import java.io.IOException;

public class DNSQuestion {


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
     * @return DNSQuestion
     */
    public DNSQuestion setName(String name) {
        this.Name = name;
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
     * @return DNSQuestion
     */
    public DNSQuestion setQuestionType(DNSQuestionType questionType) {
        this.QuestionType = questionType;
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
     * @return DNSQuestion
     */
    public DNSQuestion setQuestionClass(DNSQuestionClass questionClass) {
        this.QuestionClass = questionClass;
        return this;
    }

    private DNSQuestionClass QuestionClass;

    public DNSQuestion(byte[] buf) throws IOException {
        ExtendedDataInputStream in = new ExtendedDataInputStream(buf);
        this
                .setName(in.readDomainName())
                .setQuestionType(DNSQuestionType.getByCode(in.readUnsignedShort()))
                .setQuestionClass(DNSQuestionClass.getByCode(in.readUnsignedShort()));
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        out.writeDomainName(this.getName());
        out.writeShort((short) this.getQuestionType().getType());
        out.writeShort((short) this.getQuestionClass().getQclass());
        return out.toByteArray();
    }
}
