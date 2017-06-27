package de.tobias.cooknow.tasks

import java.sql.{Connection, SQLException}
import java.util.TimerTask

import de.tobias.cooknow.model.market.{Market, MarketOfferEntry}
import de.tobias.cooknow.model.market.offer.{MarketOfferParserAldi, MarketOfferParserReal, MarketOfferParserRewe}

/**
  * Background Task for crawling market offers.
  *
  * @param connection database connection
  */
class OfferScheduler(connection: Connection) extends TimerTask {

	/**
	  * List of all markets
	  */
	private val markets = Map("rewe" -> new MarketOfferParserRewe, "real" -> new MarketOfferParserReal, "aldi"
		-> new MarketOfferParserAldi
	)

	override def run(): Unit = {
		MarketOfferEntry.dropTable(connection)
		for ((key, value) <- markets) {
			try {
				val offers = value.fetchOffers()
				val market = Market(connection, key)

				// Insert Offers
				for (offer <- offers) {
					try {
						offer.insert(connection, market)
					} catch {
						case _: SQLException =>
					}
				}
			} catch {
				case e: Exception => e.printStackTrace()
			}
		}
	}
}
