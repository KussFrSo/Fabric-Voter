package com.clases.gateway.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user")
@Entity
@NoArgsConstructor
@ToString
public class TUser implements Serializable {
    @Id
    @Getter
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "dni")
    private String dni;

    @Getter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Column(name = "hasDonated")
    private Boolean hasDonated;

    @Getter
    @Column(name = "ledgerId")
    private String ledgerId;

    public TUser(String name, String dni, String email, String password, Boolean hasDonated, String ledgerId) {
        this.name = name;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.hasDonated = hasDonated;
        this.ledgerId = ledgerId;
    }
}
