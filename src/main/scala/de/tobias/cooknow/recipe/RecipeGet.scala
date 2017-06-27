package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import spark.{Request, Response, Route, Spark}

/**
  * Handles http get request to get one recipe.
  *
  * Possible request parameters: id<p>
  * Return Value: {id, name, difficulty, time, steps: [{id, content, order, ingredient[], items}]}<p>
  *
  * @param connection database connection
  * @author tobias
  */

class RecipeGet(val connection: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val recipe = try {
			val id = request.params(":id").toInt
			val recipe = Recipe(id, connection)

			if (recipe == null) {
				Spark.halt(400, "Bad Request: Recipe not exists")
			}

			recipe
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage); null
		}
		recipe
	}
}
