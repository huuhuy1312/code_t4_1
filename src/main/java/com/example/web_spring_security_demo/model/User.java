package com.example.web_spring_security_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_roles",
               joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<Book> books;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<BorrowedBook> borrowedBooks;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Comment>  comments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<SentEmail> emailList;
    @Override
    public boolean equals(Object o){
        if(this ==o)return  true;
        if(o == null || getClass()!= o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username,user.username) && Objects.equals(email,user.email);
    }

    @Override
    public int hashCode(){
        return Objects.hash(username,email);
    }
}
