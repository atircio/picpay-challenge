package com.atircio.pickpay.repositories;

import com.atircio.pickpay.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
