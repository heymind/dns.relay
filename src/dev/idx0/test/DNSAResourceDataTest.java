package dev.idx0.test;

import dev.idx0.DNSAResourceData;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DNSAResourceDataTest {
    byte buf[] = {(byte) 0x17, (byte) 0xfb, (byte) 0x79, (byte) 0x86};

    @org.junit.jupiter.api.Test
    void toByteArray() {
        assertEquals(Arrays.toString(new DNSAResourceData(402356614L).toByteArray()), Arrays.toString(buf));
    }

    @Test
    void getIp() {
        assertEquals((new DNSAResourceData("192.168.109.49")).getIp(), 3232263473L);

        assertEquals((new DNSAResourceData(buf.clone())).getIp(), 402356614L);
    }


    @Test
    void _toString() {
        assertEquals((new DNSAResourceData("192.168.109.49")).toString(), "192.168.109.49");
    }
}
