package com.example.ProjekatSVT.model;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)

    private Integer id;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Comment> commentList = new HashSet<Comment>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Reaction> reactionList = new HashSet<Reaction>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;



}
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
//    private Set<Report> reportList = new HashSet<Report>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
//    private Set<Image> imageList = new HashSet<Image>();
//