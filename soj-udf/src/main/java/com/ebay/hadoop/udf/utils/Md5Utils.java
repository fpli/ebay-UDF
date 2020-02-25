package com.ebay.hadoop.udf.utils;


import java.util.Arrays;

public class Md5Utils {
    public static final long T1 = 0xd76aa478 & 0x00000000ffffffffL;
    public static final long T2 = 0xe8c7b756 & 0x00000000ffffffffL;
    public static final long T3 = 0x242070db & 0x00000000ffffffffL;
    public static final long T4 = 0xc1bdceee & 0x00000000ffffffffL;
    public static final long T5 = 0xf57c0faf & 0x00000000ffffffffL;
    public static final long T6 = 0x4787c62a & 0x00000000ffffffffL;
    public static final long T7 = 0xa8304613 & 0x00000000ffffffffL;
    public static final long T8 = 0xfd469501 & 0x00000000ffffffffL;
    public static final long T9 = 0x698098d8 & 0x00000000ffffffffL;
    public static final long T10 = 0x8b44f7af & 0x00000000ffffffffL;
    public static final long T11 = 0xffff5bb1 & 0x00000000ffffffffL;
    public static final long T12 = 0x895cd7be & 0x00000000ffffffffL;
    public static final long T13 = 0x6b901122 & 0x00000000ffffffffL;
    public static final long T14 = 0xfd987193 & 0x00000000ffffffffL;
    public static final long T15 = 0xa679438e & 0x00000000ffffffffL;
    public static final long T16 = 0x49b40821 & 0x00000000ffffffffL;
    public static final long T17 = 0xf61e2562 & 0x00000000ffffffffL;
    public static final long T18 = 0xc040b340 & 0x00000000ffffffffL;
    public static final long T19 = 0x265e5a51 & 0x00000000ffffffffL;
    public static final long T20 = 0xe9b6c7aa & 0x00000000ffffffffL;
    public static final long T21 = 0xd62f105d & 0x00000000ffffffffL;
    public static final long T22 = 0x02441453 & 0x00000000ffffffffL;
    public static final long T23 = 0xd8a1e681 & 0x00000000ffffffffL;
    public static final long T24 = 0xe7d3fbc8 & 0x00000000ffffffffL;
    public static final long T25 = 0x21e1cde6 & 0x00000000ffffffffL;
    public static final long T26 = 0xc33707d6 & 0x00000000ffffffffL;
    public static final long T27 = 0xf4d50d87 & 0x00000000ffffffffL;
    public static final long T28 = 0x455a14ed & 0x00000000ffffffffL;
    public static final long T29 = 0xa9e3e905 & 0x00000000ffffffffL;
    public static final long T30 = 0xfcefa3f8 & 0x00000000ffffffffL;
    public static final long T31 = 0x676f02d9 & 0x00000000ffffffffL;
    public static final long T32 = 0x8d2a4c8a & 0x00000000ffffffffL;
    public static final long T33 = 0xfffa3942 & 0x00000000ffffffffL;
    public static final long T34 = 0x8771f681 & 0x00000000ffffffffL;
    public static final long T35 = 0x6d9d6122 & 0x00000000ffffffffL;
    public static final long T36 = 0xfde5380c & 0x00000000ffffffffL;
    public static final long T37 = 0xa4beea44 & 0x00000000ffffffffL;
    public static final long T38 = 0x4bdecfa9 & 0x00000000ffffffffL;
    public static final long T39 = 0xf6bb4b60 & 0x00000000ffffffffL;
    public static final long T40 = 0xbebfbc70 & 0x00000000ffffffffL;
    public static final long T41 = 0x289b7ec6 & 0x00000000ffffffffL;
    public static final long T42 = 0xeaa127fa & 0x00000000ffffffffL;
    public static final long T43 = 0xd4ef3085 & 0x00000000ffffffffL;
    public static final long T44 = 0x04881d05 & 0x00000000ffffffffL;
    public static final long T45 = 0xd9d4d039 & 0x00000000ffffffffL;
    public static final long T46 = 0xe6db99e5 & 0x00000000ffffffffL;
    public static final long T47 = 0x1fa27cf8 & 0x00000000ffffffffL;
    public static final long T48 = 0xc4ac5665 & 0x00000000ffffffffL;
    public static final long T49 = 0xf4292244 & 0x00000000ffffffffL;
    public static final long T50 = 0x432aff97 & 0x00000000ffffffffL;
    public static final long T51 = 0xab9423a7 & 0x00000000ffffffffL;
    public static final long T52 = 0xfc93a039 & 0x00000000ffffffffL;
    public static final long T53 = 0x655b59c3 & 0x00000000ffffffffL;
    public static final long T54 = 0x8f0ccc92 & 0x00000000ffffffffL;
    public static final long T55 = 0xffeff47d & 0x00000000ffffffffL;
    public static final long T56 = 0x85845dd1 & 0x00000000ffffffffL;
    public static final long T57 = 0x6fa87e4f & 0x00000000ffffffffL;
    public static final long T58 = 0xfe2ce6e0 & 0x00000000ffffffffL;
    public static final long T59 = 0xa3014314 & 0x00000000ffffffffL;
    public static final long T60 = 0x4e0811a1 & 0x00000000ffffffffL;
    public static final long T61 = 0xf7537e82 & 0x00000000ffffffffL;
    public static final long T62 = 0xbd3af235 & 0x00000000ffffffffL;
    public static final long T63 = 0x2ad7d2bb & 0x00000000ffffffffL;
    public static final long T64 = 0xeb86d391 & 0x00000000ffffffffL;


