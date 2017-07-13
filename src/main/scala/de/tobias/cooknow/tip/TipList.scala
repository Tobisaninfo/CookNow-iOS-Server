package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.Tip
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 13.07.17.
  */
class TipList(connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Tip(connection)
	}
}