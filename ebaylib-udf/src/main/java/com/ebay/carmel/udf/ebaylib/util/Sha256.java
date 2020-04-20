package com.ebay.carmel.udf.ebaylib.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.ebay.carmel.udf.ebaylib.util.Constant.*;

public class Sha256 {


    static byte[] sha2_padding = new byte[]
    {
                (byte)0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };


    public static String sha2(byte[] input, int ilen, byte output[] )
    {
        Sha256Conext ctx = new Sha256Conext();
        sha2_update(ctx, input, ilen );
        sha2_finish(ctx, output);
        String result = "";
        for(int i = 0 ; i < output.length ; i ++) {
            String hexStr = Long.toHexString(output[i]&0xff);
            if(hexStr.length() == 1) {
                hexStr = "0"+ hexStr;
            }
            result += hexStr;
        }
        return result;
    }

    public static void sha2_process(Sha256Conext ctx, byte[] data) {
         List<AtomicReference<BigInteger>> W = new ArrayList<AtomicReference<BigInteger>>(64);
         for(int i = 0 ; i < 64 ; i++) {
             W.add(new AtomicReference<BigInteger>(BigInteger.valueOf(0)));
         }
         AtomicReference<BigInteger> A, B, C, D, E, F, G, H;
         GET_ULONG_BE(W, data,  0 ,0);
         GET_ULONG_BE(W, data,  4 ,1);
         GET_ULONG_BE(W, data,  8 ,2);
         GET_ULONG_BE(W, data, 12, 3);
         GET_ULONG_BE(W, data, 16 ,4);
         GET_ULONG_BE(W, data, 20 ,5);
         GET_ULONG_BE(W, data, 24, 6);
         GET_ULONG_BE(W, data, 28, 7);
         GET_ULONG_BE(W, data, 32, 8);
         GET_ULONG_BE(W, data, 36, 9);
         GET_ULONG_BE(W, data, 40, 10);
         GET_ULONG_BE(W, data, 44, 11);
         GET_ULONG_BE(W, data, 48, 12);
         GET_ULONG_BE(W, data, 52, 13);
         GET_ULONG_BE(W, data, 56, 14);
         GET_ULONG_BE(W, data, 60, 15);

         A = new AtomicReference<BigInteger>(new BigInteger(ctx.state[0].toString()));
         B = new AtomicReference<BigInteger> (new BigInteger(ctx.state[1].toString()));
         C = new AtomicReference<BigInteger> (new BigInteger(ctx.state[2].toString()));
         D = new AtomicReference<BigInteger> (new BigInteger(ctx.state[3].toString()));
         E = new AtomicReference<BigInteger> (new BigInteger(ctx.state[4].toString()));
         F = new AtomicReference<BigInteger> (new BigInteger(ctx.state[5].toString()));
         G = new AtomicReference<BigInteger> (new BigInteger(ctx.state[6].toString()));
         H = new AtomicReference<BigInteger> (new BigInteger(ctx.state[7].toString()));

        P( A, B, C, D, E, F, G, H, W.get(0), 0x428A2F98l);
        P( H, A, B, C, D, E, F, G, W.get(1), 0x71374491l);
        P( G, H, A, B, C, D, E, F, W.get(2), 0xB5C0FBCFl);
        P( F, G, H, A, B, C, D, E, W.get(3), 0xE9B5DBA5l);
        P( E, F, G, H, A, B, C, D, W.get(4), 0x3956C25Bl);
        P( D, E, F, G, H, A, B, C, W.get(5), 0x59F111F1l);
        P( C, D, E, F, G, H, A, B, W.get(6), 0x923F82A4l);
        P( B, C, D, E, F, G, H, A, W.get(7), 0xAB1C5ED5l);
        P( A, B, C, D, E, F, G, H, W.get(8), 0xD807AA98l);
        P( H, A, B, C, D, E, F, G, W.get(9), 0x12835B01l);
        P( G, H, A, B, C, D, E, F, W.get(10), 0x243185BEl);
        P( F, G, H, A, B, C, D, E, W.get(11), 0x550C7DC3l);
        P( E, F, G, H, A, B, C, D, W.get(12), 0x72BE5D74l);
        P( D, E, F, G, H, A, B, C, W.get(13), 0x80DEB1FEl);
        P( C, D, E, F, G, H, A, B, W.get(14), 0x9BDC06A7l);
        P( B, C, D, E, F, G, H, A, W.get(15), 0xC19BF174l);
        P( A, B, C, D, E, F, G, H, R(16, W), 0xE49B69C1l);
        P( H, A, B, C, D, E, F, G, R(17, W), 0xEFBE4786l);
        P( G, H, A, B, C, D, E, F, R(18, W), 0x0FC19DC6l);
        P( F, G, H, A, B, C, D, E, R(19, W), 0x240CA1CCl);
        P( E, F, G, H, A, B, C, D, R(20, W), 0x2DE92C6Fl);
        P( D, E, F, G, H, A, B, C, R(21, W), 0x4A7484AAl);
        P( C, D, E, F, G, H, A, B, R(22, W), 0x5CB0A9DCl);
        P( B, C, D, E, F, G, H, A, R(23, W), 0x76F988DAl);
        P( A, B, C, D, E, F, G, H, R(24, W), 0x983E5152l);
        P( H, A, B, C, D, E, F, G, R(25, W), 0xA831C66Dl);
        P( G, H, A, B, C, D, E, F, R(26, W), 0xB00327C8l);
        P( F, G, H, A, B, C, D, E, R(27, W), 0xBF597FC7l);
        P( E, F, G, H, A, B, C, D, R(28, W), 0xC6E00BF3l);
        P( D, E, F, G, H, A, B, C, R(29, W), 0xD5A79147l);
        P( C, D, E, F, G, H, A, B, R(30, W), 0x06CA6351l);
        P( B, C, D, E, F, G, H, A, R(31, W), 0x14292967l);
        P( A, B, C, D, E, F, G, H, R(32, W), 0x27B70A85l);
        P( H, A, B, C, D, E, F, G, R(33, W), 0x2E1B2138l);
        P( G, H, A, B, C, D, E, F, R(34, W), 0x4D2C6DFCl);
        P( F, G, H, A, B, C, D, E, R(35, W), 0x53380D13l);
        P( E, F, G, H, A, B, C, D, R(36, W), 0x650A7354l);
        P( D, E, F, G, H, A, B, C, R(37, W), 0x766A0ABBl);
        P( C, D, E, F, G, H, A, B, R(38, W), 0x81C2C92El);
        P( B, C, D, E, F, G, H, A, R(39, W), 0x92722C85l);
        P( A, B, C, D, E, F, G, H, R(40, W), 0xA2BFE8A1l);
        P( H, A, B, C, D, E, F, G, R(41, W), 0xA81A664Bl);
        P( G, H, A, B, C, D, E, F, R(42, W), 0xC24B8B70l);
        P( F, G, H, A, B, C, D, E, R(43, W), 0xC76C51A3l);
        P( E, F, G, H, A, B, C, D, R(44, W), 0xD192E819l);
        P( D, E, F, G, H, A, B, C, R(45, W), 0xD6990624l);
        P( C, D, E, F, G, H, A, B, R(46, W), 0xF40E3585l);
        P( B, C, D, E, F, G, H, A, R(47, W), 0x106AA070l);
        P( A, B, C, D, E, F, G, H, R(48, W), 0x19A4C116l);
        P( H, A, B, C, D, E, F, G, R(49, W), 0x1E376C08l);
        P( G, H, A, B, C, D, E, F, R(50, W), 0x2748774Cl);
        P( F, G, H, A, B, C, D, E, R(51, W), 0x34B0BCB5l);
        P( E, F, G, H, A, B, C, D, R(52, W), 0x391C0CB3l);
        P( D, E, F, G, H, A, B, C, R(53, W), 0x4ED8AA4Al);
        P( C, D, E, F, G, H, A, B, R(54, W), 0x5B9CCA4Fl);
        P( B, C, D, E, F, G, H, A, R(55, W), 0x682E6FF3l);
        P( A, B, C, D, E, F, G, H, R(56, W), 0x748F82EEl);
        P( H, A, B, C, D, E, F, G, R(57, W), 0x78A5636Fl);
        P( G, H, A, B, C, D, E, F, R(58, W), 0x84C87814l);
        P( F, G, H, A, B, C, D, E, R(59, W), 0x8CC70208l);
        P( E, F, G, H, A, B, C, D, R(60, W), 0x90BEFFFAl);
        P( D, E, F, G, H, A, B, C, R(61, W), 0xA4506CEBl);
        P( C, D, E, F, G, H, A, B, R(62, W), 0xBEF9A3F7l);
        P( B, C, D, E, F, G, H, A, R(63, W), 0xC67178F2l);

        ctx.state[0] = ctx.state[0].add(A.get());
        ctx.state[1] = ctx.state[1].add(B.get());
        ctx.state[2] = ctx.state[2].add(C.get());
        ctx.state[3] = ctx.state[3].add(D.get());
        ctx.state[4] = ctx.state[4].add(E.get());
        ctx.state[5] = ctx.state[5].add(F.get());
        ctx.state[6] = ctx.state[6].add(G.get());
        ctx.state[7] = ctx.state[7].add(H.get());
    }

