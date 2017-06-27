package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.Ingredient
import spark.{Request, Response, Route, Spark}

/**
  * Handles http get request to get one ingredient.
  *
  * Possible request parameters: id<p>
  * Return Value: {id, name, unit, properties: [{id, name}]}<p>
  *
  * @param connection database connection
  * @author tobias
  */
class IngredientGet(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val ingredient = try {
			val id = request.params(":id").toInt
			val ingredient = Ingredient(id, connection)

			if (ingredient == null) {
				Spark.halt(400, "Bad Request: Ingredient not exists")
			}
			ingredient
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage)
				null
		}
		ingredient
	}
}
