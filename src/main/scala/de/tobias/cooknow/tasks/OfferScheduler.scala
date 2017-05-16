package de.tobias.cooknow.tasks

import java.sql.Connection
import java.util.TimerTask

import de.tobias.cooknow.model.market.offer.MarketOfferParserReal

/**
  * Created by tobias on 16.05.17.
  */
class OfferScheduler(connection: Connection) extends TimerTask {

	private val markets = Map("real" -> new MarketOfferParserReal)

	override def run(): Unit = {
		for ((key, value) <- markets) {
			val offers = value.fetch()
			println(s"Fetch ${offers.size} offers from ${key}")
		}
	}
}
