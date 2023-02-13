package cn.lliiooll.autotask.izuiyou;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import com.alibaba.druid.sql.visitor.functions.Hex;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

public class NetCrypto {


    private static final long[] SV = {
            0xd76aa478L, 0xe8c7b756L, 0x242070dbL, 0xc1bdceeeL, 0xf57c0fafL,
            0x4787c62aL, 0xa8304613L, 0xfd469501L, 0x698098d8L, 0x8b44f7afL,
            0xffff5bb1L, 0x895cd7beL, 0x6b901122L, 0xfd987193L, 0xa679438eL,
            0x49b40821L, 0xf61e2562L, 0xc040b340L, 0x265e5a51L, 0xe9b6c7aaL,
            0xd62f105dL, 0x2441453L, 0xd8a1e681L, 0xe7d3fbc8L, 0x21e1cde6L,
            0xc33707d6L, 0xf4d50d87L, 0x455a14edL, 0xa9e3e905L, 0xfcefa3f8L,
            0x676f02d9L, 0x8d2a4c8aL, 0xfffa3942L, 0x8771f681L, 0x6d9d6122L,
            0xfde5380cL, 0xa4beea44L, 0x4bdecfa9L, 0xf6bb4b60L, 0xbebfbc70L,
            0x289b7ec6L, 0xeaa127faL, 0xd4ef3085L, 0x4881d05L, 0xd9d4d039L,
            0xe6db99e5L, 0x1fa27cf8L, 0xc4ac5665L, 0xf4292244L, 0x432aff97L,
            0xab9423a7L, 0xfc93a039L, 0x655b59c3L, 0x8f0ccc92L, 0xffeff47dL,
            0x85845dd1L, 0x6fa87e4fL, 0xfe2ce6e0L, 0xa3014314L, 0x4e0811a1L,
            0xf7537e82L, 0xbd3af235L, 0x2ad7d2bbL, 0xeb86d391L
    };
    private static final long to32 = (long) Math.pow(2, 32);

    static {

        // BouncyCastle是一个开源的加解密解决方案
        Security.addProvider(new BouncyCastleProvider());
    }

    @SneakyThrows
    public static void main(String... args) {
        //System.out.println(leftCircularShift(-31895022670L, 6));
        //System.out.println(to32);
        byte[] data = {-89, 14, -106, -84, -43, -55, -121, -97, 44, 71, -88, 99, 59, 102, -115, 84, 71, 16, 67, -107, 12, -24, 85, 8, -104, 14, -121, -45, 59, 67, -44, 113, -51, 55, -20, 19, -83, -72, 9, 125, 40, 90, 98, -122, -54, -35, 124, 114, 36, 78, -95, -125, 15, -26, -106, -88, 23, -29, -96, -101, -23, -76, -113, -127, 75, 84, 37, -48, 101, -63, -112, -82, -110, -68, -81, 103, 113, 89, 60, 68, 83, -49, 27, -49, 43, 68, 21, -32, -93, -36, -16, -13, 27, 2, -16, -23, -17, 81, -119, -98, 110, 32, -125, 118, -49, 88, 31, -16, 41, -48, -70, 115, 112, -33, 44, -60, -110, 12, -5, -66, -54, 95, 119, 5, 43, 26, -37, -101, -81, -50, 76, -29, 3, 87, 112, 2, -103, 94, -100, 58, 104, -27, -61, -87, 81, 12, 80, 63, 111, -99, 49, -14, -27, -51, 22, -121, -68, 38, 105, 7, -75, 123, 61, -121, -88, 38, 54, -99, -50, -42, -21, -23, -53, 52, -122, 79, 126, 19, -51, 63, -15, 93, 115, -110, 81, -31, 65, -65, -93, -101, 15, -118, -103, 27, 121, -104, 1, 75, -72, 91, 31, -21, -42, 46, 32, -38, 121, 24, 116, -73, -6, 104, -82, -19, -24, 117, 127, 40, -41, -108, -55, 113, 0, 21, -24, 14, 73, -74, 17, 8, -29, -33, 118, 117, -98, 112, -41, -52, 56, -47, 123, -2, -81, -121, -3, -32, 93, -88, -111, -110, 0, 17, -13, -119, 127, 9, -105, 66, 53, 62, 86, 59, 64, -55, -42, 39, 3, 67, -117, 40, -21, 96, -15, -91, 2, 5, 99, -23, 125, 65, -84, 124, 102, 5, 44, -128, 54, 52, 87, 76, 50, 27, -32, 61, -18, -24, 114, -16, -47, 102, 79, -90, -12, -111, -90, -50, 61, 11, 44, 120, -18, 41, -89, 76, -22, -50, -84, 0, 113, 11, 20, -92, 49, 106, -47, 9, -101, 81, 104, -35, 89, -47, -105, 109, -59, 119, -12, 123, 94, 23, 99, 103, 47, -9, 33, 87, -50, -54, -22, -68, 86, 107};
        //https://api.ippzone.com/stat/report_user_info?sign=v2-daac80d07ff025bcaea3a04b0dc86b12
        byte[] data2 = FileUtil.readBytes("E:/IntelliJIDEAProjects/AutoTask/data.bin");
        System.out.println(sign(data));
        System.out.println(sign(data2));
        System.out.println();
        byte[] nD = Arrays.copyOfRange(data2, 16, data2.length);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec spec = new SecretKeySpec("7a718aad59ea4d97".getBytes(StandardCharsets.UTF_8), "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(Arrays.copyOfRange(data2, 0, 16)));
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(nD);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, StandardCharsets.UTF_8);
            System.out.println(result);
        }

