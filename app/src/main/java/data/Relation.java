package data;

public class Relation {
    private Integer relationID;
    private Integer chatroomID;
    private Integer userID;

    public Relation(){}

    public Relation(Integer idChat, Integer idUser){
        this.relationID = null;
        this.chatroomID = idChat;
        this.userID = idUser;
    }

    public Integer getRelationID() {
        return relationID;
    }

    public void setRelationID(Integer relationID) {
        this.relationID = relationID;
    }

    public Integer getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(Integer chatroomID) {
        this.chatroomID = chatroomID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "relationID=" + relationID +
                ", chatroomID=" + chatroomID +
                ", userID=" + userID +
                '}';
    }
}
