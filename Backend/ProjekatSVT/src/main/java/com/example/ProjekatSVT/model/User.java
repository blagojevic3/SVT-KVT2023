package com.example.ProjekatSVT.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)

    private Integer id;

    @Column(name="username", unique = true,nullable = false)
    private String username;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email", unique = true, nullable = false)
    private String email;


    @Column(name="last_login")
    private LocalDateTime lastlogin;

    @Column(name = "first_name",nullable = false)
    private  String firstName;

    @Column(name = "last_name",nullable = false)
    private  String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Post> posts = new HashSet<Post>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
    private Set<GroupAdmin> groupAdmins = new HashSet<GroupAdmin>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentFriend")
    private Set<User> friends = new HashSet<User>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id")
    private User parentFriend;


    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private Set<Banned> bans = new HashSet<Banned>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Comment> comments = new HashSet<Comment>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "byUser")
//    private Set<FriendRequest> sentRequests = new HashSet<FriendRequest>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forUser")
//    private Set<FriendRequest> receivedRequests = new HashSet<FriendRequest>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "byUser")
//    private Set<Report> sentReports = new HashSet<Report>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forUser")
//    private Set<Report> receivedReports = new HashSet<Report>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private Set<GroupRequest> groupRequests = new HashSet<GroupRequest>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    private Set<Reaction> reactions = new HashSet<Reaction>();



}
