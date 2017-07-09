package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * The unit type for an ingredient.
  *
  * @param id   id (from database)
  * @param name name (only for debugging)
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

	private var cache = Map[Int, UnitType]()

	/**
	  * Query a unit type from the database
	  *
	  * @param id   unit type id
	  * @param conn database connection
	  * @return unit type or null
	  */
	def apply(id: Int, conn: Connection): UnitType = {
		if (cache.contains(id)) {
			cache(id)
		} else {
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
}