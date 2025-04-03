package com.example.login.repositories;

import com.example.login.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmail(String username);




}
