package de.tobias.cooknow.model.market.offer

import java.text.SimpleDateFormat
import java.time.DayOfWeek.SATURDAY
import java.time.temporal.TemporalAdjusters.next
import java.time.{LocalDate, ZoneId}
import java.util.{Date, Locale}

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

		val today = LocalDate.now()
		val nextSunday = today.`with`(next(SATURDAY))
		val date = Date.from(nextSunday.atStartOfDay(ZoneId.systemDefault).toInstant)

		val days = document >> elementList(".mod-offer-stage__section")
		val list = days.head >> elementList(".mod-article-tile__content")

		list.foreach(i => {
			val name = (i >> element(".mod-article-tile__title")).text
			val price = (i >> element(".price__main ")).text

			val priceValue = parse(price, Locale.GERMAN) * 0.01f
			val offer = new MarketOfferEntry(name, priceValue, date)
			offerList ::= offer
		})
		offerList
	}
}
