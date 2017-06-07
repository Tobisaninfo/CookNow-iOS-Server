package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 18.05.17.
  */
class Item(val id: Int, val name: String) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject
	}
}

object Item {
	def apply(recipeID: Int, connection: Connection): List[Item] = {
		val stat = connection.prepareStatement("SELECT * FROM Item i JOIN ItemUse u ON u.itemID = i.id  WHERE stepID = ?")
		stat.setInt(1, recipeID)
		val result = stat.executeQuery()

		var list = List[Item]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val item = new Item(id, name)
			list ::= item
		}

		result.close()
		stat.close()
		list
	}
}
