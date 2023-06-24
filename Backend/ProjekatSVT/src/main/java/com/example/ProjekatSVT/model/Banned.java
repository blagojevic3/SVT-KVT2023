package com.example.ProjekatSVT.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Inheritance(strategy = InheritanceType.JOINED)
//@PrimaryKeyJoinColumn(name = "userId")



@Entity
@Table(name = "banned")
@NoArgsConstructor
@Getter
@Setter
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private Administrator admin;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupAdmin_id")
    private GroupAdmin groupAdmin;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;


}


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private User user;              //towards
//
//    @Column(name = "timestamp")
//    private LocalDate timestamp;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Group group;        //for
//
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private GroupAdmin groupAdmin;  //by
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Administrator administrator;  //by
