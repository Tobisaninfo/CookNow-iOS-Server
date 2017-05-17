package de.tobias.cooknow.model.market.offer

import java.text.SimpleDateFormat
import java.util.Locale

import com.mashape.unirest.http.Unirest
import de.tobias.cooknow.model.market.MarketOfferEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elementList
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.element
/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferParserAldi extends MarketOfferParser {
	private val dateFormatter = new SimpleDateFormat("dd.MM.yyyy")

	override def fetch(): List[MarketOfferEntry] = {
		var offerList = List[MarketOfferEntry]()

		val result = Unirest.get("https://www.aldi-nord.de/angebote.html").asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		var dateString = (document >> element(".text-gray")).text
		dateString = dateString.substring(dateString.lastIndexOf(" ") + 1)
		val date = dateFormatter.parse(dateString)

		val days = document >> elementlist(".mod-offer-stage__section")
		val list = days.head >> elementList(".mod-article-tile__content")

		list.foreach(i => {
			val name = (i >> element(".mod-article-tile__title")).text
			val price = (i >> element(".price__main ")).text

			val offer = new MarketOfferEntry(name, parse(price, Locale.GERMAN), date)
			offerList ::= offer
		})
		offerList
	}
}
