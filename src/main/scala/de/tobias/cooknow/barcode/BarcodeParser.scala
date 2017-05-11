package de.tobias.cooknow.barcode

import com.mashape.unirest.http.Unirest
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.element

/**
  * Created by tobias on 10.05.17.
  */
object BarcodeParser {

	def getProductName(barcode: String): String = {
		val result = Unirest.post("https://opengtindb.org/index.php").queryString("ean", barcode).queryString("cmd", "ean1").asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "iso-8859-1")

		val list1 = document >> element("html") >> element("body") >> element("div") >> element("table") >> element("tbody") >> elementList("tr")
		val list2 = list1.applyOrElse(2, null) >> element("td") >> elementList("table")
		val list3 = list2.applyOrElse(2, null) >> element("tbody") >> elementList("tr")
		val list4 = list3.applyOrElse(2, null) >> elementList("td")
		val list5 = list4.applyOrElse(1, null)

		list5.text
	}
}
