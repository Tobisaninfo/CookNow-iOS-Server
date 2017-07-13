package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Model for a tip.
  *
  * @param id      tip id
  * @param title   tip title
  * @param content tip content
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
	/**
	  * Query all tips for category from the database.
	  *
	  * @param category   category
	  * @param connection database connection
	  * @return list of tips
	  */
	def apply(category: Int, connection: Connection): List[Tip] = {
		val stat = connection.prepareStatement("SELECT * FROM Tip WHERE categoryID = ?")
		stat.setInt(1, category)
		val result = stat.executeQuery()

		var list = List[Tip]()

		while (result.next()) {
			val id = result.getInt("id")
			val title = result.getString("title")
			val content = result.getString("content")

			val tip = new Tip(id, title, content)
			list ::= tip
		}
		result.close()
		stat.close()

		list
	}

	/**
	  * Query all tips from the database.
	  *
	  * @param connection database connection
	  * @return list of tips
	  */
	def apply(connection: Connection): List[Tip] = {
		val stat = connection.prepareStatement("SELECT * FROM Tip")
		val result = stat.executeQuery()

		var list = List[Tip]()

		while (result.next()) {
			val id = result.getInt("id")
			val title = result.getString("title")
			val content = result.getString("content")

			val tip = new Tip(id, title, content)
			list ::= tip
		}
		result.close()
		stat.close()

		list
	}
}