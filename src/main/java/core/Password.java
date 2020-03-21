package core;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Password {


    public void setPassword(byte[] pass) throws Exception {
        byte[] salt = generateSalt(12);
        byte[] input = appendArrays(pass, salt);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashVal = messageDigest.digest(input);
        clearArray(pass);
        clearArray(input);
        saveBytes(salt, "salt.bin");
        saveBytes(hashVal, "password.bin");
        clearArray(salt);
        clearArray(hashVal);

    }

    public boolean checkPassword(byte[] pass) throws Exception {
        byte[] salt = loadBytes("salt.bin");
        byte[] input = appendArrays(pass, salt);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashVal1 = messageDigest.digest(input);
        clearArray(pass);
        clearArray(input);
        byte[] hashVal2 = loadBytes("password.bin");
        boolean arraysEqual = Arrays.equals(hashVal1, hashVal2);
        clearArray(hashVal1);
        clearArray(hashVal2);
        return arraysEqual;
    }

    private byte[] loadBytes(String file) {
        byte[] fileInArray = new byte[file.length()];
        try (FileInputStream reader = new FileInputStream(file)) {
            reader.read(fileInArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInArray;
    }

    private void saveBytes(byte[] bytes, String file) {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {
            for (byte b : bytes) {
                writer.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearArray(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    private byte[] appendArrays(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private byte[] generateSalt(int n) {
        byte[] bytes = new byte[ n];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
