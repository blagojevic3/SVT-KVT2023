package com.example.ProjekatSVT.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_admins")
public class GroupAdmin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER) //    ne moze mappedBy!!!
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;         ///svoj id

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupAdmin")
//    private Set<Banned> bans = new HashSet<Banned>();
}
