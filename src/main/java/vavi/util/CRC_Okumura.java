/*
 * http://oku.edu.mie-u.ac.jp/~okumura/algo/
 */

package vavi.util;


/**
 * CRC_Okumura.
 *
 * @author Okumura
 * @version 0.00 050102 nsano port to java <br>
 */
class CRC_Okumura {
    // x^{16}+x^{12}+x^5+1
    static final int CRCPOLY1 = 0x1021;
    // １バイトのビット数
    static final int BYTE_BIT = 8;

    int crc = 0xffff;

    /**
     * 16 ビットの CRC を方法 1 で求めます。
     * @param c データを与えます。
     * @return CRC 値を返します。
     */
    public int update(byte c[]) {

        for (int i = 0; i < c.length; i++) {
            crc ^= (c[i] & 0xff) << (16 - BYTE_BIT);
            for (int j = 0; j < BYTE_BIT; j++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ CRCPOLY1;
                } else {
                    crc <<= 1;
                }
            }
        }
        return ~crc & 0xffff;
    }

    /** */
    public long getValue() {
        return crc;
    }
}

/* */
