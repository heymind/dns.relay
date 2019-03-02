package dev.idx0;

import java.io.IOException;

public class DNSAResourceData {
    private long ip = 0;

    /**
     * Gets ip
     *
     * @return value of ip
     */
    public long getIp() {
        return ip;
    }

    /**
     * Set the value of ip
     *
     * @return DNSAResourceData
     */
    public DNSAResourceData setIp(long ip) {
        this.ip = ip;
        return this;
    }

    public DNSAResourceData(long ip) {
        this.ip = ip;
    }
    public DNSAResourceData(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int tmp = Integer.parseInt(ipAddressInArray[i]);
            ip += tmp * Math.pow(256, power);
        }
    }

    public DNSAResourceData(byte[] rdata){
        assert rdata.length == 4;

        ip = ((long)(rdata[0] & 255 ) << 24) + ((rdata[1] & 255 ) << 16) + ((rdata[2]& 255) << 8) + ((rdata[3] & 255) << 0);
    }
    public byte[] toByteArray(){
        byte[] r = new byte[4];
        r[0] = (byte) ((ip >> 24)& 0xff);
        r[1] = (byte) ((ip >> 16)& 0xff);
        r[2] = (byte) ((ip >> 8)& 0xff);
        r[3] = (byte) (ip & 0xff);
        return  r;
    }

    public String toString() {
        long ip = this.ip;
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            sb.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                sb.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return sb.toString();
    }
}
