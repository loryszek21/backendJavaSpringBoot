package com.example.userapp;
import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

//@XmlRootElement(name = "users")

//@XmlRootElement(name = "users" , namespace = "")
public class UsersList {
    @JacksonXmlProperty(localName = "user")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Users> users;


//    @XmlElement(name = "users")
//    @XmlElementWrapper(name = "user")
    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
    public void printUsers() {
        if (users != null) {
            for (Users user : users) {
                System.out.println("User: " + user.getName() + " " + user.getSurname() + " " + user.getLogin());
            }
        }
    }
}
