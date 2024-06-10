package com.example.web_spring_security_demo.services;

import com.example.web_spring_security_demo.model.Book;
import com.example.web_spring_security_demo.model.Category;
import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.AddBookRequest;
import com.example.web_spring_security_demo.repository.BookRepository;
import com.example.web_spring_security_demo.repository.CategoryRepository;
import com.example.web_spring_security_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    public String addBook (AddBookRequest addBookRequest){
        Category category = categoryRepository.findById(addBookRequest.getCategory_id()).orElse(null);
        if(category ==null){
            return "Category not exist";
        }
        User user = userRepository.findById(addBookRequest.getSeller_id()).orElse(null);
        if (user==null){
            return "User not exist";
        }
        Book book = new Book();
        book.setName(addBookRequest.getName());
        book.setDescription(addBookRequest.getDescription());
        book.setPrice(addBookRequest.getPrice());
        book.setCategory(category);
        book.setSeller(user);
        bookRepository.save(book);
//        System.out.println(addBookRequest.getCategory_id());
//        System.out.println(addBookRequest.getSeller_id());
//        System.out.println(addBookRequest.getName());
//        System.out.println(addBookRequest.getPrice().getClass());
        return "Thêm book thành công";
    }

}
