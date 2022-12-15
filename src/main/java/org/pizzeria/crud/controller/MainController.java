package org.pizzeria.crud.controller;

import java.util.LinkedList;
import java.util.List;

import org.pizzeria.crud.intf.PriceableInt;
import org.pizzeria.crud.pojo.Drink;
import org.pizzeria.crud.pojo.Ingredient;
import org.pizzeria.crud.pojo.Pizza;
import org.pizzeria.crud.pojo.Promotion;
import org.pizzeria.crud.serv.DrinkService;
import org.pizzeria.crud.serv.IngredientService;
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

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PromotionService promotionService;
	
	
//  Home ------------------------------------------------------------------
	@GetMapping
	public String goHome(Model model) {
		model.addAttribute("routeName", "home");
		return "home" ;
	}
	
//  All elements priceables
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
	
	
	// Search Pizza ----------------------------------------------------------------
		@GetMapping("/pizza/search")
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
		
	//  Search ----------------------------------------------------------------
		@GetMapping("/drink/search")
		public String getSearchDrinkByName(Model model, 
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
		
//  Index pizza--------------------------------------------------------------
	@GetMapping("/pizza/index")
	public String indexPizza(Model model) {
		
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
	
	
	// Index ----------------------------------------------------------------
		@GetMapping("/drink/index")
		public String indexdRINK(Model model) {
			
			List<Drink> drinks = drinkService.findAll();
			model.addAttribute("obj", drinks);
			
			model.addAttribute("routeName", "drink");		
			model.addAttribute("type", "display");
			model.addAttribute("objN", "drink");
			model.addAttribute("typeRel", "ty2");
			
			return "CRUDtemplates/pizzas-drinks/index";
		}
		
		
		
//  Index + edit Promotions
	@RequestMapping("/promos/index")
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
	
	// Index + edit
		@GetMapping("/ingredients/index")
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
		
//  Test Auth ------------------------------------------------------------------
	
	@GetMapping("/home")
	public String getHome() {
		return "testAdmin/home";
	}
	
	@GetMapping("/user")
	public String getUser() {
		return "testAdmin/user";
	}
	
	@GetMapping("/admin")
	public String getAdmin() {
		return "testAdmin/admin";
	}
	
	@GetMapping("/useradmin")
	public String getUserAdmin() {
		return "testAdmin/userAdmin";
	}
	
	
	
}