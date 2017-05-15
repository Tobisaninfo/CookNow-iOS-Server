package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.Market
import spark.{Request, Response, Route}

/**
  * Created by tobias on 12.05.17.
  */
class MarketList(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Market(conn)
	}
}
