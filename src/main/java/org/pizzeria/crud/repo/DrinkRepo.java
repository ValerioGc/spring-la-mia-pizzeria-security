package org.pizzeria.crud.repo;

import java.util.List;

import org.pizzeria.crud.pojo.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepo extends JpaRepository<Drink, Integer>{
	public List<Drink> findByNameContainingIgnoreCase(String name);
}