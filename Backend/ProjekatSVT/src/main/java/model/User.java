package model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name="username", unique = true,nullable = false)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email", unique = true, nullable = false)
    private String email;


    @Column(name="lastLogin",nullable = false)
    private LocalDateTime lastlogin;

    @Column(name = "firstName",nullable = false)
    private  String firstName;

    @Column(name = "lastName",nullable = false)
    private  String lastName;

    public User(Integer id, String username, String password, String email, LocalDateTime lastlogin, String firstName, String lastName) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastlogin = lastlogin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDateTime lastlogin) {
        this.lastlogin = lastlogin;
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


}
