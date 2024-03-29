package com.example.ProjekatSVT.model;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Integer id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "isDeleted")
    private Boolean isDeleted;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post post;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentComment")
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id")
    private Comment parentComment;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Reaction> reactions = new HashSet<Reaction>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private Set<Report> reports = new HashSet<Report>();
}
