package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class UnitType(val id: Int, val name: String) extends JsonConverter {
	def toJson: JSONObject = {
		val unitJson = new JSONObject()
		unitJson.put("id", id)
		unitJson.put("name", name)
		unitJson
	}
}

object UnitType {
	def apply(id: Int, conn: Connection): UnitType = {
		val stat = conn.prepareStatement("SELECT * FROM Unit WHERE id = ?")
		stat.setInt(1, id)
		val result = stat.executeQuery()

		val unit = if (result.next()) {
			new UnitType(result.getInt("id"), result.getString("name"))
		} else {
			null
		}

		result.close()
		stat.close()

		unit
	}
}