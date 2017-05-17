package de.tobias.cooknow.model.market.offer

import java.nio.file.{Files, Paths}
import java.time.DayOfWeek.SATURDAY
import java.time.temporal.TemporalAdjusters.next
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
			.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.1.1 Safari/603.2.4")
			.header("DNT", "1")
			.asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "utf-8")

		val today = LocalDate.now()
		val nextSunday = today.`with`(next(SATURDAY))
		val date = Date.from(nextSunday.atStartOfDay(ZoneId.systemDefault).toInstant)

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
