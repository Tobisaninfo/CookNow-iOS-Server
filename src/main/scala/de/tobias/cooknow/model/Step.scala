package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 18.05.17.
  */
class Step(val id: Int, val content: String, val order: Int) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("content", content)
		jsonObject.put("order", order)
		jsonObject
	}
}

object Step {
	def apply(recipeID: Int, connection: Connection): List[Step] = {
		val stat = connection.prepareStatement("SELECT * FROM Step WHERE recipeID = ? ORDER BY 'order' ASC")
		stat.setInt(1, recipeID)
		val result = stat.executeQuery()

		var list = List[Step]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("content")
			val order = result.getInt("order")
			val step = new Step(id, name, order)
			list ::= step
		}

		result.close()
		stat.close()
		list
	}
}