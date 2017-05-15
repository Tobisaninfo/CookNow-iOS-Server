package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.Ingredient
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 11.05.17.
  */
class IngredientGet(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val ingredient = try {
			val id = request.params(":id").toInt
			val ingredient = Ingredient(id, conn)

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
