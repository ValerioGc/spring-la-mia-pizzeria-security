package org.pizzeria.crud.repo;

import java.util.List;

import org.pizzeria.crud.pojo.Pizza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepo extends JpaRepository<Pizza, Integer>{

	public List<Pizza> findByNameContainingIgnoreCase(String name);
	
}