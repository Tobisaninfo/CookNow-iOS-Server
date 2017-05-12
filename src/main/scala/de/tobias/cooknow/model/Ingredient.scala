package de.tobias.cooknow.model

import org.json.{JSONArray, JSONObject}

/**
  * Created by tobias on 11.05.17.
  */
class Ingredient(val id: Int, val name: String, val unit: UnitType, val property: List[Property]) {
	def toJson: JSONObject = {
		val jsonObject = new JSONObject()

		val unitJson = new JSONObject()
		unitJson.put("id", unit.id)
		unitJson.put("name", unit.name)

		val propertiesArray = new JSONArray()
		property.map(prop => {
			val propertyJson = new JSONObject()
			propertyJson.put("id", prop.id)
			propertyJson.put("name", prop.name)
			propertyJson
		}).foreach(propertiesArray.put)

		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("unit", unitJson)
		jsonObject.put("property", propertiesArray)
		jsonObject
	}

}
