package com.ms.user.models;


import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_users")
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID userId;
    
    @Column(nullable = false, length = 120)
    private String name;
    
    @Column(nullable = false, unique = true, length = 180)
    private String email;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
