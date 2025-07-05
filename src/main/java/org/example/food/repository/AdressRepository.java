package org.example.food.repository;

import org.example.food.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {
}