    public long[] PMS = null;
    public int[] count;
    public int[] buf = new int[64];

    public void init() {
        PMS = new long[]{
                0x67452301 & 0x00000000ffffffffL,
                0xefcdab89 & 0x00000000ffffffffL,
                0x98badcfe & 0x00000000ffffffffL,
                0x10325476 & 0x00000000ffffffffL
        };

        count = new int[]{0, 0};
    }

    public static void main(String[] args) {
        Md5Utils utils = new Md5Utils();
        long digit[] = new long[16];

        String[] strs = {
                "", /*d41d8cd98f00b204e9800998ecf8427e*/
                "a", /*0cc175b9c0f1b6a831c399e269772661*/
                "abc", /*900150983cd24fb0d6963f7d28e17f72*/
                "message digest", /*f96b697d7cb7938d525a2f31aaf161d0*/
                "abcdefghijklmnopqrstuvwxyz", /*c3fcd3d76192e4007dfb496cca67e13b*/
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
                /*d174ab98d277d9f5a5611c2c9f419d9f*/
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" /*57edf4a22be3c955ac49da2e2107b67a*/
        };
        for (int i = 0; i < strs.length; i++) {
            utils.init();
            utils.append(strs[i]);
            utils.finish(digit);
            for (int j = 0; j < digit.length; j++) {
                String str = Long.toHexString(digit[j] & 0xff);
                if (str.length() == 1) {
                    System.out.print("0" + str);
                } else {
                    System.out.print(str);
                }

            }
            System.out.println();
        }

    }


