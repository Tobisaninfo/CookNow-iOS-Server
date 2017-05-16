package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.{JSONArray, JSONObject}

/**
  * Created by tobias on 10.05.17.
  */
class Recipe(val id: Int, val name: String, val descript: String, val difficulty: Int, val time: Int, val ingredients: List[IngredientUse]) extends JsonConverter {

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("description", descript)
		jsonObject.put("time", time)
		jsonObject.put("difficulty", difficulty)

		val ingredientJsonArray = new JSONArray()
		ingredients.map(_.toJson).foreach(ingredientJsonArray.put)
		jsonObject.put("ingredient", ingredientJsonArray)

		jsonObject
	}
}

object Recipe {
	def apply(conn: Connection): List[Recipe] = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe")
		val result = stat.executeQuery()

		var list = List[Recipe]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val description = result.getString("description")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			val recipe = new Recipe(id, name, description, difficulty, time, IngredientUse(id, conn))
			list ::= recipe
		}

		result.close()
		stat.close()
		list
	}

	def apply(id: Int, conn: Connection): Recipe = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe WHERE id=?")
		stat.setInt(1, id)
		val result = stat.executeQuery()

		val recipe = if (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val description = result.getString("description")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			new Recipe(id, name, description, difficulty, time, IngredientUse(id, conn))
		} else {
			null
		}

		result.close()
		stat.close()

		recipe
	}
}
