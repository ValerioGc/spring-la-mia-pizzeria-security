package org.pizzeria.crud.controller.old;

import java.util.List;
import java.util.Optional;

import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.serv.PizzaService;
import org.pizzeria.crud.serv.PromotionService;
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

@RequestMapping("/promos")
// @Controller
public class PromoController {

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private PizzaService pizzaService;
	
	
//  Index + edit
	@RequestMapping("/index")
	public String indexPromos(Model model) {
		
		List<Promotion> promos = promotionService.findAll();
		model.addAttribute("obj", promos);

		// --------------- Create ----------------------------
		
		List<Pizza> pizzas = pizzaService.findAll();
		model.addAttribute("pizzas", pizzas);

		Promotion prom = new Promotion();
		model.addAttribute("objS", prom);

		// -----------------------------------------------------
		
		model.addAttribute("objN", "promos");
		model.addAttribute("type", "display");
		model.addAttribute("routeName", "promozioni");

		return "CRUDtemplates/ingredients-promo/index";
	}

	
// Store 
	@PostMapping("/admin/store")
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
	@GetMapping("/admin/edit/{id}")
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
	@PostMapping("/admin/update/{id}")
	public String updatePromo(@Valid Promotion promotion, 
								@PathVariable("id") int id,
								BindingResult bindingResult, 
								RedirectAttributes redirectAttributes) {
	
		//---------------------------- Errors & Msg ----------------------------------------------
		
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			return "redirect:/promos/admin/update/" + promotion.getId();
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
	@GetMapping("/admin/delete/{id}")
	public String deletePromotion(@PathVariable("id") int id) {
		
		promotionService.deletePromotionById(id);
		
		return "redirect:/promos/index";
	}
}
