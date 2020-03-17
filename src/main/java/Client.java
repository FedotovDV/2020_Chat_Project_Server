import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;


@Getter
@Setter
@EqualsAndHashCode
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



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword=" + Arrays.toString(userPassword) +
                ", userId=" + userId +
                '}';
    }
}
