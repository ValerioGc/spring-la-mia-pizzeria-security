package org.pizzeria.crud.serv;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.repo.DrinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {
	
	@Autowired
	private DrinkRepo drinkRepo;
	
// Save
	public void save(Drink drink) {
		drinkRepo.save(drink);
	}
	
// Find 
	public List<Drink> findAll() {
		return drinkRepo.findAll();
	}

// Find by ID
	public Optional<Drink> findDrinkById(int id) {
		return drinkRepo.findById(id);
	}
	
// Find by ID
	public void deleteDrinkById(int id) {
		drinkRepo.deleteById(id);
	}
	
// Find by Name	
	public List<Drink> findByName(String name) {
		return drinkRepo.findByNameContainingIgnoreCase(name);
	}
}
