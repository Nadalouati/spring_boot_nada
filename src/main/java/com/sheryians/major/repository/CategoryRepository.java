package com.sheryians.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.model.Category;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
