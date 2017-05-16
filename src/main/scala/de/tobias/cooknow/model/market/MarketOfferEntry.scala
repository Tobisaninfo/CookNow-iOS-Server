package de.tobias.cooknow.model.market

import java.sql.Connection
import java.util.Date

import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferEntry(name: String, price: Float, expires: Date) {

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("name", name)
		jsonObject.put("price", price)
		jsonObject.put("expires", expires)
		jsonObject
	}
}

object MarketOfferEntry {
	def apply(marketID: Int, conn: Connection): List[MarketOfferEntry] = {
		val stat = conn.prepareStatement("SELECT * FROM MarketOffer WHERE marketID = ?")
		stat.setInt(1, marketID)
		val result = stat.executeQuery()

		var list = List[MarketOfferEntry]()

		while (result.next()) {
			val name = result.getString("name")
			val price = result.getFloat("price")
			val expires = result.getDate("expires")

			val entry = new MarketOfferEntry(name, price, expires)
			list ::= entry
		}

		result.close()
		stat.close()
		list
	}
}
