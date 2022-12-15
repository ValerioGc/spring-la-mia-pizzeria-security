package org.pizzeria.crud.pojo;

import org.pizzeria.crud.intf.PriceableInt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Drink implements PriceableInt{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false )
	@NotNull(message = "Il nome della pizza non deve essere vuoto")
	@NotEmpty(message = "Il nome della pizza non deve essere vuoto")
	private String name;
	
	@Lob
	private String description;
	
	@Min(value = 1, message = "Il prezzo deve essere superiore a 0€")
	@Max(value = 100, message = "Il prezzo non deve essere superiore a 100€")
	@NotNull(message = "Il prezzo deve essere vuoto")
	private int price;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Promotion promotion;
	
	
// Costruttori
	public Drink() { }
	//Costruttore Senza Relazioni
	public Drink(String name, String description, int price ) {
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	// Costruttore con promozioni
	public Drink(String name, String description, int price, 
					Promotion promotion ) {
		
		setName(name);
		setDescription(description);
		setPrice(price);
		setPromotion(promotion);
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

//  Description
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

//  Price
	@Override
	public int getPrice() {
		return price;
	}
	@Override
	public void setPrice(int price) {
		this.price = price;
	}
	
//  Promo 
	public Promotion getPromotion() {
		return promotion;
	}
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
		
	
	@Override
	public String toString() {
		return "\nNome: " + getName()
			+ "\nDescription: " + getDescription()
			+ "\nPrice: " + getPrice();
	}
}