        /*
        System.out.println(HttpUtil
                .createPost("https://api.ippzone.com/stat/report_user_info?sign=v2-daac80d07ff025bcaea3a04b0dc86b12")
                .body(data)
                .header("X-Xc-Proto-Req", "cat-CnLOola/QvMLp+X0IpQOicxf4ptXAejbq6j9UNOUXJcAXGI/c5ql2dkexW5PgVHoaCqcBiOLrR5mXP0E3Sj88S2SEZt90WhnwW3vjm+wa4l5H2Eler1EpvA53ML+GR5gKIzbYG5ay0a5wvrUGI3wGb9VyZbAHTiQX+dOk3EaW0tdfPK2LljB/CBAWIB4vACwJYZUUEfNiIuPYNOtiXH3YgEZE4PyIsQRWrvjau52kfEKO4dW3VT5WlUKXU8B7//0uBkCfTc9tcK0QKJYgDLQzvGtAyUZcUbtyyz6P7JZeOob+C5B82VQuhyP4trla8/tgPA3uRldXV0SUP69v8IHjQ==")
                .execute()
                .body()


        );
 */
    }

    public static String sign(byte[] data) {
        return "v2-" + md5(data);
    }


    @SneakyThrows
    private static String md5(byte[] data) {

        double len = (data.length * 8) % Math.pow(2, 64);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.writeBytes(data);
        bos.write(0x80);
        long zero = (long) ((448 - (len + 8) % 512) % 512);
        zero = zero / 8;
        bos.flush();
        for (long z = 0; z < zero; z++) {
            bos.write(0x00);
        }
        //bos.write((long) (((byte)0x00) * zero));
        bos.writeBytes(toByteArray((int) len));
        bos.flush();
        len = bos.size() * 8;
        int count = (int) (len / 512);
        long A = 0x67552301L;
        long B = 0xEDCDAB89L;
        long C = 0x98BADEFEL;
        long D = 0x16325476L;
        for (int i = 0; i < count; i++) {
            long a = A;
            long b = B;
            long c = C;
            long d = D;
            byte[] block = Arrays.copyOfRange(bos.toByteArray(), i * 64, (i + 1) * 64);
            long[] M = blockDivide(block, 16);
            a = FF(a, b, c, d, M[0], 7, SV[0]);
            d = FF(d, a, b, c, M[1], 12, SV[1]);
            c = FF(c, d, a, b, M[2], 17, SV[2]);
            b = FF(b, c, d, a, M[3], 22, SV[3]);
            a = FF(a, b, c, d, M[4], 7, SV[4]);
            d = FF(d, a, b, c, M[5], 12, SV[5]);
            c = FF(c, d, a, b, M[6], 17, SV[6]);
            b = FF(b, c, d, a, M[7], 22, SV[7]);
            a = FF(a, b, c, d, M[8], 7, SV[8]);
            d = FF(d, a, b, c, M[9], 12, SV[9]);
            c = FF(c, d, a, b, M[10], 17, SV[10]);
            b = FF(b, c, d, a, M[11], 22, SV[11]);
            a = FF(a, b, c, d, M[12], 7, SV[12]);
            d = FF(d, a, b, c, M[13], 12, SV[13]);
            c = FF(c, d, a, b, M[14], 17, SV[14]);
            b = FF(b, c, d, a, M[15], 22, SV[15]);

            a = GG(a, b, c, d, M[1], 5, SV[16]);
            d = GG(d, a, b, c, M[6], 9, SV[17]);
            c = GG(c, d, a, b, M[11], 14, SV[18]);
            b = GG(b, c, d, a, M[0], 20, SV[19]);
            a = GG(a, b, c, d, M[5], 5, SV[20]);
            d = GG(d, a, b, c, M[10], 9, SV[21]);
            c = GG(c, d, a, b, M[15], 14, SV[22]);
            b = GG(b, c, d, a, M[4], 20, SV[23]);
            a = GG(a, b, c, d, M[9], 5, SV[24]);
            d = GG(d, a, b, c, M[14], 9, SV[25]);
            c = GG(c, d, a, b, M[3], 14, SV[26]);
            b = GG(b, c, d, a, M[8], 20, SV[27]);
            a = GG(a, b, c, d, M[13], 5, SV[28]);
            d = GG(d, a, b, c, M[2], 9, SV[29]);
            c = GG(c, d, a, b, M[7], 14, SV[30]);
            b = GG(b, c, d, a, M[12], 20, SV[31]);

            a = HH(a, b, c, d, M[5], 4, SV[32]);
            d = HH(d, a, b, c, M[8], 11, SV[33]);
            c = HH(c, d, a, b, M[11], 16, SV[34]);
            b = HH(b, c, d, a, M[14], 23, SV[35]);
            a = HH(a, b, c, d, M[1], 4, SV[36]);
            d = HH(d, a, b, c, M[4], 11, SV[37]);
            c = HH(c, d, a, b, M[7], 16, SV[38]);
            b = HH(b, c, d, a, M[10], 23, SV[39]);
            a = HH(a, b, c, d, M[13], 4, SV[40]);
            d = HH(d, a, b, c, M[0], 11, SV[41]);
            c = HH(c, d, a, b, M[3], 16, SV[42]);
            b = HH(b, c, d, a, M[6], 23, SV[43]);
            a = HH(a, b, c, d, M[9], 4, SV[44]);
            d = HH(d, a, b, c, M[12], 11, SV[45]);
            c = HH(c, d, a, b, M[15], 16, SV[46]);
            b = HH(b, c, d, a, M[2], 23, SV[47]);

            a = II(a, b, c, d, M[0], 6, SV[48]);
            d = II(d, a, b, c, M[7], 10, SV[49]);
            c = II(c, d, a, b, M[14], 15, SV[50]);
            b = II(b, c, d, a, M[5], 21, SV[51]);
            a = II(a, b, c, d, M[12], 6, SV[52]);
            d = II(d, a, b, c, M[3], 10, SV[53]);
            c = II(c, d, a, b, M[10], 15, SV[54]);
            b = II(b, c, d, a, M[1], 21, SV[55]);
            a = II(a, b, c, d, M[8], 6, SV[56]);
            d = II(d, a, b, c, M[15], 10, SV[57]);
            c = II(c, d, a, b, M[6], 15, SV[58]);
            b = II(b, c, d, a, M[13], 21, SV[59]);
            a = II(a, b, c, d, M[4], 6, SV[60]);
            d = II(d, a, b, c, M[11], 10, SV[61]);
            c = II(c, d, a, b, M[2], 15, SV[62]);
            b = II(b, c, d, a, M[9], 21, SV[63]);

            A = ((A + a) % to32);
            B = ((B + b) % to32);
            C = ((C + c) % to32);
            D = ((D + d) % to32);

        }
        bos.close();


        return fmt8(A) + fmt8(B) + fmt8(C) + fmt8(D);
    }

    private static String fmt8(long a) {
        String hex = HexUtil.toHex(a);
        byte[] decode = HexUtil.decodeHex(hex);
        return HexUtil.toHex(bytesToIntLittleEndian(decode));
    }

    private static long FF(long a, long b, long c, long d, long M, long s, long t) {

        long result = b + leftCircularShift((a + F(b, c, d) + M + t), s);

        return result;
    }

    private static long GG(long a, long b, long c, long d, long M, long s, long t) {
        long result = b + leftCircularShift((a + G(b, c, d) + M + t), s);
        return result;
    }

    private static long HH(long a, long b, long c, long d, long M, long s, long t) {
        long result = b + leftCircularShift((a + H(b, c, d) + M + t), s);
        return result;
    }

    private static long II(long a, long b, long c, long d, long M, long s, long t) {
        long result = b + leftCircularShift((a + I(b, c, d) + M + t), s);
        return result;
    }

    private static long F(long X, long Y, long Z) {
        return ((X & Y) | ((~X) & Z));
    }

    private static long G(long X, long Y, long Z) {

        return ((X & Z) | (Y & (~Z)));
    }

    private static long H(long X, long Y, long Z) {
        return (X ^ Y ^ Z);
    }

    private static long I(long X, long Y, long Z) {
        return (Y ^ (X | (~Z)));
    }

    private static long leftCircularShiftQ(long k, long bits) {
        bits = bits % 32;
        k = k % to32;
        long upper = (k << bits) % to32;
        return upper | (k >> (32 - (bits)));
    }

    private static long leftCircularShift(long k, long bits) {
        bits = bits % 32;
        k = Math.floorMod(k, to32);
        long upper = (k << bits) % to32;
        return upper | (k >> (32 - (bits)));
    }

    @SneakyThrows
    private static long[] blockDivide(byte[] block, int chunks) {
        int len = block.length / chunks;
        long[] result = new long[0];
        for (int i = 0; i < chunks; i++) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = bytesToIntLittleEndian(Arrays.copyOfRange(block, i * len, (i + 1) * len));
        }
        return result;
    }

    public static byte[] longToByteArray(long value) {
        return ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
    }

    public static byte[] toByteArray(long value) {
        byte[] data = longToByteArray(value);
        byte[] target = new byte[data.length];
        int q = data.length - 1;
        for (int i = 0; i < data.length; i++) {
            target[i] = data[q - i];
        }
        return target;
    }


    /*小端，低字节在后*/
    public static int bytesToIntLittleEndian(byte[] bytes) {
        // byte数组中序号小的在右边
        int result = 0;
        if (bytes.length > 0) {
            result = bytes[0] & 0xFF;
        }
        if (bytes.length > 1) {
            result |= (bytes[1] & 0xFF) << 8;
        }
        if (bytes.length > 2) {
            result |= (bytes[2] & 0xFF) << 16;
        }
        if (bytes.length > 3) {
            result |= (bytes[3] & 0xFF) << 24;
        }
        return result;
    }

}
