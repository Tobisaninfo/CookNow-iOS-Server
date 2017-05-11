package de.tobias.cooknow.model.market.offer

import de.tobias.cooknow.model.market.MarketOfferEntry

/**
  * Created by tobias on 11.05.17.
  */
trait MarketOfferParser {

	def fetch(): List[MarketOfferEntry]
}
