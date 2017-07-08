package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Property for an ingredient. The concrete objects are defined in the database.
  *
  * @param id   id
  * @param name name
  */
class Property(val id: Int, val name: String) extends JsonConverter {
	def toJson: JSONObject = {
		val propertyJson = new JSONObject()
		propertyJson.put("id", id)
		propertyJson.put("name", name)
		propertyJson
	}
}

object Property {
	/**
	  * Query all properties for an ingredient from the database
	  *
	  * @param ingredientID ingredient id
	  * @param conn         database connection
	  * @return list of properties
	  */
	def apply(ingredientID: Int, conn: Connection): List[Property] = {
		val stat = conn.prepareStatement("SELECT * FROM IngredientProperty JOIN Property ON " +
			"IngredientProperty.propertyID = Property.id WHERE ingredientID = ?")
		stat.setInt(1, ingredientID)
		val result = stat.executeQuery()

		var list = List[Property]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")

			val property = new Property(id, name)
			list ::= property
		}

		result.close()
		stat.close()

		list
	}

	/**
	  * Query all properties from the database
	  *
	  * @param conn         database connection
	  * @return list of properties
	  */
	def apply(conn: Connection): List[Property] = {
		val stat = conn.prepareStatement("SELECT * FROM Property")
		val result = stat.executeQuery()
		var list = List[Property]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")

			val property = new Property(id, name)
			list ::= property
		}

		result.close()
		stat.close()

		list
	}
}