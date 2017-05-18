package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 17.05.17.
  */
class Tip(val id: Int, val title: String, val content: String) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("title", title)
		jsonObject.put("content", content)
		jsonObject
	}
}

object Tip {
	def apply(connection: Connection): List[Tip] = {
		val stat = connection.prepareStatement("SELECT * FROM Tip")
		val result = stat.executeQuery()

		var list = List[Tip]()

		while (result.next()) {
			val id = result.getInt("id")
			val title = result.getString("title")
			val content = result.getString("content")

			val tip = new Tip(id, title, content)
			list::= tip
		}
		result.close()
		stat.close()

		list
	}
}