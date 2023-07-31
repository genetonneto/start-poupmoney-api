package com.web.start.poupmoney.api.poupmoney.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.start.poupmoney.api.poupmoney.models.Expense;
import com.web.start.poupmoney.api.poupmoney.models.User;
import com.web.start.poupmoney.api.poupmoney.repositories.ExpenseRepo;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.DataException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.ObjectNotFoundException;

@Service
public class ExpenseService {

    // ALTERAR NOME PARA EXPENSE
    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private UserService userService;

    // ALTERAR NOME PARA EXPENSE
    public Expense findById(Long id) {
        Optional<Expense> task = this.expenseRepo.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException (
                "Tarefa não encontrada! Id: " + id + ", Tipo: " + Expense.class.getName()));
    }

    // ALTERAR NOME PARA EXPENSE
    public List<Expense> findAllByUserId(Long userId) {
        List<Expense> tasksfinances = this.expenseRepo.findByUser_Id(userId);
        return tasksfinances;
    }

    // ALTERAR NOME PARA EXPENSE
    @Transactional // Atomicidade dos dados, ou faz tudo ou não faz nada (util para inserçoes/atualização no data base)
    public Expense create(Expense obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.expenseRepo.save(obj);
        return obj;
    }

    // ALTERAR NOME PARA EXPENSE
    // @Transactional
    // public Expense update(Expense obj) {
    //     Expense newObj = findById(obj.getId());
    //     newObj.setDescription(obj.getDescription());
    //     return this.expenseRepo.save(newObj);
    // }

    // MANEIRA MAIS ADEQUADA DE NOMEAR UPDATE
    @Transactional
    public Expense update(Expense existingExpense) {
        Expense NewExistingExpense = findById(existingExpense.getId());
        NewExistingExpense .setDescription(existingExpense.getDescription());
        return this.expenseRepo.save(existingExpense);
    }


    public void delete(Long id) {
        findById(id);
        try {  // Se o usuario existir no banco ele vai ser deletado
            this.expenseRepo.deleteById(id);
        } catch (Exception e) { // Caso contratio, sera impossivel deletar um usuario que não existe
            throw new DataException("Não é possivel excluir pois a entidades relacionadas");
        }
    }

}
