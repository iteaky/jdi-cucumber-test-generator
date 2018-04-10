package com.epam.test_generator.entities;

import com.epam.test_generator.entities.api.TokenTrait;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * This class represents token entity. Token is a special key that is used for user identification.
 */
@Entity
public class Token implements TokenTrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String tokenString;

    @OneToOne
    private User user;

    @NotNull
    private Date expiryDate;

    public static Token withExpiryDuration(Integer minutes) {
        Token token = new Token();
        token.tokenString = UUID.randomUUID().toString();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        token.expiryDate = now.getTime();
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return tokenString;
    }

    public void setToken(String tokenString) {
        this.tokenString = tokenString;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

}
