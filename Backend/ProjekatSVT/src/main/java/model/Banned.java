package model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "banned")
public class Banned extends User{

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;              //towards

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;        //for


    @ManyToOne(fetch = FetchType.EAGER)
    private GroupAdmin groupAdmin;  //by

    @ManyToOne(fetch = FetchType.EAGER)
    private Administrator administrator;  //by

}
