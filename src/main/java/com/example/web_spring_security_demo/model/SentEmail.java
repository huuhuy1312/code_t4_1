package com.example.web_spring_security_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    private ETypeEmail typeEmail;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSend;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

}
