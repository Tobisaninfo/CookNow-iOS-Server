package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.TipCategory
import spark.{Request, Response, Route}

/**
  * Handles http get request to get all tip categories.
  *
  * Return Value: [{id, name}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class TipCategoryList(connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		TipCategory(connection)
	}
}
