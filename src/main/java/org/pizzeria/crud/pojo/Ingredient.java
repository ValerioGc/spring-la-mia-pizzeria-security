package org.pizzeria.crud.pojo;

import java.util.List;

import org.pizzeria.crud.repo.IngredientRepo;
import org.pizzeria.crud.serv.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false )
	@NotNull(message = "Il nome dell'ingrediente non deve essere vuoto")
	@NotEmpty(message = "Il nome dell'ingrediente non deve essere vuoto")
	private String name;	
	
	
	@ManyToMany(mappedBy = "ingredients", cascade = CascadeType.REMOVE)
	private List<Pizza> pizzas;

	
//  Constructor
	public Ingredient() { }
	public Ingredient(String name) {
		setName(name);
	}
	public Ingredient(String name, List<Pizza> pizzas) {
		this(name);
		setPizzas(pizzas);
	}

	
//  Linked Pizzas
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
//  ID	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
//  Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "\nNome: " + getName();
	}
	
	
	// Controllo id e unicità
	@Override
	public int hashCode() {
		return getId();
	}
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Ingredient)) {
			return false;
		}
		return obj.hashCode() == hashCode();	
	}
}
