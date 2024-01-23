package com.clases.gateway.entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user")
@Entity
@NoArgsConstructor
@ToString
public class TUser implements Serializable {
    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dni")
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "hasDonated")
    private Boolean hasDonated;

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

    public void setNewPassword(String newPassword){
        this.password = newPassword;
    }
}
