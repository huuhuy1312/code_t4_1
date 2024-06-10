package com.example.web_spring_security_demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBorrowedBookRequest {
    private Long book_id;
    private Long user_id;
    private int num_of_days_borrow;
}
