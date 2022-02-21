/*
 * http://oku.edu.mie-u.ac.jp/~okumura/algo/
 */

package vavi.util;


/**
 * OkumuraCRC16.
 *
 * @author Okumura
 * @version 0.00 050102 nsano port to java <br>
 */
public class OkumuraCRC16 {
    /** x^{16}+x^{12}+x^5+1 */
    private static final int CRCPOLY1 = 0x1021;
    /** bits per a byte */
    private static final int BYTE_BIT = 8;

    private int crc = 0xffff;

    /**
     * Calculates 16 bit CRC by the method 1.
     * @param c given data
     * @return CRC value
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
