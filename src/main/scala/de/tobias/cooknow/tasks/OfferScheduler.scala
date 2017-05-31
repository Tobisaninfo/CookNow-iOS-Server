package de.tobias.cooknow.tasks

import java.sql.{Connection, SQLException}
import java.util.TimerTask

import de.tobias.cooknow.model.market.{Market, MarketOfferEntry}
import de.tobias.cooknow.model.market.offer.{MarketOfferParserAldi, MarketOfferParserReal, MarketOfferParserRewe}

/**
  * Created by tobias on 16.05.17.
  */
class OfferScheduler(connection: Connection) extends TimerTask {

	private val markets = Map("rewe" -> new MarketOfferParserRewe, "real" -> new MarketOfferParserReal, "aldi"
		-> new MarketOfferParserAldi
	)

	override def run(): Unit = {
		MarketOfferEntry.dropTable(connection)
		for ((key, value) <- markets) {
			try {
				val offers = value.fetch()
				val market = Market(connection, key)

				for (offer <- offers) {
					try {
						offer.insert(connection, market)
					} catch {
						case _: SQLException =>
					}
				}
			} catch {
				case _: Exception =>
			}
		}
	}
}
