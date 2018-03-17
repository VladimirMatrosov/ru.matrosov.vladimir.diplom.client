package data;

import java.util.Date;


public class Message {

    private Integer messsageID;
    private Integer userID;
    private Integer chatroomID;
    private String text;
    private Date date;

    public Message() {

    }

    public Integer getMesssageID() {
        return messsageID;
    }

    public void setMesssageID(Integer messsageId) {
        this.messsageID = messsageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(Integer chatroomID) {
        this.chatroomID = chatroomID;
    }

    public String toString() {
        return "Message{" +
                "messsageID=" + messsageID +
                ", UserID=" + userID +
                ", ChatID=" + chatroomID +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
