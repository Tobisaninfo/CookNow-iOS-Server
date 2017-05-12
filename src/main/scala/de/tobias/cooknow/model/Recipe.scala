package de.tobias.cooknow.model

import org.json.JSONObject

/**
  * Created by tobias on 10.05.17.
  */
class Recipe(val id: Int, val name: String, val descript: String, val difficulty: Int, val time: Int) {

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("description", descript)
		jsonObject.put("time", time)
		jsonObject.put("difficulty", difficulty)
		jsonObject
	}
}
