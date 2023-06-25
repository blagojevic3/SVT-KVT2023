package com.example.ProjekatSVT.model;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="administrators")
public class Administrator extends User{


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private Set<Banned> bans = new HashSet<Banned>();


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;







//    @Id
//    @OneToOne(cascade = CascadeType.ALL)
//    @MapsId
//    @JoinColumn(name="userId")
//    private User user;




}
