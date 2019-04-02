package dev.idx0;


import java.io.IOException;

public class DNSQuestion {


    private String Name;
    private int QuestionType;

    public DNSQuestion(DNSQuestion other) {
        this.Name = other.Name;
        this.QuestionType = other.QuestionType;
        this.QuestionClass = other.QuestionClass;
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
     * @return DNSQuestion
     */
    public DNSQuestion setName(String name) {
        this.Name = name;
        return this;
    }


    private int QuestionClass;

    public DNSQuestion() {
    }

    public DNSQuestion(ExtendedDataInputStream in) throws IOException {

        this
                .setName(in.readDomainName())
                .setQuestionType((in.readUnsignedShort()))
                .setQuestionClass((in.readUnsignedShort()));
    }

    public void serializeToStream(ExtendedDataOutputStream out) throws IOException {
        out.writeDomainName(this.getName());
        out.writeShort((short) this.getQuestionType());
        out.writeShort((short) this.getQuestionClass());
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

    /**
     * Set the value of QuestionType
     *
     * @return DNSQuestion
     */
    public DNSQuestion setQuestionType(int questionType) {
        QuestionType = questionType;
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
     * @return DNSQuestion
     */
    public DNSQuestion setQuestionClass(int questionClass) {
        QuestionClass = questionClass;
        return this;
    }
}
