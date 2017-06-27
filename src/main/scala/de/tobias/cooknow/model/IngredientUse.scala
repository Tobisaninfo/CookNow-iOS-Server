package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * The connection between an ingredient and the step of a recipe. In this class is the amount of the ingredient for an
  * recipe stored. Also the amount depended price is stored.
  *
  * @param ingredient Ingredient
  * @param amount     amount
  * @param price      price
  */
class IngredientUse(val ingredient: Ingredient, val amount: Double, price: Double) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("ingredient", ingredient.toJson)
		jsonObject.put("amount", amount)
		jsonObject.put("price", price)
		jsonObject
	}
}

object IngredientUse {
	/**
	  * Query the list of ingredients for a step from the database.
	  *
	  * @param stepID     step id
	  * @param connection database connection
	  * @return list of ingredients with amount and price.
	  */
	def apply(stepID: Int, connection: Connection): List[IngredientUse] = {
		val stat = connection.prepareStatement("SELECT * FROM IngredientUse u JOIN Ingredient i ON u.ingredientID = i.id WHERE u.stepID = ?")
		stat.setInt(1, stepID)
		println(stat)
		val result = stat.executeQuery()

		var list = List[IngredientUse]()
		while (result.next()) {
			val amount = result.getDouble("amount")
			val ingredientId = result.getInt("ingredientId")
			val price = result.getDouble("price")

			val use = new IngredientUse(Ingredient(ingredientId, connection), amount, price)
			list ::= use
		}
		result.close()
		stat.close()

		list
	}
}
