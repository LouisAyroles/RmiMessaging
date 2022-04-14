package m2dl.pcr.rmi.domain;


import java.io.Serializable;

public class Message implements Serializable {
    public String sender;
    public String message;
    public String date;

    public Message(String userName, String mess, String hour) {
        sender = userName;
        message = mess;
        date = hour;
    }

}
