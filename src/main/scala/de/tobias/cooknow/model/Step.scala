package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.{JSONArray, JSONObject}

/**
  * Model for a step of a recipe.
  *
  * @param id          step id
  * @param content     step content
  * @param order       order in the recipe
  * @param ingredients list of needed ingredients
  * @param items       needed items
  */
class Step(val id: Int, val content: String, val order: Int, val ingredients: List[IngredientUse], val items: List[Item]) extends JsonConverter {
	override def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("content", content)
		jsonObject.put("order", order)

		val ingredientJsonArray = new JSONArray()
		ingredients.map(_.toJson).foreach(ingredientJsonArray.put)
		jsonObject.put("ingredients", ingredientJsonArray)

		val itemJsonArray = new JSONArray()
		items.map(_.toJson).foreach(itemJsonArray.put)
		jsonObject.put("items", itemJsonArray)

		jsonObject
	}
}

object Step {
	/**
	  * Query all steps for a recipe from the database.
	  *
	  * @param recipeID   recipe id
	  * @param connection database connection
	  * @return list of steps
	  */
	def apply(recipeID: Int, connection: Connection): List[Step] = {
		val stat = connection.prepareStatement("SELECT * FROM Step WHERE recipeID = ? ORDER BY 'order' ASC")
		stat.setInt(1, recipeID)
		val result = stat.executeQuery()

		var list = List[Step]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("content")
			val order = result.getInt("order")
			val step = new Step(id, name, order, IngredientUse(id, connection), Item(id, connection))
			list ::= step
		}

		result.close()
		stat.close()
		list
	}
}