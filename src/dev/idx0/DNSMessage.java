package dev.idx0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DNSMessage {
    private DNSHeader header;
    private List<DNSQuestion> questions;
    private List<DNSMessageResourceRecord> answerRecords;
    private List<DNSMessageResourceRecord> otherRecords;

    public DNSMessage(DNSMessage other) {
        this.header = other.header;
        this.questions = other.questions;
        this.answerRecords = other.answerRecords;
        this.otherRecords = other.otherRecords;
    }

    public boolean isAsk(){
        return header.getAnswerRecordCount() == 0;
    }
    /**
     * Gets header
     *
     * @return value of header
     */
    public DNSHeader getHeader() {
        return header;
    }

    /**
     * Set the value of header
     *
     * @return DNSMessage
     */
    public DNSMessage setHeader(DNSHeader header) {
        this.header = header;
        return this;
    }

    /**
     * Gets questions
     *
     * @return value of questions
     */
    public List<DNSQuestion> getQuestions() {
        return questions;
    }

    /**
     * Set the value of questions
     *
     * @return DNSMessage
     */
    public DNSMessage setQuestions(List<DNSQuestion> questions) {
        this.questions = questions;
        return this;
    }

    /**
     * Gets answerRecords
     *
     * @return value of answerRecords
     */
    public List<DNSMessageResourceRecord> getAnswerRecords() {
        return answerRecords;
    }

    /**
     * Set the value of answerRecords
     *
     * @return DNSMessage
     */
    public DNSMessage setAnswerRecords(List<DNSMessageResourceRecord> answerRecords) {
        this.answerRecords = answerRecords;
        return this;
    }

    /**
     * Gets otherRecords
     *
     * @return value of otherRecords
     */
    public List<DNSMessageResourceRecord> getOtherRecords() {
        return otherRecords;
    }

    /**
     * Set the value of otherRecords
     *
     * @return DNSMessage
     */
    public DNSMessage setOtherRecords(List<DNSMessageResourceRecord> otherRecords) {
        this.otherRecords = otherRecords;
        return this;
    }

    DNSMessage(byte[] buffer) throws IOException {
        ExtendedDataInputStream inp = new ExtendedDataInputStream(buffer);
        setHeader(new DNSHeader(inp));
        questions = new ArrayList<DNSQuestion>();
        for (int i = 0; i < header.getQuestionCount(); i++) {
            questions.add(new DNSQuestion(inp));
        }
        answerRecords = new ArrayList<DNSMessageResourceRecord>();
        otherRecords = new ArrayList<DNSMessageResourceRecord>();
        for (int i = 0; i < header.getAnswerRecordCount(); i++) {
            answerRecords.add(new DNSMessageResourceRecord(inp));
        }

        int otherRecordCount = header.getAuthorityRecordCount() + header.getAdditionalRecordCount();
        for (int i = 0; i < otherRecordCount; i++) {
            otherRecords.add(new DNSMessageResourceRecord(inp));
        }
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        header.serializeToStream(out);
        for (DNSQuestion question:questions) {
            question.serializeToStream(out);
        }
        for (DNSMessageResourceRecord rr:answerRecords){
            rr.serializeToStream(out);
        }
        for (DNSMessageResourceRecord rr:otherRecords){
            rr.serializeToStream(out);
        }
        return out.toByteArray();
    }
}
