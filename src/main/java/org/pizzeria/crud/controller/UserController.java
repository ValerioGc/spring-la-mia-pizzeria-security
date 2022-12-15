package org.pizzeria.crud.controller;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.serv.DrinkService;
import org.pizzeria.crud.serv.IngredientService;
import org.pizzeria.crud.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DrinkService drinkService;

	@Autowired
	private IngredientService ingredientService;

	
//  ShowPizza ------------------------------------------------------------------
	@GetMapping("/pizza/{id}")
	public String getPizza(@PathVariable("id") int id, Model model) {
	
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);
		
		Optional<Pizza> optPizza = pizzaService.findPizzaById(id);
		Pizza pizza = optPizza.get();
		model.addAttribute("obj", pizza);
		
		model.addAttribute("routeName", "show");
		model.addAttribute("element", "pizza");
		
		return "CRUDtemplates/pizzas-drinks/show";
	}
	
	
	// Show Drink ----------------------------------------------------------------
		@GetMapping("/drink/{id}")
		public String getDrink(@PathVariable("id") int id, Model model) {
			
			Optional<Drink> optDrink = drinkService.findDrinkById(id);
			Drink drink = optDrink.get();
			model.addAttribute("obj", drink);
			
			model.addAttribute("element", "drink");
			model.addAttribute("routeName", "showDrink");
			
			return "CRUDtemplates/pizzas-drinks/show";
		}
		
}
