package dev.idx0;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;


@Accessors(chain = true)
@Data
public class DNSMessage {
    private DNSHeader header;
    private List<DNSQuestion> questions;
    private List<DNSMessageResourceRecord> answerRecords;
    private List<DNSMessageResourceRecord> otherRecords;

    DNSMessage() {
    }

    DNSMessage(byte[] buffer) throws IOException {
        ExtendedDataInputStream inp = new ExtendedDataInputStream(buffer);
        setHeader(new DNSHeader(inp));
        questions = new ArrayList<>();
        for (int i = 0; i < header.getQuestionCount(); i++) {
            questions.add(new DNSQuestion(inp));
        }
        answerRecords = new ArrayList<>();
        otherRecords = new ArrayList<>();
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
        if (questions != null)  for (DNSQuestion question : questions) {
            question.serializeToStream(out);
        }
        if (answerRecords != null) for (DNSMessageResourceRecord rr : answerRecords) {
            rr.serializeToStream(out);
        }
        if (otherRecords != null) for (DNSMessageResourceRecord rr : otherRecords) {
            rr.serializeToStream(out);
        }
        return out.toByteArray();
    }


}
