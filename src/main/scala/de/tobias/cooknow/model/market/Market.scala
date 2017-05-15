package de.tobias.cooknow.model.market

import java.sql.Connection

import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class Market(id: Int, name: String) {

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
}