package app;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by gleb on 2/26/2017.
 */
public class Sha1HackTest {

    @Test
    public void testSha1IsBroken() throws Exception {
        // success
        testSha1("one string".getBytes(), "another string".getBytes());
        testSha1("same strings".getBytes(), "same strings".getBytes());


        // fail
        byte[] pdf1 = readBytes("shattered-1.pdf");
        byte[] pdf2 = readBytes("shattered-2.pdf");

        System.out.println("are pdfs the same? " + Arrays.equals(pdf1, pdf2));
        testSha1(pdf1, pdf2);
    }


    private void testSha1(byte[] data1, byte[] data2) {
        String data1Hashed = DigestUtils.sha1Hex(data1);
        String data2Hashed = DigestUtils.sha1Hex(data2);
        if (Arrays.equals(data1, data2)) {
            assertEquals(data1Hashed, data2Hashed);
        } else {
            assertNotEquals(data1Hashed, data2Hashed);
        }
    }

    private byte[] readBytes(String filePath) throws Exception {
        Path doc = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());
        return Files.readAllBytes(doc);
    }

}
