package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.Property
import spark.{Request, Response, Route}

/**
  * Handles http get request to get all properties.
  *
  * Return Value: [{id, name}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class IngredientPropertiesList(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Property(connection)
	}
}