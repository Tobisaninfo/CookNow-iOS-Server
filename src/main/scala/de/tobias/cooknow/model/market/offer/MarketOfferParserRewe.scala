package de.tobias.cooknow.model.market.offer

import java.nio.file.{Files, Paths}
import java.time.{LocalDate, ZoneId}
import java.util.Date

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

		val result = Unirest.get("https://www.rewe.de/angebote/")
			.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
			.asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		Files.write(Paths.get("rewe.html"), document.toHtml.toString.getBytes)

		val days = (document >> element(".days")).text
		val localDate = LocalDate.now().plusDays(days.toInt)
		val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault).toInstant)

		val list = document >> elementList(".product")

		list.foreach(i => {
			val name = (i >> element(".dotdot")).text
			val price = (i >> element(".price ")).text

			val marketOfferEntry = new MarketOfferEntry(name, price.toFloat, date)
			offerList ::= marketOfferEntry
		})
		offerList
	}
}
