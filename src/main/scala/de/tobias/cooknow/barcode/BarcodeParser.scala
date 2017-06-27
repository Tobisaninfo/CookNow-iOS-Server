package de.tobias.cooknow.barcode

import com.mashape.unirest.http.Unirest
import de.tobias.cooknow.model.Barcode
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.element

/**
  * Handle barcode requests to an api.
  *
  * @author tobias
  */
object BarcodeParser {

	/**
	  * Get the product that matches the barcode.
	  *
	  * @param barcode barcode numbers
	  * @return product or null
	  */
	def getProduct(barcode: String): Barcode = {
		val result = Unirest.post("https://opengtindb.org/index.php").queryString("ean", barcode).queryString("cmd", "ean1").asBinary()

		val browser = JsoupBrowser()
		val document = browser.parseInputStream(result.getBody, "iso-8859-1")

		val product = try {
			val name = getName(document)
			val description = getDescription(document)

			new Barcode(barcode, null, if (!name.isEmpty) {
				name
			} else {
				description
			})
		} catch {
			case _: Exception => null
		}
		product
	}

	/**
	  * Parse the website the get the name for a product.
	  *
	  * @param document html document
	  * @return name
	  */
	private def getName(document: Document): String = {
		val list1 = document >> element("html") >> element("body") >> element("div") >> element("table") >> element("tbody") >> elementList("tr")
		val list2 = list1.applyOrElse(2, null) >> element("td") >> elementList("table")
		val list3 = list2.applyOrElse(2, null) >> element("tbody") >> elementList("tr")
		val list4 = list3.applyOrElse(2, null) >> elementList("td")
		val list5 = list4.applyOrElse(1, null)

		list5.text
	}

	/**
	  * Parse the website the get the description for a product.
	  *
	  * @param document html document
	  * @return description
	  */
	private def getDescription(document: Document): String = {
		val list1 = document >> element("html") >> element("body") >> element("div") >> element("table") >> element("tbody") >> elementList("tr")
		val list2 = list1.applyOrElse(2, null) >> element("td") >> elementList("table")
		val list3 = list2.applyOrElse(2, null) >> element("tbody") >> elementList("tr")
		val list4 = list3.applyOrElse(3, null) >> elementList("td")
		val list5 = list4.applyOrElse(1, null)

		list5.text
	}
}
