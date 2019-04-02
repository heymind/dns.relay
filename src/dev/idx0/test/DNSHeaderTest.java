package dev.idx0.test;

import dev.idx0.DNSHeader;
import dev.idx0.ExtendedDataInputStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DNSHeaderTest {

    @Test
    void serializer() throws IOException{
        byte[] bytes = {
                (byte) 0xb1, (byte) 0x3b, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
        };

        DNSHeader header = new DNSHeader();
        header.setID(0xb13b);
        header.setQueryFlag(true);
        header.setRecursionDesiredFlag(true);
        header.setOPCode(DNSHeader.OPCODE.QUERY);
        header.setResponseCode(DNSHeader.RCODE.NoError);
        header.setQuestionCount(1);

        assertEquals(Arrays.toString(header.toByteArray()),Arrays.toString(bytes));
    }

    @Test
    void deserializer() throws IOException{
        byte[] bytes = {
                (byte) 0x27, (byte) 0x7c, (byte) 0x81, (byte) 0x80, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x03,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x63, (byte) 0x64, (byte) 0x6e,
                (byte) 0x04, (byte) 0x76, (byte) 0x32, (byte) 0x65, (byte) 0x78, (byte) 0x03, (byte) 0x63, (byte) 0x6f,
                (byte) 0x6d, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0xc0, (byte) 0x0c,
                (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0xd5,
                (byte) 0x00, (byte) 0x09, (byte) 0x06, (byte) 0x67, (byte) 0x6c, (byte) 0x6f, (byte) 0x62, (byte) 0x61,
                (byte) 0x6c, (byte) 0xc0, (byte) 0x10, (byte) 0xc0, (byte) 0x2a, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x04, (byte) 0x3a,
                (byte) 0xdf, (byte) 0xa8, (byte) 0x38, (byte) 0xc0, (byte) 0x2a, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x04, (byte) 0x3a,
                (byte) 0xdf, (byte) 0xa8, (byte) 0x37
        };
        DNSHeader header = new DNSHeader(new ExtendedDataInputStream(bytes));
        assertEquals(header.getID(),0x277c);
        assertEquals(header.getOPCode(), DNSHeader.OPCODE.QUERY);
        assertEquals(header.isQueryFlag(),false);
        assertEquals(header.isAuthoritativeAnswerFlag(),false);
        assertEquals(header.isTruncationFlag(),false);
        assertEquals(header.isRecursionAvailableFlag(),true);
        assertEquals(header.isRecursionDesiredFlag(),true);
        assertEquals(header.getResponseCode(), DNSHeader.RCODE.NoError);
        assertEquals(header.getQuestionCount(),1);
        assertEquals(header.getAnswerRecordCount(),3);
        assertEquals(header.getAdditionalRecordCount(),0);
        assertEquals(header.getAuthorityRecordCount(),0);
    }
}