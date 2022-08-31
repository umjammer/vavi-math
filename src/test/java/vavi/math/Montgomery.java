
package vavi.math;

import java.math.BigInteger;


class Montgomery {

    /** 法(m_n) */
    private BigInteger m_n;
    /** rを(m_n)より大きな２のべきとして、(m_r2)=(r)^2 mod n */
    private BigInteger m_r2;
    /** (m_n)*(m_n2) ≡ -1 mod r を満たす(m_n2) */
    private BigInteger m_n2;
    /** (m_n)の有効Bit数, 例えば(m_n)=5 のとき(m_nb)=3 */
    private int m_nb;

    public Montgomery(BigInteger n) {
        m_n = n;
        // n を法とする
        // Bit長を調べる(nb)
        m_nb = n.bitCount();

        // r: nより大きな２のべき
        BigInteger r = BigInteger.ONE;
        r = r.shiftLeft(m_nb);

        // r2 = r^2 mod n
        m_r2 = r.multiply(r);
        m_r2 = m_r2.mod(n);

        // n*n2 ≡ -1 mod r
        m_n2 = BigInteger.ZERO; // 求めるN'
        BigInteger t = BigInteger.ZERO;
        BigInteger vi = BigInteger.ONE;
        int ni = m_nb;
        while (ni-- > 0) { // Rのトップビットを除いたビット数分繰り返す
            if ((t.and(BigInteger.ONE)).equals(BigInteger.ZERO)) {
                // ゼロになっているビットがあったら、N'のその部分を1にする（NはRと互いに素なので必ず奇数）
                t = t.add(n); // 掛け算だが、二進数一桁の掛け算なので実質は足し算
                m_n2 = m_n2.add(vi); // N'のその部分を1にする
            }
            t = t.shiftRight(1); // 必ず端数が出るが切り捨てる
            vi = vi.shiftLeft(1); // Rは2の冪なので、絶対端数は出ない
        }
    }

    BigInteger reduction(BigInteger t) {
        // tのモンゴメリリダクション
        BigInteger c = t.multiply(m_n2);
//        c.hibitmask(m_nb); // mod Rの意味,(m_nb)bit以上を0クリア TODO TODO TODO
        c = c.multiply(m_n);
        c = c.add(t);
        c = c.shiftRight(m_nb); // 1/r の意味
        if (c.compareTo(m_n) >= 0)
            c = c.subtract(m_n);
        return c;
    }

    // 冪剰余 a^b mod n, バイナリ法の下位桁から計算
    BigInteger exp(BigInteger a, BigInteger b) {
        BigInteger p = reduction(a.multiply(m_r2));
        BigInteger x = reduction(m_r2);
        BigInteger y = b;
        while (y.compareTo(BigInteger.ZERO) > 0) {
            if (!y.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
                x = reduction(x.multiply(p));
            }
            p = reduction(p.multiply(p));
            y = y.shiftRight(1);
        }
        return reduction(x);
    }
}
