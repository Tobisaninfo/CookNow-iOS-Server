package de.tobias.cooknow.model.market

import java.util.Date

import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferEntry(id: Int, name: String, price: Float, expires: Date) {

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("price", price)
		jsonObject.put("expires", expires)
		jsonObject
	}
}
