
package com.example.vaccination_management.entity;

import javax.persistence.*;

@Entity
@Table(name = "account_role")
public class AccountRole{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AppRole role;


    public AccountRole() {
    }

    public AccountRole(Account account,  AppRole role) {
        this.account = account;
        this.role = role;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Account getAccount() {
        return account;
    }

    public void setAccount( Account account) {
        this.account = account;
    }

    public  AppRole getRole() {
        return role;
    }

    public void setRole( AppRole role) {
        this.role = role;
    }
}

