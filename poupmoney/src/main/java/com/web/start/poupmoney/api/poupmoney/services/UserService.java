package com.web.start.poupmoney.api.poupmoney.services;


import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.start.poupmoney.api.poupmoney.models.User;
import com.web.start.poupmoney.api.poupmoney.models.enums.ProfileEnum;
import com.web.start.poupmoney.api.poupmoney.repositories.UserRepo;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.DataException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    public User findById(Long id) {
        Optional<User> user = this.userRepo.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepo.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepo.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try { // Se o usuario existir no banco ele vai ser deletado
            this.userRepo.deleteById(id);
        } catch (Exception e) { // Caso contratio, sera impossivel deletar um usuario que não existe
            throw new DataException("Não é possível excluir pois há entidades relacionadas.");
        }
    }

}