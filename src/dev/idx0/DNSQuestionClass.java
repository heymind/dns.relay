package dev.idx0;

import java.util.HashMap;
import java.util.Map;

public enum DNSQuestionClass {

    IN(1),
    CS(2),
    CH(3),
    HS(4);

    /**
     * Gets qclass
     *
     * @return value of qclass
     */
    public int getQclass() {
        return qclass;
    }

    public void setQclass(int qclass) {
        this.qclass = qclass;
    }

    private int qclass;

    private DNSQuestionClass(int qclass) {
        this.qclass = qclass;
    }

    static Map<Integer, DNSQuestionClass> map = new HashMap<>();

    static {
        for (DNSQuestionClass t : DNSQuestionClass.values()) {
            map.put(t.qclass, t);
        }
    }

    public static DNSQuestionClass getByCode(int code) {
        return map.get(code);
    }
}
