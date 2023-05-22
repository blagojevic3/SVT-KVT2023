package model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reaction")

public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reactionId",unique = true,nullable = false)   //nejasno da li je id userid ili reactionid
    private Integer reactionId;

    @Column(name = "type")
    @Enumerated
    private EReactionType type;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentId")
    private Comment comment;

}
