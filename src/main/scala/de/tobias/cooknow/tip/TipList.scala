package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.Tip
import spark.{Request, Response, Route, Spark}

/**
  * Handles http get request to get all tips.
  *
  * Return Value: [{id, title, content}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class TipList(connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Tip(connection)
	}
}