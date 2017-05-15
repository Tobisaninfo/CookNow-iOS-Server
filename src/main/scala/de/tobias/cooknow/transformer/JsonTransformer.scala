package de.tobias.cooknow.transformer

import de.tobias.cooknow.JsonConvertable
import org.json.JSONArray
import spark.ResponseTransformer

/**
  * Created by tobias on 15.05.17.
  */
class JsonTransformer extends ResponseTransformer {
	override def render(o: scala.Any): String = {
		val response = o match {
			case json: JsonConvertable =>
				json.toJson.toString
			case list: List[JsonConvertable] =>
				val jsonArray = new JSONArray()
				list.map(_.toJson).foreach(jsonArray.put)
				jsonArray.toString
			case _ =>
				o.toString
		}
		response
	}
}
