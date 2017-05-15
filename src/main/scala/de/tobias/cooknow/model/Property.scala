package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConvertable
import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class Property(val id: Int, val name: String) extends JsonConvertable  {
	def toJson: JSONObject = {
		val propertyJson = new JSONObject()
		propertyJson.put("id", id)
		propertyJson.put("name", name)
		propertyJson
	}
}

object Property {
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
}