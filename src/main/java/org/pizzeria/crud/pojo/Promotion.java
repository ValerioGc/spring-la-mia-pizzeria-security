package org.pizzeria.crud.pojo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	@NotEmpty(message = "Il titolo non può essere vuoto")
	@NotNull(message = "Il titolo non può essere null")
	private String name;

	@NotNull(message = "La data non può essere vuota")
	private LocalDate startDate;
	
	@NotNull (message = "La data non può essere vuota")
	private LocalDate endDate;
	
	
	@OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Pizza> pizzas;

	@OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Drink> drinks;
	
	
//  Constructor
	public Promotion() { }
	public Promotion(String name, LocalDate startDate, LocalDate endDate ) {
		setName(name);
		setStartDate(startDate);
		setEndDate(endDate);
	}
	
	
// ID
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

//  Start Date
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {

		this.startDate = startDate;
	}

//  End date
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
//  Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//  Linked Pizzas
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
//  Linked Drinks
	public List<Drink> getDrinks() {
		return drinks;
	}
	public void setDrinks(List<Drink> drinks) {
		this.drinks = drinks;
	}
		
// Controllo id e unicità
	@Override
	public int hashCode() {
		return getId();
	}
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Promotion)) {
			return false;
		}
		return obj.hashCode() == hashCode();	
	}
		
	@Override
	public String toString() {
		return  "Titolo: " + getName() 
				+ "\nInzio: " + getStartDate()
				+ "\nFine: " + getEndDate();
	}
}
