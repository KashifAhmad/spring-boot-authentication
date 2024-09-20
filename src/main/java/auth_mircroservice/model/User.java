package auth_mircroservice.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String createdTimeStamp;

    // Define your desired format
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructors
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.createdTimeStamp = LocalDateTime.now().format(FORMATTER);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }
}
