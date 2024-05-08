package com.example.userapp;
import org.springframework.data.repository.CrudRepository;

import com.example.userapp.Users;
public interface UsersRepository  extends CrudRepository<Users, Integer> {

}
