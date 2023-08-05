package com.web.start.poupmoney.api.poupmoney.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.start.poupmoney.api.poupmoney.models.Expense;
import com.web.start.poupmoney.api.poupmoney.models.User;
import com.web.start.poupmoney.api.poupmoney.models.enums.ProfileEnum;
import com.web.start.poupmoney.api.poupmoney.models.projections.ExpenseProjection;
import com.web.start.poupmoney.api.poupmoney.repositories.ExpenseRepo;
import com.web.start.poupmoney.api.poupmoney.security.UserSpringSecurity;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.AuthorizationException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.DataException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.ObjectNotFoundException;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private UserService userService;

    public Expense findById(Long id) {
        Expense expense = this.expenseRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Expense.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasExpense(userSpringSecurity, expense))
            throw new AuthorizationException("Acesso negado!");

        return expense;
    }

    public List<ExpenseProjection> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<ExpenseProjection> expenses = this.expenseRepo.findByUser_Id(userSpringSecurity.getId());
        return expenses;
    }

    @Transactional // Atomicidade dos dados, ou faz tudo ou não faz nada (util para
                   // inserçoes/atualização no data base)
    public Expense create(Expense obj) {

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");


        User user = this.userService.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.expenseRepo.save(obj);
        return obj;
    }

    @Transactional
    public Expense update(Expense existingExpense) {
        Expense NewExistingExpense = findById(existingExpense.getId());
        NewExistingExpense.setDescription(existingExpense.getDescription());
        return this.expenseRepo.save(existingExpense);
    }

    public void delete(Long id) {
        findById(id);
        try { // Se o usuario existir no banco ele vai ser deletado
            this.expenseRepo.deleteById(id);
        } catch (Exception e) { // Caso contratio, sera impossivel deletar um usuario que não existe
            throw new DataException("Não é possivel excluir pois a entidades relacionadas");
        }
    }

    private Boolean userHasExpense(UserSpringSecurity userSpringSecurity, Expense expense) {
        return expense.getUser().getId().equals(userSpringSecurity.getId());
    }

}
