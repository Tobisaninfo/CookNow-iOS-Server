package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Model for an item. An item could be a pot.
  *
  * @param id   id
  * @param name item name
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
	/**
	  * Query all items for an step from the database.
	  *
	  * @param stepID     step id
	  * @param connection database connection
	  * @return list of items
	  */
	def apply(stepID: Int, connection: Connection): List[Item] = {
		val stat = connection.prepareStatement("SELECT * FROM Item i JOIN ItemUse u ON u.itemID = i.id  WHERE stepID = ?")
		stat.setInt(1, stepID)
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
