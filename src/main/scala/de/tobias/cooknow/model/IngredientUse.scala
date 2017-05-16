package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 15.05.17.
  */
class IngredientUse(val ingredient: Ingredient, val amount: Double) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("ingredient", ingredient.toJson)
		jsonObject.put("amount", amount)
		jsonObject
	}
}

object IngredientUse {
	def apply(recipeId: Int, connection: Connection): List[IngredientUse] = {
		val stat = connection.prepareStatement("SELECT * FROM IngredientUse u JOIN Ingredient i ON u.ingredientID = i.id WHERE u.recipeID = ?")
		stat.setInt(1, recipeId)
		val result = stat.executeQuery()

		var list = List[IngredientUse]()
		while (result.next()) {
			val amount = result.getDouble("amount")
			val ingredientId = result.getInt("ingredientId")

			val use = new IngredientUse(Ingredient(ingredientId, connection), amount)
			list ::= use
		}
		result.close()
		stat.close()

		list
	}
}
