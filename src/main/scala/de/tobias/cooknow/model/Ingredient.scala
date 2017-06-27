package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.{JSONArray, JSONObject}

/**
  * Model for ingredients.
  *
  * @param id         id
  * @param name       name
  * @param unit       unit type
  * @param properties properties for the ingredient
  */
class Ingredient(val id: Int, val name: String, val unit: UnitType, val properties: List[Property]) extends JsonConverter {
	def toJson: JSONObject = {
		val jsonObject = new JSONObject()

		val propertiesArray = new JSONArray()
		properties.map(_.toJson).foreach(propertiesArray.put)

		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("unit", unit.toJson)
		jsonObject.put("property", propertiesArray)
		jsonObject
	}

}

object Ingredient {
	/**
	  * Query all ingredients from the database.
	  *
	  * @param conn database connection
	  * @return list of all ingredients
	  */
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

	/**
	  * Query an ingredients from the database. Once it is queried, it is stored in a local cache.
	  *
	  * @param id   id
	  * @param conn database connection
	  * @return ingredient.
	  */
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
