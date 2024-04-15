package com.tvt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvt.entity.Category;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
