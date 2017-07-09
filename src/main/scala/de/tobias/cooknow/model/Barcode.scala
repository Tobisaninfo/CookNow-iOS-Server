package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Model for a special product that has a barcode.
  *
  * @param code       barcode
  * @param ingredient matched ingredient
  * @param name       name
  * @param amount     amount in the product
  */
class Barcode(val code: String, var ingredient: Ingredient, val name: String, val amount: Double = 0) extends JsonConverter {
	/**
	  * Insert a new product into the database
	  *
	  * @param connection database connection
	  */
	def insert(connection: Connection): Unit = {
		if (ingredient != null) {
			val stat = connection.prepareStatement("INSERT INTO Barcode (name, ingredientID, code, amount) VALUES (?, ?, ?, ?)")
			stat.setString(1, name)
			stat.setInt(2, ingredient.id)
			stat.setString(3, code)
			stat.setDouble(4, amount)
			stat.executeUpdate()
			stat.close()
		} else {
			val stat = connection.prepareStatement("INSERT INTO Barcode (name, code, amount) VALUES (?, ?, ?)")
			stat.setString(1, name)
			stat.setString(2, code)
			stat.setDouble(3, amount)
			stat.executeUpdate()
			stat.close()
		}
	}

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("code", code)
		jsonObject.put("name", name)
		jsonObject.put("amount", amount)
		jsonObject.put("ingredient", ingredient.toJson)
		jsonObject
	}

}

object Barcode {
	/**
	  * Query a barcode from the database.
	  *
	  * @param ean        code
	  * @param connection database connection
	  * @return barcode
	  */
	def apply(ean: String, connection: Connection): Barcode = {
		val stat = connection.prepareStatement("SELECT * FROM Barcode WHERE code = ?")
		stat.setString(1, ean)
		val result = stat.executeQuery()

		val product = if (result.next()) {
			val name = result.getString("name")
			val amount = result.getDouble("amount")
			val ingredientID = result.getInt("ingredientID")

			result.close()
			stat.close()

			new Barcode(ean, Ingredient(ingredientID, connection), name, amount)
		} else {
			null
		}
		product
	}
}
