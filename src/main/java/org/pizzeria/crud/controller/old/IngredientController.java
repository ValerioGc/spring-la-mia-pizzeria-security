package org.pizzeria.crud.controller.old;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.serv.IngredientService;
import org.pizzeria.crud.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

//@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private PizzaService pizzaService;


// Index + edit
	@GetMapping("/index")
	public String index(Model model) {
		
		List<Ingredient> ingredients = ingredientService.findAllPizzas();
		model.addAttribute("obj", ingredients);
		
		// ------------- Create-------------------------
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);
		
		Ingredient ingr = new Ingredient();
		model.addAttribute("objS", ingr);
		
		// ----------------------------------------------
		
		model.addAttribute("routeName", "Ingrediente");
		model.addAttribute("type", "display");
		model.addAttribute("objN", "ingredients");
		
		return "CRUDtemplates/ingredients-promo/index";
	}
	
	
// Store
	@PostMapping("/admin/store")
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
	@GetMapping("/admin/edit/{id}")
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
	@PostMapping("/admin/update/{id}")
	public String updateIngredient(@Valid Ingredient ingredient, 
									@PathVariable("id") int id,
									BindingResult bindingResult, 
									RedirectAttributes redirectAttributes) {
		 
		//------------------------ Errors & meassages --------------------------------------
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/ingredients/edit/" + ingredient.getId();
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
	@GetMapping("/admin/delete/{id}")
	public String deleteingredient(@PathVariable("id") int id) {
		
		ingredientService.deleteIngredientById(id);
		
		return "redirect:/ingredients";
	}
}
