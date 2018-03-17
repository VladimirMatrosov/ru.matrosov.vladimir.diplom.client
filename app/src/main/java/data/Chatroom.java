package data;


import java.util.HashSet;
import java.util.Set;

public class Chatroom {
    private Integer chatroomID;
    private String name;
    private Set relations = new HashSet();
    private Set messages = new HashSet();

    public Chatroom() {

    }

    public Chatroom(String name){
        this.name = name;
    }

    public Integer getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(Integer chatroomID) {
        this.chatroomID = chatroomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getRelations() {
        return relations;
    }

    public void setRelations(Set relations) {
        this.relations = relations;
    }

    public Set getMessages() {
        return messages;
    }

    public void setMessages(Set messages) {
        this.messages = messages;
    }

    public String toString() {
        return "Chatroom{" + "chatroomID=" + chatroomID + ", name=" + name + '}';
    }
}
