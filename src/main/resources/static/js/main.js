let pizzaDrop = document.getElementById('pizza-dropdown');
let drinkDrop = document.getElementById('drink-dropdown');
	
pizzaDrop.addEventListener("click", displayPizzasActions)
drinkDrop.addEventListener("click", displayDrinksActions)
	
	
let pzDrop = false;
let drDrop = false;
 	

function displayPizzasActions() {
	
	if (drDrop == true) {
		document.getElementById("drink-actions").classList.toggle("d-none");
	}
	if (pzDrop == true) {
		pzDrop = false ;	
	} else {
		pzDrop = true;
	}
	document.getElementById("pizza-actions").classList.toggle("d-none");
	
}

function displayDrinksActions() {
	if (pzDrop == true) {
		document.getElementById("pizza-actions").classList.toggle("d-none");
	}
	if (drDrop == true) {
		drDrop = false ;	
	} else {
		drDrop = true;
	}
	
	document.getElementById("drink-actions").classList.toggle("d-none");
}

// Controllo elementi attivi navbar
if (pizzaElAct.classList="active") {
	document.getElementById("pizza-actions").classList.remove("d-none");
}