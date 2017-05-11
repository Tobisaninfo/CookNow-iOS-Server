package de.tobias.cooknow.model.market.offer

import com.mashape.unirest.http.Unirest
import de.tobias.cooknow.model.market.MarketOfferEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element, elementList}

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferParserRewe extends MarketOfferParser {
	override def fetch(): List[MarketOfferEntry] = {
		var offerList = List[MarketOfferEntry]()

		val result = Unirest.get("https://www.rewe.de/angebote/").asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		val list = document >> elementList(".product")

		println(list.size)

		list.foreach(i => {
			val name = (i >> element(".dotdot")).text
			val price = (i >> element(".price ")).text
		})
		offerList
	}
}
