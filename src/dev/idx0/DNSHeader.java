package dev.idx0;


import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * http://www.tcpipguide.com/free/t_DNSMessageHeaderandQuestionSectionFormat.htm
 */
public class DNSHeader {

    DNSHeader(){}
    /**
     * Gets ID
     *
     * @return value of ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Set the value of ID
     *
     * @return DNSHeader
     */
    public DNSHeader setID(int ID) {
        this.ID = ID;
        return this;
    }

    private OPCODE OPCode;
    /**
     * Authoritative Answer Flag:
     * This bit is set to 1 in a response to indicate that the server that created the response is authoritative for
     * the zone in which the domain name specified in the Question section is located. If it is 0, the response
     * is non-authoritative.
     */
    private boolean AuthoritativeAnswerFlag = false;
    /**
     * Truncation Flag:
     * When set to 1, indicates that the message was truncated due to its length being longer than the maximum permitted
     * for the type of transport mechanism used. TCP doesn't have a length limit for messages, while UDP messages are
     * limited to 512 bytes, so this bit being sent usually is an indication that the message was sent using UDP and was
     * too long to fit. The client may need to establish a TCP session to get the full message. On the other hand,
     * if the portion truncated was part of the Additional section, it may choose not to bother.
     */
    private boolean TruncationFlag = false;
    /**
     * Recursion Desired:
     * When set in a query, requests that the server receiving the query attempt to answer the query recursively,
     * if the server supports recursive resolution. The value of this bit is not changed in the response.
     */
    private boolean RecursionDesiredFlag = false;
    /**
     * Recursion Available:
     * Set to 1 or cleared to 0 in a response to indicate whether the server creating the response supports recursive
     * queries. This can then be noted by the device that sent the query for future use.
     */
    private boolean RecursionAvailableFlag = false;

    private RCODE ResponseCode;

    /**
     * @return value of QuestionCount
     */
    public int getQuestionCount() {
        return QuestionCount;
    }

    /**
     * Set the value of QuestionCount
     *
     * @return DNSHeader
     */
    public DNSHeader setQuestionCount(int questionCount) {
        QuestionCount = questionCount;
        return this;
    }

    /**
     * Gets AnswerRecordCount
     *
     * @return value of AnswerRecordCount
     */
    public int getAnswerRecordCount() {
        return AnswerRecordCount;
    }

    /**
     * Set the value of AnswerRecordCount
     *
     * @return DNSHeader
     */
    public DNSHeader setAnswerRecordCount(int answerRecordCount) {
        AnswerRecordCount = answerRecordCount;
        return this;
    }

    /**
     * Gets AuthorityRecordCount
     *
     * @return value of AuthorityRecordCount
     */
    public int getAuthorityRecordCount() {
        return AuthorityRecordCount;
    }

    /**
     * Set the value of AuthorityRecordCount
     *
     * @return DNSHeader
     */
    public DNSHeader setAuthorityRecordCount(int authorityRecordCount) {
        AuthorityRecordCount = authorityRecordCount;
        return this;
    }

    /**
     * Gets AdditionalRecordCount
     *
     * @return value of AdditionalRecordCount
     */
    public int getAdditionalRecordCount() {
        return AdditionalRecordCount;
    }

    /**
     * Set the value of AdditionalRecordCount
     *
     * @return DNSHeader
     */
    public DNSHeader setAdditionalRecordCount(int additionalRecordCount) {
        AdditionalRecordCount = additionalRecordCount;
        return this;
    }

    /**
     * Gets OPCode
     *
     * @return value of OPCode
     */
    public OPCODE getOPCode() {
        return OPCode;
    }

    /**
     * Set the value of OPCode
     *
     * @return DNSHeader
     */
    public DNSHeader setOPCode(OPCODE OPCode) {
        this.OPCode = OPCode;
        return this;
    }

    /**
     * Gets AuthoritativeAnswerFlag
     *
     * @return value of AuthoritativeAnswerFlag
     */
    public boolean isAuthoritativeAnswerFlag() {
        return AuthoritativeAnswerFlag;
    }

    /**
     * Set the value of AuthoritativeAnswerFlag
     *
     * @return DNSHeader
     */
    public DNSHeader setAuthoritativeAnswerFlag(boolean authoritativeAnswerFlag) {
        AuthoritativeAnswerFlag = authoritativeAnswerFlag;
        return this;
    }

    /**
     * Gets TruncationFlag
     *
     * @return value of TruncationFlag
     */
    public boolean isTruncationFlag() {
        return TruncationFlag;
    }

    /**
     * Set the value of TruncationFlag
     *
     * @return DNSHeader
     */
    public DNSHeader setTruncationFlag(boolean truncationFlag) {
        TruncationFlag = truncationFlag;
        return this;
    }

    /**
     * Gets RecursionDesiredFlag
     *
     * @return value of RecursionDesiredFlag
     */
    public boolean isRecursionDesiredFlag() {
        return RecursionDesiredFlag;
    }

    /**
     * Set the value of RecursionDesiredFlag
     *
     * @return DNSHeader
     */
    public DNSHeader setRecursionDesiredFlag(boolean recursionDesiredFlag) {
        RecursionDesiredFlag = recursionDesiredFlag;
        return this;
    }

