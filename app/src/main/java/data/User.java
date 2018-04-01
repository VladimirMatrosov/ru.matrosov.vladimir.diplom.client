package data;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Integer userID;
    private String firstName;
    private String lastName;
    private String email;
    private String post;
    private String password;
    private Set relations = new HashSet();
    private Set messages = new HashSet();

    public Set getRelations() {
        return relations;
    }

    public void setRelations(Set messages) {
        this.relations = messages;
    }

    public User(){

    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userId) {
        this.userID = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set getMessages() {
        return messages;
    }

    public void setMessages(Set messages) {
        this.messages = messages;
    }

    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", post='" + post + '\'' +
                ", password='" + password + '\'' +
                ", relations=" + relations +
                ", messages=" + messages +
                '}';
    }

    public String write(){
        return "Имя: " + firstName + '\'' + ", Фамилия: " + lastName + '\''
                + ", email: " + email + '\'' + ", Должность: " + post;
    }
}