    public long[] process(int[] processData) {
        long a = PMS[0];
        long b = PMS[1];
        long c = PMS[2];
        long d = PMS[3];


        long[] data = new long[16];

        for (int i = 0; i < data.length; i++) {
            data[i] = processData[4 * i] | processData[4 * i + 1] << 8 | processData[4 * i + 2] << 16 | processData[4 * i + 3] << 24;
        }


        a = SET1(a, b, c, d, 0, 7, T1, data);
        d = SET1(d, a, b, c, 1, 12, T2, data);
        c = SET1(c, d, a, b, 2, 17, T3, data);
        b = SET1(b, c, d, a, 3, 22, T4, data);
        a = SET1(a, b, c, d, 4, 7, T5, data);
        d = SET1(d, a, b, c, 5, 12, T6, data);
        c = SET1(c, d, a, b, 6, 17, T7, data);

        b = SET1(b, c, d, a, 7, 22, T8, data);
        a = SET1(a, b, c, d, 8, 7, T9, data);
        d = SET1(d, a, b, c, 9, 12, T10, data);
        c = SET1(c, d, a, b, 10, 17, T11, data);
        b = SET1(b, c, d, a, 11, 22, T12, data);
        a = SET1(a, b, c, d, 12, 7, T13, data);
        d = SET1(d, a, b, c, 13, 12, T14, data);
        c = SET1(c, d, a, b, 14, 17, T15, data);
        b = SET1(b, c, d, a, 15, 22, T16, data);


        a = SET2(a, b, c, d, 1, 5, T17, data);
        d = SET2(d, a, b, c, 6, 9, T18, data);
        c = SET2(c, d, a, b, 11, 14, T19, data);
        b = SET2(b, c, d, a, 0, 20, T20, data);
        a = SET2(a, b, c, d, 5, 5, T21, data);
        d = SET2(d, a, b, c, 10, 9, T22, data);
        c = SET2(c, d, a, b, 15, 14, T23, data);
        b = SET2(b, c, d, a, 4, 20, T24, data);
        a = SET2(a, b, c, d, 9, 5, T25, data);
        d = SET2(d, a, b, c, 14, 9, T26, data);
        c = SET2(c, d, a, b, 3, 14, T27, data);
        b = SET2(b, c, d, a, 8, 20, T28, data);
        a = SET2(a, b, c, d, 13, 5, T29, data);
        d = SET2(d, a, b, c, 2, 9, T30, data);
        c = SET2(c, d, a, b, 7, 14, T31, data);
        b = SET2(b, c, d, a, 12, 20, T32, data);


        a = SET3(a, b, c, d, 5, 4, T33, data);
        d = SET3(d, a, b, c, 8, 11, T34, data);
        c = SET3(c, d, a, b, 11, 16, T35, data);
        b = SET3(b, c, d, a, 14, 23, T36, data);
        a = SET3(a, b, c, d, 1, 4, T37, data);
        d = SET3(d, a, b, c, 4, 11, T38, data);
        c = SET3(c, d, a, b, 7, 16, T39, data);
        b = SET3(b, c, d, a, 10, 23, T40, data);
        a = SET3(a, b, c, d, 13, 4, T41, data);
        d = SET3(d, a, b, c, 0, 11, T42, data);
        c = SET3(c, d, a, b, 3, 16, T43, data);
        b = SET3(b, c, d, a, 6, 23, T44, data);
        a = SET3(a, b, c, d, 9, 4, T45, data);
        d = SET3(d, a, b, c, 12, 11, T46, data);
        c = SET3(c, d, a, b, 15, 16, T47, data);
        b = SET3(b, c, d, a, 2, 23, T48, data);

        a = SET4(a, b, c, d, 0, 6, T49, data);
        d = SET4(d, a, b, c, 7, 10, T50, data);
        c = SET4(c, d, a, b, 14, 15, T51, data);
        b = SET4(b, c, d, a, 5, 21, T52, data);
        a = SET4(a, b, c, d, 12, 6, T53, data);
        d = SET4(d, a, b, c, 3, 10, T54, data);
        c = SET4(c, d, a, b, 10, 15, T55, data);
        b = SET4(b, c, d, a, 1, 21, T56, data);
        a = SET4(a, b, c, d, 8, 6, T57, data);
        d = SET4(d, a, b, c, 15, 10, T58, data);
        c = SET4(c, d, a, b, 6, 15, T59, data);
        b = SET4(b, c, d, a, 13, 21, T60, data);
        a = SET4(a, b, c, d, 4, 6, T61, data);
        d = SET4(d, a, b, c, 11, 10, T62, data);
        c = SET4(c, d, a, b, 2, 15, T63, data);
        b = SET4(b, c, d, a, 9, 21, T64, data);


        PMS[0] = (PMS[0] + a) & 0x00000000ffffffffL;
        PMS[1] = (PMS[1] + b) & 0x00000000ffffffffL;
        PMS[2] = (PMS[2] + c) & 0x00000000ffffffffL;
        PMS[3] = (PMS[3] + d) & 0x00000000ffffffffL;

        return PMS;
    }

