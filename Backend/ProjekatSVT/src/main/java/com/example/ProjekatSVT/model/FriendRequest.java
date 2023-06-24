package com.example.ProjekatSVT.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendRequests")
@NoArgsConstructor
@Getter
@Setter
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Boolean approved;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime at;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "by_user")
    private User byUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "for_user")
    private User forUser;

}