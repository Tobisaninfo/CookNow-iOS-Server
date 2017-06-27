package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.MarketOfferEntry
import spark.{Request, Response, Route, Spark}

/**
  * Handles http get request to get all offers for a special market.
  *
  * Possible request parameters: id<p>
  * Return Value: [{id, title, content}]<p>
  *
  * @param connection database connection
  * @author tobias
  */
class MarketOfferList(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val offers = try {
			val marketId = request.params(":id").toInt
			MarketOfferEntry(marketId, connection)
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage)
				null
		}
		offers
	}
}
