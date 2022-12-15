package org.pizzeria.crud.repo;

import org.pizzeria.crud.pojo.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Integer> {

}
