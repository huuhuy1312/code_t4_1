package com.example.web_spring_security_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private User customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
}
