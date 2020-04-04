package utility;

import lombok.*;
import java.security.MessageDigest;

@Getter
public class PasswordEncoding implements PasswordEncoder {

    private String hashPass;


    @Override
    public String hashPassword(char[] pass) throws Exception {
        this.hashPass = byteArrayToHexString(computeHash(String.valueOf(pass)));
        return hashPass;
    }

    @Override
    public boolean checkPassword(char[] pass) throws Exception {
        String inputHash = byteArrayToHexString(computeHash(String.valueOf(pass)));
        if (this.hashPass.equals(inputHash)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean checkPassword(String hashPass) {
               return this.hashPass.equals(hashPass);
    }

    private static byte[] computeHash(String pass)
            throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(pass.getBytes());
        return messageDigest.digest();
    }

    private static String byteArrayToHexString(byte[] bytes) {
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
