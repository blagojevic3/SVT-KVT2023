package model;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="administrator")
public class Administrator implements Serializable{
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="userId")
    private User user;




}
