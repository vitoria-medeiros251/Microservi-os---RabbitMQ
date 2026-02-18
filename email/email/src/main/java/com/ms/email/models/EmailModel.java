package com.ms.email.models;

import com.ms.email.enums.StatusEmail;
import javax.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_email")
public class EmailModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "email_id")
    private UUID emailId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "email_from")
    private String emailFrom;
    @Column(name = "email_to", nullable = false)
    private String emailTo;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;
    @Column(name = "send_date_email")
    private LocalDateTime sendDateEmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_email", nullable = false)
    private StatusEmail statusEmail;

    public EmailModel() {}

    public UUID getEmailId() { return emailId; }
    public void setEmailId(UUID emailId) { this.emailId = emailId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getEmailFrom() { return emailFrom; }
    public void setEmailFrom(String emailFrom) { this.emailFrom = emailFrom; }
    public String getEmailTo() { return emailTo; }
    public void setEmailTo(String emailTo) { this.emailTo = emailTo; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public LocalDateTime getSendDateEmail() { return sendDateEmail; }
    public void setSendDateEmail(LocalDateTime sendDateEmail) { this.sendDateEmail = sendDateEmail; }
    public StatusEmail getStatusEmail() { return statusEmail; }
    public void setStatusEmail(StatusEmail statusEmail) { this.statusEmail = statusEmail; }
}