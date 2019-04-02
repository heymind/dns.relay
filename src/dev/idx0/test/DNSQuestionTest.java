package dev.idx0.test;

import dev.idx0.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DNSQuestionTest {
    @Test
    void serializer() throws IOException {
        byte[] bytes = {
                (byte)0x0a, (byte)0x33, (byte)0x37, (byte)0x2d, (byte)0x63, (byte)0x6f, (byte)0x75, (byte)0x72,
                (byte)0x69, (byte)0x65, (byte)0x72, (byte)0x04, (byte)0x70, (byte)0x75, (byte)0x73, (byte)0x68,
                (byte)0x05, (byte)0x61, (byte)0x70, (byte)0x70, (byte)0x6c, (byte)0x65, (byte)0x03, (byte)0x63,
                (byte)0x6f, (byte)0x6d, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01
        };

       DNSQuestion q = (new DNSQuestion()).setName("37-courier.push.apple.com").setQuestionClass(DNSQuestionClass.IN)
               .setQuestionType(DNSQuestionType.A);

        assertEquals(Arrays.toString(q.toByteArray()),Arrays.toString(bytes));
    }

    @Test
    void deserializer() throws IOException{
        byte[] bytes = {
                (byte)0x0a, (byte)0x33, (byte)0x37, (byte)0x2d, (byte)0x63, (byte)0x6f, (byte)0x75, (byte)0x72,
                (byte)0x69, (byte)0x65, (byte)0x72, (byte)0x04, (byte)0x70, (byte)0x75, (byte)0x73, (byte)0x68,
                (byte)0x05, (byte)0x61, (byte)0x70, (byte)0x70, (byte)0x6c, (byte)0x65, (byte)0x03, (byte)0x63,
                (byte)0x6f, (byte)0x6d, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01
        };
        DNSQuestion q = new DNSQuestion(new ExtendedDataInputStream(bytes));
        assertEquals(q.getName(),"37-courier.push.apple.com");
        assertEquals(q.getQuestionClass(), DNSQuestionClass.IN);
        assertEquals(q.getQuestionType(), DNSQuestionType.A);
    }

}