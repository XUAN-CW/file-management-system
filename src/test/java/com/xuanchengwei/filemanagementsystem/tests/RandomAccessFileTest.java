//0123456789
//0123456789
//0123456789
//0123456789


package com.xuanchengwei.filemanagementsystem.tests;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HexFormat;

/**
 * @author 禤成伟
 * @date 2022-11-19 - 15:06
 */
public class RandomAccessFileTest {

    @Test
    public void t1() throws IOException {
        byte[] bytes = new byte[1024];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        File bytesTest = new File("bytes.test");
        Files.write(bytesTest.toPath(), bytes);
    }

    @Test
    public void t2() throws IOException {
        String test = "test";
        String hex = Hex.encodeHexString(test.getBytes(StandardCharsets.UTF_8));

        HexFormat.of().formatHex(test.getBytes());
        System.out.println(new String(HexFormat.of().parseHex(hex)));

    }
}
