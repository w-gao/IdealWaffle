package io.github.wgao.idealwaffle.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * IdealWaffle
 *
 * @author w-gao Copyright (c) 2016
 * @version 1.0
 */
public class FileUtil {

    static void writeFile(File file, InputStream content) throws IOException {
        if (content == null) {
            throw new IllegalArgumentException("content must not be null");
        }
        if (!file.exists()) {
            if (!file.createNewFile()) System.out.println("O.o");
        }
        FileOutputStream stream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = content.read(buffer)) != -1) {
            stream.write(buffer, 0, length);
        }
        stream.close();
    }


    public static String readFile(InputStream inputStream) throws IOException {

        return readFile(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private static String readFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        temp = br.readLine();
        while (temp != null) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(temp);
            temp = br.readLine();
        }
        return stringBuilder.toString();
    }
}
