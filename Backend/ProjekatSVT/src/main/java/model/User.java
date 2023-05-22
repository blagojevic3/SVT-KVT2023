package model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="user")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",unique = true,nullable = false)

    private Integer userId;

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



    @OneToMany(fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private Set<GroupAdmin> groupAdmins;

    public User(Integer userId, String username, String password, String email, LocalDateTime lastlogin, String firstName, String lastName) {
        super();
        this.userId = userId;
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
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
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
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


}
