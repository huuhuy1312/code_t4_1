package com.example.web_spring_security_demo.services;

import com.example.web_spring_security_demo.model.Book;
import com.example.web_spring_security_demo.model.BorrowedBook;
import com.example.web_spring_security_demo.model.User;
import com.example.web_spring_security_demo.payload.request.AddBorrowedBookRequest;
import com.example.web_spring_security_demo.payload.response.ResponseEmail;
import com.example.web_spring_security_demo.repository.BookRepository;
import com.example.web_spring_security_demo.repository.BorrowedBookRepository;
import com.example.web_spring_security_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedBookService {
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    public String addBorrowedBook(AddBorrowedBookRequest addBorrowedBookRequest)
    {
        Book book = bookRepository.findById(addBorrowedBookRequest.getBook_id()).orElse(null);
        if(book==null){
            return "Không có book có id="+addBorrowedBookRequest.getBook_id();
        }
        User user = userRepository.findById(addBorrowedBookRequest.getUser_id()).orElse(null);
        if(user ==null){
            return "Không tìm thấy user có id="+addBorrowedBookRequest.getUser_id();
        }
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);
        Date today = new Date();
        borrowedBook.setBorrowDate(today);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR,addBorrowedBookRequest.getNum_of_days_borrow());
        Date dueDate = calendar.getTime();
        borrowedBook.setDueDate(dueDate);
        borrowedBook.setCustomer(user);
        borrowedBookRepository.save(borrowedBook);
        return "Mượn sách thành công";
    }
    public List<ResponseEmail> getOverdueAndNearDueBooks() {
        List<Object[]> results = borrowedBookRepository.getOverdueAndNearDueBooks();
        System.out.println(results.get(0)[2]);
        return results.stream()
                .map(result -> new ResponseEmail(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2]
                ))
                .collect(Collectors.toList());
    }

}
