package dev.idx0;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class DNSAResourceData {
    private long ip = 0;


    public DNSAResourceData(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int tmp = Integer.parseInt(ipAddressInArray[i]);
            ip += tmp * Math.pow(256, power);
        }
    }

    public DNSAResourceData(byte[] rdata) {
        assert rdata.length == 4;
        ip = ((long) (rdata[0] & 255) << 24) + ((rdata[1] & 255) << 16) + ((rdata[2] & 255) << 8) + (rdata[3] & 255);
    }

    public byte[] toByteArray() {
        return new byte[]{
                (byte) ((ip >> 24) & 0xff),
                (byte) ((ip >> 16) & 0xff),
                (byte) ((ip >> 8) & 0xff),
                (byte) (ip & 0xff)
        };

    }

    public String toString() {
        long ip = this.ip;
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            sb.insert(0,ip & 0xff);
            if (i < 3) {
                sb.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return sb.toString();
    }
}
