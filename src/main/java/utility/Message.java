package utility;

import client.Client;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Message {

    private int idMessage;
    private String messageText;
    private  int idUser;
    private  int idTopic;
    private int idRecipient;

    public Message (String messageText, Client client){
        this.messageText = messageText;
        this.idUser = client.getUserId();
    }

}
