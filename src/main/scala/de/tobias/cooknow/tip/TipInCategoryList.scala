package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.Tip
import spark.{Request, Response, Route, Spark}

/**
  * Handles http get request to get all tips.
  *
  * Possible request parameters: category id<p>
  * Return Value: [{id, title, content}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class TipInCategoryList(connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		try {
			val id = request.params(":category").toInt
			Tip(id, connection)
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage); null
		}
	}
}
