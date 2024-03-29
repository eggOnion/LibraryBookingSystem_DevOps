package com.example.librarybookingsystem.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.librarybookingsystem.entities.Book;
import com.example.librarybookingsystem.entities.Learner;
import com.example.librarybookingsystem.entities.LoanPeriod;
import com.example.librarybookingsystem.services.BookService;
import com.example.librarybookingsystem.services.LearnerService;
import com.example.librarybookingsystem.services.LoanPeriodService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/loanStatus")
public class LoanPeriodController {

    private LoanPeriodService loanPeriodService;
    private LearnerService learnerService;
    private BookService bookService;

    public LoanPeriodController(LoanPeriodService loanPeriodService, LearnerService learnerService,
            BookService bookService) {
        this.loanPeriodService = loanPeriodService;
        this.learnerService = learnerService;
        this.bookService = bookService;
    }  

    @GetMapping(path = { "", "/" })
    public ResponseEntity<ArrayList<LoanPeriod>> getAllLoanPeriods() {
        ArrayList<LoanPeriod> allLoanPeriods = loanPeriodService.getAllLoanPeriods();
        return new ResponseEntity<>(allLoanPeriods, HttpStatus.OK);
    }

    @GetMapping(path = { "/{id}", "/{id}/" })
    public ResponseEntity<LoanPeriod> getLoanPeriod(@PathVariable int id) {
        LoanPeriod findLoanPeriod = loanPeriodService.getLoanPeriod(id);
        return new ResponseEntity<>(findLoanPeriod, HttpStatus.OK);
    }  

    @DeleteMapping(path = { "/{id}", "/{id}/" })
    public ResponseEntity<LoanPeriod> deleteLoanPeriod(@PathVariable int id) {
        loanPeriodService.deleteLoanPeriod(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 

    @PostMapping("/borrow/{learner_id}/{book_id}")
    public ResponseEntity<LoanPeriod> createLoanPeriod(@PathVariable int learner_id, @PathVariable int book_id) {
        LoanPeriod createLoanPeriod = loanPeriodService.createLoanPeriod(learner_id, book_id);
        return new ResponseEntity<>(createLoanPeriod, HttpStatus.OK);
    } 

    @PutMapping("/return/{id}")
    public ResponseEntity<LoanPeriod> returnLoanPeriod(@PathVariable int id) {
        LoanPeriod returnedLoanPeriod = loanPeriodService.returnLoanPeriod(id);

        if (returnedLoanPeriod == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(loanPeriodService.getLoanPeriod(id), HttpStatus.ACCEPTED);
    }
}   
