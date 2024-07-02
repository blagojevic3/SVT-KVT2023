package com.example.ProjekatSVT.model;




import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="groups")
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
    private LocalDate creationDate;

    @Column(name = "isSuspended",nullable = false)
    private Boolean isSuspended;

    @Column(name = "suspendedReason")
    private String suspendedReason;
//
    @OneToMany(mappedBy = "group",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<GroupAdmin> groupAdmins =new HashSet<GroupAdmin>();


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<Post> posts = new ArrayList<>();   //contains

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private DummyTable file;









//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
//    private Set<Banned> bans = new HashSet<Banned>();
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
//    private Set<GroupRequest> groupRequests = new HashSet<GroupRequest>();



}