    public static void sha2_update(Sha256Conext ctx, byte[] input, long ilen){
        long fill;
        long left;
        int sidx = 0;
        if( ilen <= 0 )
            return;

        left = ctx.total[0] & 0x3F;
        fill = 64 - left;

        ctx.total[0] += ilen;
        ctx.total[0] &= 0xFFFFFFFF;

        if( ctx.total[0] < ilen )
            ctx.total[1]++;
        if(left !=0 && ilen >= fill )
        {
            System.arraycopy(input, sidx, ctx.buffer, (int)left, (int)fill);
            sha2_process(ctx, ctx.buffer);
            sidx += fill;
            ilen -= fill;
            left = 0;
        }

        while( ilen >= 64 )
        {
            sha2_process( ctx, input );
            sidx += 64;
            ilen -= 64;
        }

        if( ilen > 0 )
        {
            System.arraycopy(input, sidx, ctx.buffer, (int)left, (int)ilen);
        }
    }

    public static void GET_ULONG_BE(List<AtomicReference<BigInteger>> w, byte[] b, int i,int idx) {
        BigInteger shift1 = longParseUnsigned(BigInteger.valueOf(b[i] & 0xff).shiftLeft(24));
        BigInteger shift2 = longParseUnsigned(BigInteger.valueOf(b[i + 1] & 0xff).shiftLeft(16));
        BigInteger shift3 = longParseUnsigned(BigInteger.valueOf(b[i + 2] & 0xff).shiftLeft(8));
        BigInteger shift0 = longParseUnsigned(BigInteger.valueOf(b[i + 3] & 0xff));
        BigInteger r = shift1.or(shift2).or(shift3).or(shift0);
        w.get(idx).set(r);
    }

