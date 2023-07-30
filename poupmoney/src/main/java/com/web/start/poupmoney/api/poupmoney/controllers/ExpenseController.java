package com.web.start.poupmoney.api.poupmoney.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.web.start.poupmoney.api.poupmoney.models.Expense;
import com.web.start.poupmoney.api.poupmoney.services.ExpenseService;
import com.web.start.poupmoney.api.poupmoney.services.UserService;


@RestController
@RequestMapping("/expense") // ALTERAR NOME PARA EXPENSE
@Validated
public class ExpenseController { // ALTERAR NOME PARA EXPENSE
    
    @Autowired
    private ExpenseService expenseService; // ALTERAR NOME PARA EXPENSE

    @Autowired
    private UserService userService ;


    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable Long id) { // ALTERAR NOME PARA EXPENSE
        Expense obj = this.expenseService.findById(id); // ALTERAR NOME PARA EXPENSE
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> findAllByUserId(@PathVariable Long userId) {
        this.userService.findById(userId);
        List<Expense> objs = this.expenseService.findAllByUserId(userId); // ALTERAR NOME PARA EXPENSE
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Expense obj) {
        this.expenseService.create(obj); // ALTERAR NOME PARA EXPENSE
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Expense obj, @PathVariable Long id) {
        obj.setId(id);
        this.expenseService.update(obj); // ALTERAR NOME PARA EXPENSE
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.expenseService.delete(id); // ALTERAR NOME PARA EXPENSE
        return ResponseEntity.noContent().build();
    }
    
}