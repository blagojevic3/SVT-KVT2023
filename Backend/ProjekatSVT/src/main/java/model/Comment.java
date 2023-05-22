package model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "comment")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId",nullable = false,unique = true)
    private Integer commentId;

    @Column(name = "text")
    private String text;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post post;
}
