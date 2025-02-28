package com.atircio.pickpay.repositories;

import com.atircio.pickpay.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     Optional<User> findByCPF(String cpf);

     Optional<User> findByEmail(String email);

}
