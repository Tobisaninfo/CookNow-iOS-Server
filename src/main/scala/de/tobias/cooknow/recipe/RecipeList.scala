package de.tobias.cooknow.recipe

import java.sql.Connection

import de.tobias.cooknow.model.Recipe
import org.json.JSONArray
import spark.{Request, Response, Route}

/**
  * Created by tobias on 10.05.17.
  */
class RecipeList(val conn: Connection) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val stat = conn.prepareStatement("SELECT * FROM Recipe")
		val result = stat.executeQuery()

		var list = List[Recipe]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val description = result.getString("description")
			val time = result.getInt("time")
			val difficulty = result.getInt("difficulty")

			val recipe = new Recipe(id, name, description, difficulty, time)
			list ::= recipe
		}

		result.close()
		stat.close()

		val jsonArray = new JSONArray()
		list.map(_.toJson).foreach(jsonArray.put)
		jsonArray
	}
}
