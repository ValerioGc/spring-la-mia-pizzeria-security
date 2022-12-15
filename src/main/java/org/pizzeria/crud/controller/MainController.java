package org.pizzeria.crud.controller;

import java.util.List;

import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.pojo.Pizza;

import org.pizzeria.crud.serv.DrinkService;
import org.pizzeria.crud.serv.PizzaService;
import org.pizzeria.crud.serv.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private DrinkService drinkService;

//  Home ------------------------------------------------------------------
	@GetMapping
	public String goHome(Model model) {
		model.addAttribute("routeName", "home");
		return "home" ;
	}
	
//  Search ------------------------------------------------------------------
	@GetMapping("/search")
	public String searchByName(Model model,
			@RequestParam(name = "query", required = false) 
			String query) {
		
		List<Drink> drinks = query == null ? drinkService.findAll() : drinkService.findByName(query);
		model.addAttribute("drinks", drinks);

		List<Pizza> pizzas = query == null ? pizzaService.findAll() : pizzaService.findByName(query);
		model.addAttribute("pizzas", pizzas);
		
		model.addAttribute("query", query);
		model.addAttribute("routeName", "search");
		model.addAttribute("typeRel", "ty2");
		
		return "SRCtemplates/commonSearch";
 	}
}