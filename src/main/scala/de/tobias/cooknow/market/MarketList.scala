package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.Market
import spark.{Request, Response, Route}

/**
  * Handles http get request to get all markets.
  *
  * Return Value: [{id, name}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class MarketList(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		Market(connection)
	}
}
