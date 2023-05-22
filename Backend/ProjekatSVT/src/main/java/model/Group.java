package model;


import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId",unique = true,nullable = false)
    private Integer groupId;

    @Column(name="name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "isSuspended")
    private Boolean isSuspended;

    @Column(name = "suspendedReason")
    private String suspendedReason;

    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY)
    private Set<GroupAdmin> groupAdmins;

    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY)
    private Set<Post> posts;   //contains



}