    public static BigInteger SHR(BigInteger x, int n) {
        BigInteger r = x.and(BigInteger.valueOf(0xFFFFFFFFl)).shiftRight(n);
        return longParseUnsigned(r);
    }

    public static BigInteger ROTR(BigInteger x, int n) {
        BigInteger tmp = x.shiftLeft(32-n);
        tmp = longParseUnsigned(tmp);
        BigInteger r = (SHR(x,n).or(tmp));
        return r;
    }

    public static BigInteger S0(BigInteger x) {
        BigInteger r =  ROTR(x, 7).xor(ROTR(x,18)).xor(SHR(x, 3));
        return longParseUnsigned(r);
    }
    public static BigInteger S1(BigInteger x) {
        BigInteger r =  (ROTR(x,17).xor(ROTR(x,19)).xor(SHR(x,10)));
        return longParseUnsigned(r);
    }

    public static BigInteger S2(BigInteger x) {
        BigInteger r = (ROTR(x, 2).xor(ROTR(x,13)).xor(ROTR(x,22)));
        return longParseUnsigned(r);
    }
    public static BigInteger S3(BigInteger x) {
        BigInteger r =  ROTR(x, 6).xor(ROTR(x,11)).xor(ROTR(x,25));
        return longParseUnsigned(r);
    }
    public static BigInteger F0(BigInteger x, BigInteger y, BigInteger z) {
        BigInteger r = x.and(y).or(z.and(x.or(y)));
        return longParseUnsigned(r);
    }
    public static BigInteger F1(BigInteger  x, BigInteger y, BigInteger z) {
        BigInteger r = z.xor(x.and(y.xor(z)));
        return longParseUnsigned(r);
    }

    public static AtomicReference<BigInteger> R(int t, List<AtomicReference<BigInteger>> W) {
        BigInteger a1  = S1(W.get(t -  2).get());
        BigInteger a2 =  W.get(t -  7).get();
        BigInteger a3 = S0(W.get(t - 15).get());
        BigInteger a4 = W.get(t - 16).get();
        BigInteger tmp = a1.add(a2).add(a3).add(a4);
        W.get(t).set(longParseUnsigned(tmp));
        return W.get(t);
    }

    public static void P(AtomicReference<BigInteger> a, AtomicReference<BigInteger> b, AtomicReference<BigInteger> c, AtomicReference<BigInteger> d,
                         AtomicReference<BigInteger> e,
                         AtomicReference<BigInteger> f, AtomicReference<BigInteger> g, AtomicReference<BigInteger> h, AtomicReference<BigInteger> x, long K) {
        BigInteger s3 = S3(e.get());
        BigInteger f1 = F1(e.get(), f.get(), g.get());
        BigInteger temp1 = h.get().add(s3).add(f1).add(BigInteger.valueOf(K)).add(x.get());
        temp1 = longParseUnsigned(temp1);
        BigInteger f0 = F0(a.get(), b.get(), c.get());
        BigInteger temp2 = longParseUnsigned(S2(a.get()).add(f0));
        d.set(longParseUnsigned(d.get().add(temp1)));
        h.set(longParseUnsigned(temp1.add(temp2)));
    }

