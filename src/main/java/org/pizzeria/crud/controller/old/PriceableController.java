package org.pizzeria.crud.controller.old;

import java.util.LinkedList;
import java.util.List;

import org.pizzeria.crud.intf.PriceableInt;
import org.pizzeria.crud.serv.DrinkService;
import org.pizzeria.crud.serv.PizzaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller	
@RequestMapping
public class PriceableController {
	
	@Autowired
	private DrinkService drinkService;
	
	@Autowired
	private PizzaService pizzaService;


	@GetMapping("/priceable")
	public String getPriceable(Model model) {
	
		List<PriceableInt> elementsPriceables = new LinkedList<>();
		
		elementsPriceables.addAll(drinkService.findAll());
		elementsPriceables.addAll(pizzaService.findAll());
	
		elementsPriceables.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
	
		model.addAttribute("obj", elementsPriceables);
		model.addAttribute("routeName", "priceable");
		model.addAttribute("typeRel", "ty2");
		
		return "SRCtemplates/priceable";
	}
}
