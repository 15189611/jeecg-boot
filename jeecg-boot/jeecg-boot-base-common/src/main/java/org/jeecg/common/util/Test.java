package org.jeecg.common.util;

import java.io.*;

public class Test {

    public static void main(String[] args) {
        File file = new File("D:\\Ethan\\code\\jeecg-boot\\jeecg-boot\\jeecg-boot-module-system\\src\\main\\resources\\static\\city_code.json");
        try {
            System.out.println(readBytes(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String  readBytes(File file) throws IOException {
        FileInputStream in = null;
        StringBuilder sb = new StringBuilder();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=br.readLine()) != null){
                sb.append(line);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        String result = sb.toString();
        result = result.trim();
        return result;
    }

}
