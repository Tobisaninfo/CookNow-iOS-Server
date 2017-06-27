package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import spark.{Request, Response, Route}

/**
  * Handles http get request to get all recipes.
  *
  * Return Value: [{id, name, difficulty, time, steps: [{id, content, order, ingredient[], items}]}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class RecipeList(val connection: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		Recipe(connection)
	}
}
