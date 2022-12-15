package org.pizzeria.crud.controller;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.serv.DrinkService;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.serv.PromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/drink")
public class DrinkController {

	@Autowired
	private DrinkService drinkService;
	
	@Autowired
	private PromotionService promotionService;
	
// Index ----------------------------------------------------------------
	@GetMapping("/index")
	public String index(Model model) {
		
		List<Drink> drinks = drinkService.findAll();
		model.addAttribute("obj", drinks);
		
		model.addAttribute("routeName", "drink");		
		model.addAttribute("type", "display");
		model.addAttribute("objN", "drink");
		model.addAttribute("typeRel", "ty2");
		
		return "CRUDtemplates/pizzas-drinks/index";
	}
	
	
// Show ----------------------------------------------------------------
	@GetMapping("/{id}")
	public String getDrink(@PathVariable("id") int id, Model model) {
		
		Optional<Drink> optDrink = drinkService.findDrinkById(id);
		Drink drink = optDrink.get();
		model.addAttribute("obj", drink);
		
		model.addAttribute("element", "drink");
		model.addAttribute("routeName", "showDrink");
		
		return "CRUDtemplates/pizzas-drinks/show";
	}
		
		
// Create ----------------------------------------------------------------
	@GetMapping("/create")
	public String createDrink(Model model) {
		
		List<Promotion> promotions = promotionService.findAll(); 
		model.addAttribute("promo", promotions);
		
		Drink drink = new Drink();
		model.addAttribute("obj", drink);
		
		model.addAttribute("routeName", "newDrink");
		model.addAttribute("element", "drink");
		model.addAttribute("objN", "drink");
		
		return "CRUDtemplates/pizzas-drinks/new";
	}
//  Store ----------------------------------------------------------------
	@PostMapping("/store")
	public String storeDrink(@Valid Drink drink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
	// --------------------------------- Errors & Msg --------------------------------------	
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/drink/create";
		}
		
		redirectAttributes.addFlashAttribute("successMsg", "Creazione avvenuta con successo");
	 
	//	-------------------------------------------------------------------------------------	
		
		drinkService.save(drink);
		
		return "redirect:/drink/index";
	}
	
	
//  Edit ----------------------------------------------------------------
	@GetMapping("/edit/{id}")
	public String editDrink(@PathVariable("id") int id, Model model) {
		
		List<Promotion> promotions = promotionService.findAll(); 
		model.addAttribute("promo", promotions);

		Optional<Drink> optDrink = drinkService.findDrinkById(id);
		Drink drink = optDrink.get();
		model.addAttribute("obj", drink);
		
		model.addAttribute("routeName", "drinkEdit");
		model.addAttribute("element", "drink");
		
		return "CRUDtemplates/pizzas-drinks/edit";
	}

//  Update  ----------------------------------------------------------------
	@PostMapping("/update")
	public String updateDrink(@Valid Drink drink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
	// --------------------------------- Errors & Msg --------------------------------------	
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/drink/edit/" + drink.getId();
		}
		
		redirectAttributes.addFlashAttribute("successMsg", "Modifica avvenuta con successo");

	// -------------------------------------------------------------------------------------	

		drinkService.save(drink);
		
		return "redirect:/drink/index";
	}
	
	
//  Delete ----------------------------------------------------------------
	@GetMapping("/delete/{id}")
	public String deleteDrink(@PathVariable("id") int id) {
		
		drinkService.deleteDrinkById(id);
		
		return "redirect:/drink/index";
	}
	
	
//  Search ----------------------------------------------------------------
	@GetMapping("/search")
	public String getSearchPizzaByName(Model model, 
										@RequestParam(name = "query", required = false) 
										String query) {
		
		List<Drink> drinks = query == null ? drinkService.findAll() : drinkService.findByName(query);
		model.addAttribute("obj", drinks);
		
		model.addAttribute("query", query);
		model.addAttribute("routeName", "searchDrink");
		model.addAttribute("element", "drink");
		model.addAttribute("typeRel", "ty2");

		return "SRCtemplates/search";
	}
}

