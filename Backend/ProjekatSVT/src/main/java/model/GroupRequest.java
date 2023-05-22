package model;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "groupRequest")
public class GroupRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupReqId",unique = true,nullable = false)
    private Integer groupReqId;

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
