package de.tobias.cooknow.model.market.offer

import com.mashape.unirest.http.Unirest
import de.tobias.cooknow.model.market.MarketOfferEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.element

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferParserKaufland extends MarketOfferParser {
	private val urls = Array(
		"https://www.kaufland.de/angebote/aktuelle-woche.category=01_Fleisch__Gefl%C3%BCgel__Wurst.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=01a_Frischer_Fisch.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=02_Obst__Gemüse__Pflanzen.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=03_Molkereiprodukte__Fette.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=04_Tiefkühlkost.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=05_Feinkost__Konserven.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=06_Grundnahrungsmittel.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=07_Kaffee__Tee__Süßwaren__Knabberartikel.html",
		"https://www.kaufland.de/angebote/aktuelle-woche.category=08_Getränke__Spirituosen.html"
	)

	override def fetch(): List[MarketOfferEntry] = {
		urls.flatMap(fetchUrl).toList
	}

	private def fetchUrl(url: String): List[MarketOfferEntry] = {
		var offerList = List[MarketOfferEntry]()

		val result = Unirest.get(url).asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		val list = document >> elementList(".t-offers-overview__list-item")
		list.foreach(i => {
			print((i >> element(".m-offer-tile__title")).text + "\t\t\t")
			print((i >> element(".m-offer-tile__price")).text + "\t\t\t")
			println(url)
		})
		offerList
	}
}
