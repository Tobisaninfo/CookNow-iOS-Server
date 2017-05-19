package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.Tip
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 18.05.17.
  */
class TipList(connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		try {
			val id = request.params(":category").toInt
			Tip(id, connection)
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage); null
		}
	}
}
