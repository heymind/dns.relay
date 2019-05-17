package dev.idx0;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;


@Data
@Accessors(chain = true)
@AllArgsConstructor
public class DNSQuestion {


    private String Name;
    private int QuestionType;
    private int QuestionClass;

    public static final int QUESTION_CLASS_IN = (1);
    public static final int QUESTION_CLASS_CS = (2);
    public static final int QUESTION_CLASS_CH = (3);
    public static final int QUESTION_CLASS_HS = (4);

    public static final int QUESTION_TYPE_A = (1);
    public static final int QUESTION_TYPE_NS = (2);
    public static final int QUESTION_TYPE_MD = (3);
    public static final int QUESTION_TYPE_MF = (4);
    public static final int QUESTION_TYPE_CNAME = (5);
    public static final int QUESTION_TYPE_SOA = (6);
    public static final int QUESTION_TYPE_MB = (7);
    public static final int QUESTION_TYPE_MG = (8);
    public static final int QUESTION_TYPE_MR = (9);
    public static final int QUESTION_TYPE_NULL = (10);
    public static final int QUESTION_TYPE_WKS = (11);
    public static final int QUESTION_TYPE_PTR = (12);
    public static final int QUESTION_TYPE_HINFO = (13);
    public static final int QUESTION_TYPE_MINFO = (14);
    public static final int QUESTION_TYPE_MX = (15);
    public static final int QUESTION_TYPE_TXT = (16);

    public DNSQuestion(){}
    public String getQuestionTypeName() {
        switch (getQuestionType()) {
            case QUESTION_TYPE_A:
                return "A";
            case QUESTION_TYPE_CNAME:
                return "CNAME";
            default:
                return String.format("TYPE(%d)", getQuestionType());
        }
    }


    public DNSQuestion(ExtendedDataInputStream in) throws IOException {


        setName(in.readDomainName());
        setQuestionType((in.readUnsignedShort()));
        setQuestionClass((in.readUnsignedShort()));
    }

    void serializeToStream(ExtendedDataOutputStream out) throws IOException {
        out.writeDomainName(getName());
        out.writeShort((short) getQuestionType());
        out.writeShort((short) getQuestionClass());
    }

    public byte[] toByteArray() throws IOException {
        ExtendedDataOutputStream out = new ExtendedDataOutputStream();
        serializeToStream(out);
        return out.toByteArray();
    }

}