    public static final BigInteger longParseUnsigned(BigInteger value) {
        if (value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0)
            return value;
        BigInteger lowValue = value.and(BigInteger.valueOf(0x7fffffffffffffffL));
        BigInteger tmp = new BigInteger("9223372036854775808");
        if (value.and(tmp).compareTo(BigInteger.valueOf(0)) == 0) {
            return lowValue;
        }
        return lowValue.add(BigInteger.valueOf(Long.MAX_VALUE)).add(BigInteger.valueOf(1));
    }

    public static void sha2_finish( Sha256Conext ctx,  byte output[]) {
        long last, padn;
        long high, low;
        byte msglen[] = new byte[8];
        high = ( ctx.total[0] >> 29 )
                | ( ctx.total[1] <<  3 );
        low  = ( ctx.total[0] <<  3 );

        PUT_ULONG_BE( BigInteger.valueOf(high), msglen, 0 );
        PUT_ULONG_BE( BigInteger.valueOf(low),  msglen, 4 );

        last = ctx.total[0] & 0x3F;
        padn = ( last < 56 ) ? ( 56 - last ) : ( 120 - last );

        sha2_update( ctx, sha2_padding, padn );
        sha2_update( ctx, msglen, 8 );

        PUT_ULONG_BE( ctx.state[0], output,  0 );
        PUT_ULONG_BE( ctx.state[1], output,  4 );
        PUT_ULONG_BE( ctx.state[2], output,  8 );
        PUT_ULONG_BE( ctx.state[3], output, 12 );
        PUT_ULONG_BE( ctx.state[4], output, 16 );
        PUT_ULONG_BE( ctx.state[5], output, 20 );
        PUT_ULONG_BE( ctx.state[6], output, 24 );
        PUT_ULONG_BE( ctx.state[7], output, 28 );

    }

    public static void PUT_ULONG_BE(BigInteger n, byte[] b, int i) {
        BigInteger shift24 = n.shiftRight(24).and(BigInteger.valueOf(0xFF));
        BigInteger shift16 = n.shiftRight(16).and(BigInteger.valueOf(0xFF));
        BigInteger shift8 = n.shiftRight(8).and(BigInteger.valueOf(0xFF));
        BigInteger shift = n.and(BigInteger.valueOf(0xFF));
        b[i    ] = shift24.byteValue();
        b[i + 1] = shift16.byteValue();
        b[i + 2] = shift8.byteValue();
        b[i+  3] =  shift.byteValue();
    }
    static class Sha256Conext {
        public long[] total = new long[] {0,0};
        public BigInteger[] state = new BigInteger[] {BigInteger.valueOf(0x6A09E667l),BigInteger.valueOf(0xBB67AE85l),BigInteger.valueOf(0x3C6EF372l),
                BigInteger.valueOf(0xA54FF53Al),BigInteger.valueOf(0x510E527Fl) ,BigInteger.valueOf(0x9B05688Cl) ,
                BigInteger.valueOf(0x1F83D9ABl),BigInteger.valueOf(0x5BE0CD19l)};
        public byte[] buffer = new byte[64];
    }



    public static void evaluate(byte[] text, int text_len, byte[] key, int key_len, byte[] digest) {
        Sha256.Sha256Conext ctx = new Sha256.Sha256Conext();
        int i;
        byte ipad[] = new byte[SHA256_BLOCK_SIZE];
        byte opad[] = new byte[SHA256_BLOCK_SIZE];
        byte tk[] = new byte[SHA256_DIGEST_LENGTH];
        byte tk2[] = new byte[SHA256_DIGEST_LENGTH];
        Arrays.fill(ipad, (byte)0);
        Arrays.fill(opad, (byte)0);
        Arrays.fill(tk, (byte)0);
        Arrays.fill(tk2, (byte)0);
        if (key_len > SHA256_BLOCK_SIZE ) {
            Sha256.sha2(key, key_len, tk);
            System.arraycopy(tk, 0, ipad, 0, SHA256_DIGEST_LENGTH);
            System.arraycopy(tk, 0, opad, 0, SHA256_DIGEST_LENGTH);
        }
        else {
            System.arraycopy(key, 0, ipad, 0, key_len);
            System.arraycopy(key, 0, opad, 0, key_len);
        }
        for (i = 0; i < SHA256_BLOCK_SIZE; i++) {
            ipad[i] ^= 0x36;
            opad[i] ^= 0x5c;
        }

        Sha256.sha2_update(ctx, ipad, SHA256_BLOCK_SIZE );
        Sha256.sha2_update(ctx, text, text_len);
        Sha256.sha2_finish(ctx, tk2);

        ctx = new Sha256.Sha256Conext();
        Sha256.sha2_update(ctx, opad, SHA256_BLOCK_SIZE );
        Sha256.sha2_update(ctx, tk2, SHA256_DIGEST_LENGTH );
        Sha256.sha2_finish(ctx, digest);
    }
}



