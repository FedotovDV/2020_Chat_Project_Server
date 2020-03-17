import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Client implements Serializable {

    private String userName;
    private transient char[] userPassword;
    private int userId;
    private transient int countUser;

    public Client() {
        userId = countUser++;
    }

    public Client(String userName, char[] userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        userId = countUser++;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char[]  getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(char[]  userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getUserId() == client.getUserId() &&
                countUser == client.countUser &&
                getUserName().equals(client.getUserName()) &&
                Arrays.equals(getUserPassword(), client.getUserPassword());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getUserName(), getUserId(), countUser);
        result = 31 * result + Arrays.hashCode(getUserPassword());
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword=" + Arrays.toString(userPassword) +
                ", userId=" + userId +
                '}';
    }
}
