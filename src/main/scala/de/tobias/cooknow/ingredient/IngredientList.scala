package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.Ingredient
import spark.{Request, Response, Route}

/**
  * Created by tobias on 11.05.17.
  */
class IngredientList(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Ingredient(conn)
	}
}
