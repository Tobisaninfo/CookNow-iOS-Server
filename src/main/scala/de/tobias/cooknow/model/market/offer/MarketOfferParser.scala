package de.tobias.cooknow.model.market.offer

import java.text.ParseException

import de.tobias.cooknow.model.market.MarketOfferEntry

/**
  * Created by tobias on 11.05.17.
  */
trait MarketOfferParser {

	def fetchOffers(): List[MarketOfferEntry]

	import java.text.{DecimalFormat, NumberFormat}
	import java.util.Locale

	@throws[ParseException]
	def parse(amount: String, locale: Locale): Float = {
		val format = NumberFormat.getNumberInstance(locale)
		format match {
			case f: DecimalFormat => f.setParseBigDecimal(true)
			case _ =>
		}
		format.parse(amount.replaceAll("[^\\d.,]", "")).floatValue()
	}
}
