package com.atircio.pickpay.entities;

import com.atircio.pickpay.entities.enums.NotificationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_NOTIFICATION")
public class Notification {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime sentDate;

    public Notification() {

    }

    public Notification(User user, String message, NotificationStatus status, LocalDateTime sentDate) {
        this.user = user;
        this.message = message;
        this.status = status;
        this.sentDate = sentDate;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    @PrePersist
    public void onPrePersist() {
        this.sentDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Notification that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(message, that.message) && status == that.status && Objects.equals(sentDate, that.sentDate);
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, user, message, status, sentDate);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "user=" + user +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", sentDate=" + sentDate +
                ", id=" + id +
                '}';
    }
}
