package dev.idx0;

import java.util.HashMap;
import java.util.Map;

public enum DNSQuestionType {
    A(1),
    NS(2),
    MD(3),
    MF(4),
    CNAME(5),
    SOA(6),
    MB(7),
    MG(8),
    MR(9),
    NULL(10),
    WKS(11),
    PTR(12),
    HINFO(13),
    MINFO(14),
    MX(15),
    TXT(16);

    private DNSQuestionType(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return this.type;
    }

    public String toString() {
        return String.valueOf(this.type);
    }

    static Map<Integer, DNSQuestionType> map = new HashMap<>();

    static {
        for (DNSQuestionType t : DNSQuestionType.values()) {
            map.put(t.type, t);
        }
    }

    public static DNSQuestionType getByCode(int code) {
        return map.get(code);
    }

}
