import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Client implements Serializable {

    private String userName;
    private transient String userPassword;
    public Client() {
    }
}
