package de.tobias.cooknow.tip

import java.sql.Connection

import de.tobias.cooknow.model.TipCategory
import spark.{Request, Response, Route}

/**
  * Created by tobias on 19.05.17.
  */
class TipCategoryList(databaseConnection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		TipCategory(databaseConnection)
	}
}
