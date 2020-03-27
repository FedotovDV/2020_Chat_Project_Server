package utility;

import lombok.*;
import java.security.MessageDigest;

@Getter
public class PasswordEncoding {

    private String hashPass;


    public String hashPassword(char[] pass) throws Exception {
        this.hashPass = byteArrayToHexString(computeHash(String.valueOf(pass)));
        return hashPass;
    }

    public boolean checkPassword(char[] pass) throws Exception {
        String inputHash = byteArrayToHexString(computeHash(String.valueOf(pass)));
        if (this.hashPass.equals(inputHash)) {
            return true;
        }
        return false;
    }


    public boolean checkPassword(String hashPass) {
               return this.hashPass.equals(hashPass);
    }

    public static byte[] computeHash(String pass)
            throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(pass.getBytes());
        return messageDigest.digest();
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            int var = aByte & 0xff;
            if (var < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(var));
        }
        return stringBuilder.toString().toUpperCase();
    }

}
