package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 12.05.17.
  */
class Barcode(val code: String, val ingredient: Ingredient, val name: String, val amount: Double = 0) extends JsonConverter  {
	def insert(connection: Connection) = {
		val stat = connection.prepareStatement("INSERT INTO Barcode (name, code, amount) VALUES (?, ?, ?)")
		stat.setString(1, name)
		stat.setString(2, code)
		stat.setDouble(3, amount)
		stat.executeUpdate()
		stat.close()
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
