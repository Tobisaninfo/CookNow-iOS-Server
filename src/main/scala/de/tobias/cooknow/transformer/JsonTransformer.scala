package de.tobias.cooknow.transformer

import de.tobias.cooknow.JsonConverter
import org.json.JSONArray
import spark.ResponseTransformer

/**
  * Class to convert a concrete object into a json object. Handles JsonConverter and List.
  *
  * @author tobias
  */
class JsonTransformer extends ResponseTransformer {
	override def render(o: scala.Any): String = {
		val response = o match {
			case json: JsonConverter =>
				json.toJson.toString
			case list: List[JsonConverter] =>
				val jsonArray = new JSONArray()
				list.map(_.toJson).foreach(jsonArray.put)
				jsonArray.toString
			case _ =>
				o.toString
		}
		response
	}
}
