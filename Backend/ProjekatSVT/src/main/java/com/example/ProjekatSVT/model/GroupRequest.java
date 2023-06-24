package com.example.ProjekatSVT.model;





import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "groupRequests")
public class GroupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Integer id;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "at")
    private LocalDateTime at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="groupId")
    private Group group;
}
