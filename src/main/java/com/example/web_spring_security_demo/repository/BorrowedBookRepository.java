package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.BorrowedBook;
import com.example.web_spring_security_demo.payload.response.ResponseEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    @Query(value = "SELECT bb.customer.email AS email, " +
            "IFNULL(GROUP_CONCAT(CASE WHEN bb.dueDate <= CURRENT_TIMESTAMP() THEN CONCAT(CHAR(13), CHAR(10), bb.book.name, ' ', bb.dueDate) END), 'No overdue books') AS overdueBooks, " +
            "IFNULL(GROUP_CONCAT(CASE WHEN bb.dueDate >= TIMESTAMPADD(DAY, -1, CURRENT_TIMESTAMP()) THEN CONCAT(CHAR(13), CHAR(10), bb.book.name, ' ', bb.dueDate) END), 'No near-due books') AS nearDueBooks " +
            "FROM BorrowedBook bb " +
            //"WHERE  bb.customer.email.timeSend >= TIMESTAMPADD(DAY, -1, CURRENT_TIMESTAMP()) " +
            "GROUP BY bb.customer.email")
    List<Object[]> getOverdueAndNearDueBooks();





}
