package com.example.web_spring_security_demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {
    private String name;
    private String description;
    private Long price;
    private Long category_id;
    private Long seller_id;
}
