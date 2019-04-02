package dev.idx0.test;

import dev.idx0.DNSMessageResourceRecord;
import dev.idx0.DNSQuestion;
import dev.idx0.DNSQuestionClass;
import dev.idx0.DNSQuestionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DNSMessageResourceRecordTest {
    private byte[] bytes = {
            (byte)0xc0, (byte)0x0c, (byte)0x00, (byte)0x05, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00,
            (byte)0x6a, (byte)0x86, (byte)0x00, (byte)0x26, (byte)0x02, (byte)0x33, (byte)0x37, (byte)0x12,
            (byte)0x63, (byte)0x6f, (byte)0x75, (byte)0x72, (byte)0x69, (byte)0x65, (byte)0x72, (byte)0x2d,
            (byte)0x70, (byte)0x75, (byte)0x73, (byte)0x68, (byte)0x2d, (byte)0x61, (byte)0x70, (byte)0x70,
            (byte)0x6c, (byte)0x65, (byte)0x03, (byte)0x63, (byte)0x6f, (byte)0x6d, (byte)0x06, (byte)0x61,
            (byte)0x6b, (byte)0x61, (byte)0x64, (byte)0x6e, (byte)0x73, (byte)0x03, (byte)0x6e, (byte)0x65,
            (byte)0x74, (byte)0x00
    };
    void serializer() throws IOException {


        DNSQuestion q = (new DNSQuestion()).setName("37-courier.push.apple.com").setQuestionClass(DNSQuestionClass.IN)
                .setQuestionType(DNSQuestionType.A);

        assertEquals(Arrays.toString(q.toByteArray()),Arrays.toString(bytes));
    }

    @Test
    void deserializer() throws IOException{
//        DNSMessageResourceRecord rr = new DNSMessageResourceRecord(bytes);
//        assertEquals(rr.getName());
//        assertEquals(rr.getQuestionClass());
    }


}