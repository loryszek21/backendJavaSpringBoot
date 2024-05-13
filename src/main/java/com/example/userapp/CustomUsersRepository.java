package com.example.userapp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomUsersRepository extends PagingAndSortingRepository<Users, Integer>, CrudRepository<Users, Integer> {
    @Query("SELECT COUNT(*) FROM Users")
    long customCountQuery();
}