    public void finish(long[] digest) {
        int pad[] = {
                0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        int[] data = new int[8];

        for (int i = 0; i < 8; ++i)
            data[i] = (count[i >> 2] >> ((i & 3) << 3));

        append(pad, ((55 - (count[0] >> 3)) & 63) + 1);
        append(data, 8);

        for (int i = 0; i < 16; ++i)
            digest[i] = PMS[i >> 2] >> ((i & 3) << 3);
    }

    public void append(String str) {
        if (str == "") {
            append(null, 0);
        } else {
            int[] data = new int[str.length()];
            for (int i = 0; i < data.length; i++) {
                data[i] = str.charAt(i);
            }
            append(data, data.length);
        }
    }

    public void append(int[] data, int nbytes) {
        int left = nbytes;
        int offset = (count[0] >> 3) & 63;
        int nbits = nbytes << 3;

        if (nbytes <= 0) {
            return;
        }

        count[1] += nbytes >> 29;
        count[0] += nbits;

        if (count[0] < nbits) {
            count[1]++;
        }

        int p = 0;
        if (offset > 0) {
            int copy = (offset + nbytes > 64 ? 64 - offset : nbytes);

            System.arraycopy(data, p, buf, offset, copy);
            if (offset + copy < 64)
                return;
            p += copy;
            left -= copy;
            process(buf);
        }


        /* Process full blocks. */
        for (; left >= 64; p += 64, left -= 64)
            process(Arrays.copyOfRange(data, p, p + 64));

        /* Process a final partial block. */
        if (left > 0) {
            System.arraycopy(data, p, buf, 0, left);
        }
    }


    long ROTATE_LEFT(long x, long n) {
        return (((x & 0x00000000ffffffffL) << n)) | (((x & 0x00000000ffffffffL) >> (32 - (n))));
    }


    public long F(long x, long y, long z) {
        return (((x) & (y)) | (~(x) & 0x00000000ffffffffL & (z)));
    }

    public long G(long x, long y, long z) {
        return ((x) & (z)) | ((y) & (~(z) & 0x00000000ffffffffL));
    }

    public long H(long x, long y, long z) {
        return ((x) ^ (y) ^ (z));
    }

    public long I(long x, long y, long z) {
        return ((y) ^ ((x) | (~(z) & 0x00000000ffffffffL)));
    }

    public long SET1(long a, long b, long c, long d, int k, long s, long Ti, long[] X) {
        long t = a + F(b, c, d) + X[k] + Ti;
        a = ROTATE_LEFT(t, s) + b;
        return a & 0x00000000ffffffffL;
    }


    public long SET2(long a, long b, long c, long d, int k, long s, long Ti, long[] X) {
        long t = a + G(b, c, d) + X[k] + Ti;
        a = ROTATE_LEFT(t, s) + b;
        return a & 0x00000000ffffffffL;
    }


    public long SET3(long a, long b, long c, long d, int k, long s, long Ti, long[] X) {
        long t = a + H(b, c, d) + X[k] + Ti;
        a = ROTATE_LEFT(t, s) + b;
        return a & 0x00000000ffffffffL;
    }

    public long SET4(long a, long b, long c, long d, int k, long s, long Ti, long[] X) {
        long t = a + I(b, c, d) + X[k] + Ti;
        a = ROTATE_LEFT(t, s) + b;
        return a & 0x00000000ffffffffL;
    }
}
