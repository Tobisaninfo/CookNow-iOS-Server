package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 19.05.17.
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