package com.atircio.pickpay.entities;

import com.atircio.pickpay.entities.enums.UserType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String fullName;
    @Column(unique = true)
    private String CPF;

    @Column(unique = true)
    private String email;
    private String password;
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> SentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> ReceivedTransactions;


    public User() {

    }

    public User(String fullName, String CPF, String email, String password, BigDecimal balance, UserType userType, List<Transaction> sentTransactions, List<Transaction> receivedTransactions) {
        this.fullName = fullName;
        this.CPF = CPF;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.userType = userType;
        SentTransactions = sentTransactions;
        ReceivedTransactions = receivedTransactions;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Transaction> getSentTransactions() {
        return SentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        SentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return ReceivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        ReceivedTransactions = receivedTransactions;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(fullName, user.fullName) && Objects.equals(CPF, user.CPF) && Objects.equals(email, user.email) && userType == user.userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", CPF='" + CPF + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", userType=" + userType +
                ", SentTransactions=" + SentTransactions +
                ", ReceivedTransactions=" + ReceivedTransactions +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, CPF, email, userType);
    }
}
