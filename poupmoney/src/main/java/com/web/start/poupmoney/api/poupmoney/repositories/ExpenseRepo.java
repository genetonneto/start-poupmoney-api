package com.web.start.poupmoney.api.poupmoney.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.start.poupmoney.api.poupmoney.models.Expense;
import com.web.start.poupmoney.api.poupmoney.models.projections.ExpenseProjection;


// Alterar nome para EXPENSE, LONG
@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long>{
    
    List<ExpenseProjection> findByUser_Id(Long id);

}