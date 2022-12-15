package org.pizzeria.crud.controller.old;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.serv.PizzaService;
import org.pizzeria.crud.serv.PromotionService;
import org.pizzeria.crud.serv.IngredientService;

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


//@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private IngredientService ingredientService;



	
//  Index -----------------------------------------------------------------
	@GetMapping("/index")
	public String index(Model model) {
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("obj", pizzas);

		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);
		
		model.addAttribute("routeName", "pizza");
		model.addAttribute("type", "display");
		model.addAttribute("objN", "pizza");
		model.addAttribute("typeRel", "ty1");
		
		return "CRUDtemplates/pizzas-drinks/index";
	}
	
	
//  Show ------------------------------------------------------------------
	@GetMapping("/user/{id}")
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
	
	
//  Create ----------------------------------------------------------------
	@GetMapping("/admin/create")
	public String getCreatePizza(Model model) {
		
		List<Promotion> promotions = promotionService.findAll(); 
		model.addAttribute("promo", promotions);
		
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);

		Pizza pizza = new Pizza();
		model.addAttribute("obj", pizza);
		
		model.addAttribute("routeName", "newPizza");
		model.addAttribute("element", "pizza");
		model.addAttribute("objN", "pizza");
		
		return "CRUDtemplates/pizzas-drinks/new";
	}
	
// Store  ----------------------------------------------------------------
	@PostMapping("/admin/store")
	public String storePizza(@Valid Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

	// --------------------------------- Errors & Msg --------------------------------------	
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/pizza/admin/create";
		}
		redirectAttributes.addFlashAttribute("successMsg", "Creazione avvenuta con successo");
	
	// -------------------------------------------------------------------------------------	

		pizzaService.save(pizza);
		
		return "redirect:/pizza/index";
	}
	
	
// Edit ----------------------------------------------------------------
	@GetMapping("/admin/edit/{id}")
	public String editPizza(@PathVariable("id") int id, Model model) {
		
		List<Promotion> promotions = promotionService.findAll(); 
		model.addAttribute("promo", promotions);
		
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);
		
		Optional<Pizza> optPizza = pizzaService.findPizzaById(id);
		Pizza pizza = optPizza.get();
		model.addAttribute("obj", pizza);
		
		model.addAttribute("routeName", "edit");
		model.addAttribute("element", "pizza");
		
		return "CRUDtemplates/pizzas-drinks/edit";
	}
	
//  Update ----------------------------------------------------------------
	@PostMapping("/admin/update")
	public String updatePizza(@Valid Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {		

	// --------------------------------- Errors & Msg --------------------------------------	

		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/pizza/admin/edit/" + pizza.getId();
		}
		
		redirectAttributes.addFlashAttribute("successMsg", "Modifica avvenuta con successo");
		
	// -------------------------------------------------------------------------------------	

		pizzaService.save(pizza);
		
		return "redirect:/pizza/index";
	}
	
	
// Delete ----------------------------------------------------------------
	@GetMapping("/admin/delete/{id}")
	public String deletePizza(@PathVariable("id") int id) {
		
		pizzaService.deletePizzaById(id);
		
		return "redirect:/pizza/index";
	}
	
	
// Search ----------------------------------------------------------------
	@GetMapping("/search")
	public String getSearchPizzaByName(Model model, 
										@RequestParam(name = "query", required = false) 
										String query) {
		
		List<Pizza> pizzas = query == null ? pizzaService.findAll() : pizzaService.findByName(query);
		model.addAttribute("obj", pizzas);
		
		model.addAttribute("query", query);
		model.addAttribute("routeName", "searchPizza");
		model.addAttribute("element", "pizza");
		model.addAttribute("typeRel", "ty1");
		
		return "SRCtemplates/search";
	}
}