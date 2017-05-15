package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 10.05.17.
  */
class RecipeGet(val conn: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val recipe = try {
			val id = request.params(":id").toInt
			val recipe = Recipe(id, conn)

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
