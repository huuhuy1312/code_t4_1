package com.example.web_spring_security_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=100)
    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @NotNull
    private Long price;

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonManagedReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private User seller;


    @OneToOne(mappedBy = "book")
    @JsonBackReference
    private BorrowedBook borrowedBook;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Comment> commentList;

}
