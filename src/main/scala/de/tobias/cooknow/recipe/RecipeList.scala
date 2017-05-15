package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import spark.{Request, Response, Route}

/**
  * Created by tobias on 10.05.17.
  */
class RecipeList(val conn: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		Recipe(conn)
	}
}
