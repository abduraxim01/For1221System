package com.practical.InternTask.repository;

import com.practical.InternTask.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID> {

    boolean existsByName(String name);
}
