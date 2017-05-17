package de.tobias.cooknow.model.market.offer

import java.text.SimpleDateFormat
import java.util.Locale

import com.mashape.unirest.http.Unirest
import de.tobias.cooknow.model.market.MarketOfferEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element, elementList}

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferParserReal extends MarketOfferParser {
	private val dateFormatter = new SimpleDateFormat("dd.MM.yyyy")

	override def fetch(): List[MarketOfferEntry] = {
		var offerList = List[MarketOfferEntry]()

		val result = Unirest.get("https://www.real.de/markt/wochenangebote-nach-kategorien/lebensmittel/").asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		var dateString = (document >> element(".text-gray")).text
		dateString = dateString.substring(dateString.lastIndexOf(" ") + 1)
		val date = dateFormatter.parse(dateString)

		val list = document >> elementList(".product")

		list.foreach(i => {
			val name = (i >> element("._title")).text
			val price = (i >> element(".price ")).attr("title")

			val offer = new MarketOfferEntry(name, parse(price, Locale.GERMAN), date)
			offerList ::= offer
		})
		offerList
	}
}
