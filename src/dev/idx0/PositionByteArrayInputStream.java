package dev.idx0;

import java.io.ByteArrayInputStream;

class PositionByteArrayInputStream extends ByteArrayInputStream {

    PositionByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    int getPosition() {
        return this.pos;
    }
}