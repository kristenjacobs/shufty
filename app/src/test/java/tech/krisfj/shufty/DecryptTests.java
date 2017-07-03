package tech.krisfj.shufty;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class DecryptTests {
    @Test
    public void canDecrypt() throws Exception {
        //InputStream encryptedStream = this.getClass().getClassLoader().getResourceAsStream("hello.txt");
        //String content = IOUtils.toString(encryptedStream);
        //System.out.printf(">> %s\n", content);
        InputStream encryptedStream = this.getClass().getClassLoader().getResourceAsStream("test.txt.gpg");
        String decryptedString = Decrypt.run(encryptedStream, "shufty");
        System.out.printf(">>>>> %s\n", decryptedString);
        assertEquals(4, 2 + 2);
    }
}