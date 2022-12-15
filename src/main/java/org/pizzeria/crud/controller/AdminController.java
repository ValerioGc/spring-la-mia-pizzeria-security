package org.pizzeria.crud.controller;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.serv.PizzaService;
import org.pizzeria.crud.serv.PromotionService;
import org.pizzeria.crud.serv.DrinkService;
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


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private PizzaService pizzaService;

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private DrinkService drinkService;


	// -----------------------------------------------------------------------------------
	// --------------------------------- Pizze -------------------------------------------
	
	
//  Create pizza ----------------------------------------------------------------
	@GetMapping("/pizza/create")
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
	
// Store pizza ----------------------------------------------------------------
	@PostMapping("/pizza/store")
	public String storePizza(@Valid Pizza pizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

	// --------------------------------- Errors & Msg --------------------------------------	
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/admin/pizza/create";
		}
		redirectAttributes.addFlashAttribute("successMsg", "Creazione avvenuta con successo");
	
	// -------------------------------------------------------------------------------------	

		pizzaService.save(pizza);
		
		return "redirect:/pizza/index";
	}
	
	
// Edit ----------------------------------------------------------------
	@GetMapping("/pizza/edit/{id}")
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
	@PostMapping("/pizza/update")
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
	
	
// Delete pizza ----------------------------------------------------------------
	@GetMapping("/pizza/delete/{id}")
	public String deletePizza(@PathVariable("id") int id) {
		
		pizzaService.deletePizzaById(id);
		
		return "redirect:/pizza/index";
	}

	
	// ---------------------------------------------------------------------------------
	// --------------------------------- Drink -------------------------------------------
	
	
	// Create ----------------------------------------------------------------
		@GetMapping("/drink/create")
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
		@PostMapping("/drink/store")
		public String storeDrink(@Valid Drink drink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
			
		// --------------------------------- Errors & Msg --------------------------------------	
			
			if(bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
				return "redirect:/admin/drink/create";
			}
			
			redirectAttributes.addFlashAttribute("successMsg", "Creazione avvenuta con successo");
		 
		//	-------------------------------------------------------------------------------------	
			
			drinkService.save(drink);
			
			return "redirect:/drink/index";
		}
		
		
	//  Edit ----------------------------------------------------------------
		@GetMapping("/drink/edit/{id}")
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
		@PostMapping("/drink/update")
		public String updateDrink(@Valid Drink drink, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
			
		// --------------------------------- Errors & Msg --------------------------------------	
			
			if(bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
				return "redirect:/admin/drink/edit/" + drink.getId();
			}
			
			redirectAttributes.addFlashAttribute("successMsg", "Modifica avvenuta con successo");

		// -------------------------------------------------------------------------------------	

			drinkService.save(drink);
			
			return "redirect:/drink/index";
		}
		
		
	//  Delete drink ----------------------------------------------------------------
		@GetMapping("/drink/delete/{id}")
		public String deleteDrink(@PathVariable("id") int id) {
			
			drinkService.deleteDrinkById(id);
			
			return "redirect:/drink/index";
		}
		
		
	// ---------------------------------------------------------------------------------
	// ----------------------------- Ingredients ---------------------------------------
		
		
		// Store
			@PostMapping("/ingredients/store")
			public String storeIngredient(@Valid Ingredient ingredient, 
											BindingResult bindingResult, 
											RedirectAttributes redirectAttributes) {
				
				//---------------------------- Errors & Msg ----------------------------------------------
				
				if(bindingResult.hasErrors()) {
					redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
					return "redirect:/ingredients/index";
				}
				
				redirectAttributes.addFlashAttribute("successMsg", "Ingrediente creato con successo");
				
				// ---------------------------------------------------------------------------------------
				
				for (Pizza pizza :  ingredient.getPizzas()) {
					pizza.getIngredients().add(ingredient);
				}	
			
				ingredientService.save(ingredient);
			
				return "redirect:/ingredients/index";
			}
			

		// Edit
			@GetMapping("/ingredients/edit/{id}")
			public String editIngredient(@PathVariable("id") int id, Model model) {
				
				Ingredient ingr = ingredientService.findIngredientById(id);
				model.addAttribute("obj", ingr);
						
				List<Pizza> pizzas = pizzaService.findAll();
				model.addAttribute("pizzas", pizzas);
				
				model.addAttribute("routeName", "editIngredient");
				model.addAttribute("element", "ingrediente");
				model.addAttribute("action", "ingredients");
				
				return "CRUDtemplates/ingredients-promo/edit";
			}
			
		// Update
			@PostMapping("/ingredients/update/{id}")
			public String updateIngredient(@Valid Ingredient ingredient, 
											@PathVariable("id") int id,
											BindingResult bindingResult, 
											RedirectAttributes redirectAttributes) {
				 
				//------------------------ Errors & meassages --------------------------------------
				
				if(bindingResult.hasErrors()) {
					redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
					return "redirect:/admin/ingredients/edit/" + ingredient.getId();
				}
				
				redirectAttributes.addFlashAttribute("successMsg", "Modifica effettuata con successo");
				
				//----------------------------------------------------------------------------------

				Ingredient ingrOl = ingredientService.findIngredientById(id);
				
				for (Pizza pizza : ingrOl.getPizzas()) {
					pizza.getIngredients().remove(ingrOl);
				}
				
				for (Pizza pizza : ingredient.getPizzas()) {
					pizza.addIngredient(ingredient);
				}
				
				ingredientService.save(ingredient);
				
				return "redirect:/ingredients/index";
			}
			
			
		// Delete
			@GetMapping("/ingredients/delete/{id}")
			public String deleteingredient(@PathVariable("id") int id) {
				
				ingredientService.deleteIngredientById(id);
				
				return "redirect:/ingredients/index";
			}
		
			
		// ------------------------------- Promotions ------------------------------
			// Store 
			@PostMapping("/promos/store")
			public String storePromotion(@Valid Promotion promotion, 
										BindingResult bindingResult, 
										RedirectAttributes redirectAttributes) {
				
				//---------------------------- Errors & Msg ----------------------------------------------
				
				if(bindingResult.hasErrors()) {
					redirectAttributes.addFlashAttribute("errors2", bindingResult.getAllErrors());
					return "redirect:/promos/index";
				}
					
				redirectAttributes.addFlashAttribute("successMsg", "Promozione creata con successo");
					
				// ---------------------------------------------------------------------------------------
				
				try {
					
					List<Pizza> pizze = promotion.getPizzas();
					
					for (Pizza pizza : pizze) {
						
						pizza.setPromotion(promotion);
					}
					
					promotionService.save(promotion);
					
				} catch(Exception error) {
					
					redirectAttributes.addFlashAttribute("exception", error.getMessage());
				}
				
				return "redirect:/promos/index";
			}


		// Edit
			@GetMapping("/promos/edit/{id}")
			public String editPromo(@PathVariable("id") int id, Model model) {
				
				Promotion optPromo = promotionService.findPromotionById(id);
				model.addAttribute("obj", optPromo);
				
				List<Pizza> pizzas = pizzaService.findAll();
				model.addAttribute("pizzas", pizzas);
				
				model.addAttribute("routeName", "editPromo");
				model.addAttribute("element", "promozione");
				model.addAttribute("action", "promos");
				
				return "CRUDtemplates/ingredients-promo/edit";
			}

		//  Update
			@PostMapping("/promos/update/{id}")
			public String updatePromo(@Valid Promotion promotion, 
										@PathVariable("id") int id,
										BindingResult bindingResult, 
										RedirectAttributes redirectAttributes) {
			
				//---------------------------- Errors & Msg ----------------------------------------------
				
				if(bindingResult.hasErrors()) {
					redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
					return "redirect:/admin/promos/update/" + promotion.getId();
				}
				
				redirectAttributes.addFlashAttribute("successMsg", "Modifica effettuata con successo");
				
				// ---------------------------------------------------------------------------------------
				
				
				for (Pizza pizza : promotion.getPizzas()) {
					pizza.setPromotion(promotion);
				}
				
				promotionService.save(promotion);
				
				return "redirect:/promos/index";
			}
				
			
		// Delete
			@GetMapping("/promos/delete/{id}")
			public String deletePromotion(@PathVariable("id") int id) {
				
				promotionService.deletePromotionById(id);
				
				return "redirect:/promos/index";
			}
}