package de.tobias.cooknow.model.market

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class Market(val id: Int, val name: String) extends JsonConverter {

	def toJson:JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject
	}
}

object Market {
	def apply(conn: Connection): List[Market] = {
		val stat = conn.prepareStatement("SELECT * FROM Market")
		val result = stat.executeQuery()

		var list = List[Market]()
		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")

			val market = new Market(id, name)
			list ::= market
		}

		result.close()
		stat.close()
		list
	}

	def apply(conn: Connection, name: String): Market = {
		val stat = conn.prepareStatement("SELECT * FROM Market WHERE name like ?")
		stat.setString(1, name)
		val result = stat.executeQuery()

		val market = if(result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")

			new Market(id, name)
		} else {
			null
		}

		result.close()
		stat.close()
		market
	}
}