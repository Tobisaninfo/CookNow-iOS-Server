package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import org.json.JSONObject
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 10.05.17.
  */
class RecipeGet(val conn: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe WHERE id=?")
		stat.setInt(1, request.params(":id").toInt)

		val result = stat.executeQuery()

		var recipe: Recipe = null

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val description = result.getString("description")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			recipe = new Recipe(id, name, description, difficulty, time)
		}

		result.close()
		stat.close()

		if (recipe == null) {
			Spark.halt(400, "Bad Request: Recipe not exsits")
		}

		// Build JsonObject
		val jsonObject = new JSONObject()
		jsonObject.put("id", recipe.id)
		jsonObject.put("name", recipe.name)
		jsonObject.put("description", recipe.descript)
		jsonObject.put("time", recipe.time)
		jsonObject.put("difficulty", recipe.difficulty)
		jsonObject
	}
}
