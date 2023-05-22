package model;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "groupAdmin")
public class GroupAdmin extends User{

    @ManyToOne(fetch = FetchType.EAGER)             //    ne moze mappedBy!!!
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="groupId")
    private Group group;
}
