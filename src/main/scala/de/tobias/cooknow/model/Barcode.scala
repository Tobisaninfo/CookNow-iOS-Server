package de.tobias.cooknow.model

import org.json.JSONObject

/**
  * Created by tobias on 12.05.17.
  */
class Barcode(val code: String, val name: String, val price: Float = -1) {
	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("code", code)
		jsonObject.put("name", name)
		jsonObject.put("price", price)
		jsonObject
	}

}
