/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Minato
 */
 
public class FoundIP {
    public static final String FILE_IP = "C:\\Users\\minat\\Desktop\\ip.txt";
    public static final String FILE_BIG_IP = "C:\\Users\\minat\\Desktop\\all.txt";
    public static ArrayList<String> arrayList = new ArrayList();

    // Input file text
    private static String[] inputText(String filePath) {

        String text1 = "";
        String[] textFile = new String[925];

        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            int count = 0;
            while ((text1 = input.readLine()) != null) {
                textFile[count++] = text1;
            }

        } catch (IOException e) {
            System.out.println("Error input file text...");
        }

        return textFile;
    }

    private static boolean checkIP(int[] ipNetwork, int[] ipAddress, int[] ipBroadcast) {

        boolean check = false;
        for (int i = 0; i < 4; i++) {
            if ((ipAddress[i] >= ipNetwork[i]) && (ipAddress[i] <= ipBroadcast[i])) {
                check = true;
            } else {
                check = false;
                break;
            }
        }
        return check;
    }

    private static int[] convertBinaryToDecimal(int[] binary) {

        int[] decimal = new int[4];

        for (int i = 0; i < binary.length; i++) {
            if (i < 8) {
                decimal[0] += binary[i] * Math.pow(2, (7 - i));
            } else {
                if (i < 16) {
                    decimal[1] += binary[i] * Math.pow(2, (15 - i));
                } else {
                    if (i < 24) {
                        decimal[2] += binary[i] * Math.pow(2, (23 - i));
                    } else {
                        decimal[3] += binary[i] * Math.pow(2, (31 - i));
                    }
                }
            }
        }

        return decimal;
    }

    private static int[] findIPAddress(String ipString) {

        String[] str = ipString.split("\\.");

        int[] binaryAddress = new int[32];

        System.arraycopy(convertToBinary(Integer.parseInt(str[0])), 0, binaryAddress, 0, 8);
        System.arraycopy(convertToBinary(Integer.parseInt(str[1])), 0, binaryAddress, 8, 8);
        System.arraycopy(convertToBinary(Integer.parseInt(str[2])), 0, binaryAddress, 16, 8);
        System.arraycopy(convertToBinary(Integer.parseInt(str[3].substring(0, str[3].indexOf('/')))),
                0, binaryAddress, 24, 8);

        return binaryAddress;
    }

    private static int[] findNetwork(int netmask, int[] binaryAddress) {
        int[] binaryNetwork = binaryAddress;
        for (int i = netmask; i < binaryNetwork.length; i++) {
            binaryNetwork[i] = 0;
        }
        return binaryNetwork;
    }

    private static int[] findBroadcast(int netmask, int[] binaryAddress) {
        int[] binaryBroadcast = binaryAddress;
        for (int i = netmask; i < binaryBroadcast.length; i++) {
            binaryBroadcast[i] = 1;
        }
        return binaryBroadcast;
    }

    private static int[] convertToBinary(int number) {
        int[] binary = new int[8];
        for (int i = 7, num = number; i >= 0; i--, num >>>= 1) {
            binary[i] = (num & 1);
        }
        return binary;
    }

    private static void getBigFile() {

        String fileText = "";
        try {
            BufferedReader input = new BufferedReader(new FileReader(FILE_BIG_IP));

            while ((fileText = input.readLine()) != null) {
                //System.out.println("count: " + count);
                arrayList.add(fileText);
            }

        } catch (IOException e) {
            System.out.println("Error input big file...");
        }
    }

    private static int[] convertStringIpToIntIp(String ipString) {

        int[] array = new int[4];

        String[] str = ipString.split("\\.");

        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }

        return array;
    }

    public static void main(String[] args) {

        // Input file text
        String[] ipString = inputText(FILE_IP);
        System.out.println("Da get ip from textFile");

        getBigFile();
        System.out.println("Da add ip to arrayList.");
        System.out.println("Length arrayList: " + arrayList.size());

        for (int i = 0; i < arrayList.size(); i++) {
            
            int count = 0;
            
            for (String ipString1 : ipString) {
                int netmask = Integer.parseInt(ipString1.substring(ipString1.indexOf('/') + 1, ipString1.length()));
                int[] binaryAddress = findIPAddress(ipString1);
                int[] binaryNetwork = findNetwork(netmask, binaryAddress);
                int[] ipNetwork = convertBinaryToDecimal(binaryNetwork);
                int[] binaryBroadcast = findBroadcast(netmask, binaryAddress);
                int[] ipBroadcast = convertBinaryToDecimal(binaryBroadcast);
                boolean check = checkIP(ipNetwork, convertStringIpToIntIp(arrayList.get(i)), ipBroadcast);
                if (check == true) {
                    break;
                }
                count++;
            }
            if (count == ipString.length) {
                System.out.println("Had Found IP: " + arrayList.get(i));
                break;
            }
            
            System.out.println("Line in Big File: " + (i + 1));
        }
    }
}
