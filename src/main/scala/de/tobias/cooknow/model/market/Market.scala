package de.tobias.cooknow.model.market

import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class Market(id: Int, name: String) {

	def toJson:JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject
	}
}
