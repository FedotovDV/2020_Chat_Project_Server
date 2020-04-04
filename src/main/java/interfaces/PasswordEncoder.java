package interfaces;

public interface PasswordEncoder {
    String hashPassword(char[] pass) throws Exception;

    boolean checkPassword(char[] pass) throws Exception;

    boolean checkPassword(String hashPass);
}
