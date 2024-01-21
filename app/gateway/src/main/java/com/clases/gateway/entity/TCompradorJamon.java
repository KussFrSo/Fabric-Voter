package com.clases.gateway.entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_compradorjamon")
@Entity
@NoArgsConstructor
@ToString
public class TCompradorJamon implements Serializable {
    @Id
    @SequenceGenerator(name = "compradorjamon_sequence",
            sequenceName = "compradorjamon_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "compradorjamon_sequence"
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dni")
    private String dni;

    @Column(name = "email")
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public TCompradorJamon(String name, String dni, String email) {
        this.name = name;
        this.dni = dni;
        this.email = email;
    }
}
