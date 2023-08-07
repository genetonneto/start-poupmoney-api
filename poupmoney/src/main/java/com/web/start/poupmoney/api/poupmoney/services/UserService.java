package com.web.start.poupmoney.api.poupmoney.services;


import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.start.poupmoney.api.poupmoney.models.User;
import com.web.start.poupmoney.api.poupmoney.models.dto.UserCreateDTO;
import com.web.start.poupmoney.api.poupmoney.models.dto.UserUpdateDTO;
import com.web.start.poupmoney.api.poupmoney.models.enums.ProfileEnum;
import com.web.start.poupmoney.api.poupmoney.repositories.UserRepo;
import com.web.start.poupmoney.api.poupmoney.security.UserSpringSecurity;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.AuthorizationException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.DataException;
import com.web.start.poupmoney.api.poupmoney.services.exceptionCustonService.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    // public User findById(Long id) {

    //     UserSpringSecurity userSpringSecurity = authenticated();
    //     if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
    //     throw new AuthorizationException("Acesso negado!");

    //     Optional<User> user = this.userRepo.findById(id);
    //     return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    // }


    public User findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = this.userRepo.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }


    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj.setBudget(obj.getBudget());
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

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public User fromDTO(@Valid UserCreateDTO obj) {
        User user = new User();
        user.setUsername(obj.getUsername());
        user.setPassword(obj.getPassword());
        user.setBudget(obj.getBudget());

        return user;
    }

    public User fromDTO(@Valid UserUpdateDTO obj) {
        User user = new User();
        user.setId(obj.getId());
        user.setPassword(obj.getPassword());
        return user;
    }



}