package com.web.start.poupmoney.api.poupmoney.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.web.start.poupmoney.api.poupmoney.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    @Transactional(readOnly = true)
    User findByUsername(String username);
    

}