/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Minato
 */
 
public class test6 {
    public static final String FILE_IP = "C:\\Users\\minat\\Desktop\\amazon.txt";
    public static final String FILE_BIG_IP = "C:\\Users\\minat\\Desktop\\all.txt";
    public static final String REGEX_FIND_IPV4 = "((2[0-4]\\d|25[0-5]|1\\d\\d|0\\d\\d|0\\d|\\d\\d|\\d)[\\.]){3}(2[0-4]\\d|25[0-5]|1\\d\\d|0\\d\\d|0\\d|\\d\\d|\\d)\\/\\d*";

    // Input file text
    private static String inputText(String filePath) {

        String text1 = "";
        String textFile = "";

        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));

            while ((text1 = input.readLine()) != null) {
                textFile += text1 + "\n";
            }

        } catch (IOException e) {
            System.out.println("Error input file text...");
        }

        return textFile;
    }

    private static ArrayList findStringIPAddress(String textFile) {

        ArrayList<String> arrayList = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX_FIND_IPV4);
        Matcher matcher = pattern.matcher(textFile);

        int count = 0;

        while (matcher.find()) {
            arrayList.add(matcher.group());
        }

        return arrayList;
    }
    
    public static void main(String[] args) {
        String str = inputText(FILE_IP);
        ArrayList<String> arrayList = new ArrayList();
        arrayList = findStringIPAddress(str);
        System.out.println(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}
