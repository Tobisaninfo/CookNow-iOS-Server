package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Model for the tip category.
  *
  * @param id   category id.
  * @param name category name.
  */
class TipCategory(val id: Int, val name: String) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject
	}
}

object TipCategory {
	/**
	  * Query all categories from the database.
	  *
	  * @param connection database connection
	  * @return list of categories
	  */
	def apply(connection: Connection): List[TipCategory] = {
		val stat = connection.prepareStatement("SELECT * FROM TipCategory")
		val result = stat.executeQuery()

		var list = List[TipCategory]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val category = new TipCategory(id, name)

			list ::= category
		}

		result.close()
		stat.close()
		list
	}
}