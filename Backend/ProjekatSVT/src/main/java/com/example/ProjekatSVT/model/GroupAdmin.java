package com.example.ProjekatSVT.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groupAdmins")
public class GroupAdmin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER) //    ne moze mappedBy!!!
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="groupId")
    private Group group;         ///svoj id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupAdmin")
    private Set<Banned> bans = new HashSet<Banned>();
}
