package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.MarketOfferEntry
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 12.05.17.
  */
class MarketOfferList(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val offers = try {
			val marketId = request.params(":id").toInt
			MarketOfferEntry(marketId, conn)
		} catch {
			case e: NumberFormatException => Spark.halt(400, "Bad request: " + e.getLocalizedMessage)
				null
		}
		offers
	}
}