    /**
     * Gets RecursionAvailableFlag
     *
     * @return value of RecursionAvailableFlag
     */
    public boolean isRecursionAvailableFlag() {
        return RecursionAvailableFlag;
    }

    /**
     * Set the value of RecursionAvailableFlag
     *
     * @return DNSHeader
     */
    public DNSHeader setRecursionAvailableFlag(boolean recursionAvailableFlag) {
        RecursionAvailableFlag = recursionAvailableFlag;
        return this;
    }

    /**
     * Gets ResponseCode
     *
     * @return value of ResponseCode
     */
    public RCODE getResponseCode() {
        return ResponseCode;
    }

    /**
     * Set the value of ResponseCode
     *
     * @return DNSHeader
     */
    public DNSHeader setResponseCode(RCODE responseCode) {
        ResponseCode = responseCode;
        return this;
    }

    public enum QR {Query, Response}

    public enum OPCODE {
        QUERY(0),
        IQUERY(1),
        STATUS(2),
        NOTIFY(4),
        UPDATE(5);

        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private OPCODE(int code) {
            this.code = code;
        }

        static Map<Integer, OPCODE> map = new HashMap<>();

        static {
            for (OPCODE t : OPCODE.values()) {
                map.put(t.code, t);
            }
        }

        public static OPCODE getByCode(int code) {
            return map.get(code);
        }
    }

    public enum RCODE {
        NoError(0),
        FormatError(1),
        ServerFailure(2),
        NameError(3),
        NotImplemented(4),
        Refused(5),
        YXDomain(6),
        YXRRSet(7),
        NXRRSet(8),
        NotAuth(9),
        NotZone(10);
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private int code;

        private RCODE(int code) {
            this.code = code;
        }

        static Map<Integer, RCODE> map = new HashMap<>();

        static {
            for (RCODE t : RCODE.values()) {
                map.put(t.code, t);
            }
        }

        public static RCODE getByCode(int code) {
            return map.get(code);
        }
    }

    /**
     * Identifier: A 16-bit identification field generated by the device that creates the DNS query.
     * It is copied by the server into the response, so it can be used by that device to match that query to
     * the corresponding reply received from a DNS server.
     */
    private int ID;


    /**
     * Question Count:
     * Specifies the number of questions in the Question section of the message.
     */
    private int QuestionCount = 0;
    /**
     * Answer Record Count:
     * Specifies the number of resource records in the Answer section of the message.
     */
    private int AnswerRecordCount = 0;
    /**
     * Authority Record Count:
     * Specifies the number of resource records in the Authority section of the message.
     */
    private int AuthorityRecordCount = 0;
    /**
     * Additional Record Count:
     * Specifies the number of resource records in the Additional section of the message.
     */
    private int AdditionalRecordCount =0;

    /**
     * Gets QueryFlag
     *
     * @return value of QueryFlag
     */
    public boolean isQueryFlag() {
        return QueryFlag;
    }

    /**
     * Set the value of QueryFlag
     *
     * @return DNSHeader
     */
    public DNSHeader setQueryFlag(boolean queryFlag) {
        QueryFlag = queryFlag;
        return this;
    }

    private boolean QueryFlag;
    public DNSHeader(byte[] buf) throws IOException {
        ExtendedDataInputStream in = new ExtendedDataInputStream(buf);
        this.setID(in.readUnsignedShort());
        int flags = in.readUnsignedShort();
        this.setResponseCode(RCODE.getByCode(flags & 0xf));
        flags >>= 7;
        this.setRecursionAvailableFlag((flags & 1) == 1);
        flags >>= 1;
        this.setRecursionDesiredFlag((flags & 1) == 1);
        flags >>= 1;
        this.setTruncationFlag((flags & 1) == 1);
        flags >>= 1;
        this.setAuthoritativeAnswerFlag((flags & 1) == 1);
        this.setOPCode(OPCODE.getByCode(flags & 0xf));
        flags >>= 4;
        this.setQueryFlag((flags & 1) == 1);

        this.setQuestionCount(in.readUnsignedShort())
                .setAnswerRecordCount(in.readUnsignedShort())
                .setAuthorityRecordCount(in.readUnsignedShort())
                .setAdditionalRecordCount(in.readUnsignedShort());
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        out.writeShort((short) this.getID());
        int flags = this.isQueryFlag() ? 0 : 1;
        flags <<= 4;
        flags |= this.getOPCode().getCode();
        flags <<= 1;
        flags |= this.isAuthoritativeAnswerFlag() ? 1 : 0;
        flags <<= 1;
        flags |= this.isTruncationFlag() ? 1 : 0;
        flags <<= 1;
        flags |= this.isRecursionDesiredFlag() ? 1 : 0;
        flags <<= 1;
        flags |= this.isRecursionAvailableFlag() ? 1 : 0;
        flags <<= 7;
        flags |= this.getResponseCode().getCode();
        out.writeShort((short)flags);
        out.writeShort((short) this.getQuestionCount());
        out.writeShort((short) this.getAnswerRecordCount());
        out.writeShort((short) this.getAuthorityRecordCount());
        out.writeShort((short) this.getAdditionalRecordCount());
        return out.toByteArray();
    }
}
