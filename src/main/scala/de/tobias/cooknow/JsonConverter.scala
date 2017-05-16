package de.tobias.cooknow

import org.json.JSONObject

/**
  * Interface to convert a object into a json format.
  *
  * @author tobias
  */
trait JsonConverter {
	def toJson: JSONObject
}
