package org.pizzeria.crud.serv;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class PromotionService {
	
	@Autowired
	private PromotionRepo promotionRepo;
	
// Save
	public void save(Promotion promotion) {
		promotionRepo.save(promotion);
	}
	
// Find by ID
	public Promotion findPromotionById(int id) {
		return promotionRepo.findById(id).get();
	}
	
// Delete
	public void deletePromotionById(int id) {
		promotionRepo.deleteById(id);
	}
	
// Find
	public List<Promotion> findAll() {
		return promotionRepo.findAll();
	}

// Find pizzas
	@Transactional
	public List<Promotion> findPizzas() {
		
		List<Promotion> promotions  = promotionRepo.findAll();
		
		for (Promotion promotion : promotions ) {
			Hibernate.initialize(promotion.getPizzas());
		}
		
		return promotions;
	}
	
// Find drinks
	@Transactional
	public List<Promotion> findDrinks() {
		
		List<Promotion> promotions  = promotionRepo.findAll();
		
		for (Promotion promotion : promotions ) {
			Hibernate.initialize(promotion.getDrinks());
		}
		
		return promotions;
	}
}
