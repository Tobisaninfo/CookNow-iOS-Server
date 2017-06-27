package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.Ingredient
import spark.{Request, Response, Route}

/**
  * Handles http get request to get all ingredients.
  *
  * Return Value: [{id, name, unit, properties: [{id, name}]}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class IngredientList(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Ingredient(connection)
	}
}
