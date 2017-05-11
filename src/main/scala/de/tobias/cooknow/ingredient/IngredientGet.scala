package de.tobias.cooknow.ingredient

import java.sql.Connection

import de.tobias.cooknow.model.{Ingredient, Property, UnitType}
import org.json.{JSONArray, JSONObject}
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 11.05.17.
  */
class IngredientGet(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val stat = conn.prepareStatement("SELECT * FROM Ingredient WHERE id = ?")
		stat.setInt(1, request.params(":id").toInt)

		val result = stat.executeQuery()

		val ingerdient = if (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val unitType = result.getInt("unitType")


			new Ingredient(id, name, getUnitType(unitType), getProperties(id))
		} else {
			null
		}

		result.close()
		stat.close()

		if (ingerdient == null) {
			Spark.halt(400, "Bad Request: Ingredient not exists")
		}

		val jsonObject = new JSONObject()

		val unitJson = new JSONObject()
		unitJson.put("id", ingerdient.unit.id)
		unitJson.put("name", ingerdient.unit.name)

		val propertiesArray = new JSONArray()
		ingerdient.property.map(prop => {
			val propertyJson = new JSONObject()
			propertyJson.put("id", prop.id)
			propertyJson.put("name", prop.name)
			propertyJson
		}).foreach(propertiesArray.put)

		jsonObject.put("id", ingerdient.id)
		jsonObject.put("name", ingerdient.name)
		jsonObject.put("unit", unitJson)
		jsonObject.put("property", propertiesArray)
		jsonObject
	}

	private def getUnitType(id: Int): UnitType = {
		val stat = conn.prepareStatement("SELECT * FROM Unit WHERE id = ?")
		stat.setInt(1, id)
		val result = stat.executeQuery()

		val unit = if (result.next()) {
			new UnitType(id, result.getString("name"))
		} else {
			null
		}

		result.close()
		stat.close()

		unit
	}

	private def getProperties(id: Int): List[Property] = {
		val stat = conn.prepareStatement("SELECT * FROM IngredientProperty JOIN Property ON " +
			"IngredientProperty.propertyID = Property.id WHERE ingredientID = ?")
		stat.setInt(1, id)
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
