package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConverter
import org.json.{JSONArray, JSONObject}

/**
  * Model for a recipe.
  *
  * @param id         recipe id
  * @param name       recipe name
  * @param difficulty difficulty (1-3)
  * @param time       time (in min)
  * @param steps      list of steps
  */
class Recipe(val id: Int, val name: String, val difficulty: Int, val time: Int, val steps: List[Step]) extends JsonConverter {

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("id", id)
		jsonObject.put("name", name)
		jsonObject.put("time", time)
		jsonObject.put("difficulty", difficulty)

		val stepsJsonArray = new JSONArray()
		steps.map(_.toJson).foreach(stepsJsonArray.put)
		jsonObject.put("steps", stepsJsonArray)

		jsonObject
	}
}

object Recipe {
	/**
	  * Query all recipes from the database.
	  *
	  * @param conn database connection
	  * @return list of recipes
	  */
	def apply(conn: Connection): List[Recipe] = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe")
		val result = stat.executeQuery()

		var list = List[Recipe]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			val recipe = new Recipe(id, name, difficulty, time, Step(id, conn))
			list ::= recipe
		}

		result.close()
		stat.close()
		list
	}

	/**
	  * Query one recipe from the database.
	  *
	  * @param id   recipe id
	  * @param conn database connection
	  * @return recipe
	  */
	def apply(id: Int, conn: Connection): Recipe = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe WHERE id=?")
		stat.setInt(1, id)
		val result = stat.executeQuery()

		val recipe = if (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			new Recipe(id, name, difficulty, time, Step(id, conn))
		} else {
			null
		}

		result.close()
		stat.close()

		recipe
	}
}
