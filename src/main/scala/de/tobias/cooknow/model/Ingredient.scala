package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.{JSONArray, JSONObject}

/**
  * Created by tobias on 11.05.17.
  */
class Ingredient(val id: Int, val name: String, val unit: UnitType, val property: List[Property]) extends JsonConverter  {
	def toJson: JSONObject = {
		val jsonObject = new JSONObject()

		val propertiesArray = new JSONArray()
		property.map(_.toJson).foreach(propertiesArray.put)

		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("unit", unit.toJson)
		jsonObject.put("property", propertiesArray)
		jsonObject
	}

}

object Ingredient {
	def apply(conn: Connection): List[Ingredient] = {
		val stat = conn.prepareStatement("SELECT * FROM Ingredient")
		val result = stat.executeQuery()

		var list = List[Ingredient]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val unitType = result.getInt("unitType")

			val ingredient = new Ingredient(id, name, UnitType(unitType, conn), Property(id, conn))
			list ::= ingredient
		}

		result.close()
		stat.close()

		list
	}

	private var cache = Map[Int, Ingredient]()

	def apply(id: Int, conn: Connection): Ingredient = {
		if (cache.contains(id)) {
			cache(id)
		} else {
			val stat = conn.prepareStatement("SELECT * FROM Ingredient WHERE id = ?")
			stat.setInt(1, id)

			val result = stat.executeQuery()

			val ingredient = if (result.next()) {
				val id = result.getInt("id")
				val name = result.getString("name")
				val unitType = result.getInt("unitType")


				new Ingredient(id, name, UnitType(unitType, conn), Property(id, conn))
			} else {
				null
			}

			result.close()
			stat.close()
			cache += id -> ingredient
			ingredient
		}
	}
}
