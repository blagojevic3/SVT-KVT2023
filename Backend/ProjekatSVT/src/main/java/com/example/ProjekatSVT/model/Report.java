package com.example.ProjekatSVT.model;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@NoArgsConstructor
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private EReportReason reason;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false)
    private Boolean accepted;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "byUser")
    private User byUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forUser")
    private User forUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;
}