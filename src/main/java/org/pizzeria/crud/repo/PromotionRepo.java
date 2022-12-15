package org.pizzeria.crud.repo;

import org.pizzeria.crud.pojo.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo extends JpaRepository<Promotion, Integer> {

	
}