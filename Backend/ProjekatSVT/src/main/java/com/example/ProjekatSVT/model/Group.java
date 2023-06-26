package com.example.ProjekatSVT.model;




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
@Table(name="social_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Integer id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "creationDate",nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "isSuspended",nullable = false)
    private Boolean isSuspended;

    @Column(name = "suspendedReason")
    private String suspendedReason;
//
//    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY)
//    private Set<GroupAdmin> groupAdmins =new HashSet<GroupAdmin>();
//
//
//    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY)
//    private Set<Post> posts = new HashSet<Post>();   //contains
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
//    private Set<Banned> bans = new HashSet<Banned>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
//    private Set<GroupRequest> groupRequests = new HashSet<GroupRequest>();



}
