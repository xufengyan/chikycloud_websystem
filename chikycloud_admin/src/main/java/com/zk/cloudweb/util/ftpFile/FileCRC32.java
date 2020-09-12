package com.zk.cloudweb.util.ftpFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @author xf
 * @version 1.0
 * @date 2020/9/10 9:27
 */
public class FileCRC32 {

    public static String getCRC32(InputStream inputStream) {
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkedinputstream = null;
        String crc = null;
        try {
            checkedinputstream = new CheckedInputStream(inputStream, crc32);
            while (checkedinputstream.read() != -1) {
            }
            crc = Long.toHexString(crc32.getValue()).toUpperCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (checkedinputstream != null) {
                try {
                    checkedinputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return crc;
    }


}
