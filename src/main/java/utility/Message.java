package utility;

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
    private int idRecepient;

    public Message (String messageText){
        this.messageText = messageText;
    }

}
