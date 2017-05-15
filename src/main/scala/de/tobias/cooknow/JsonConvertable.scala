package de.tobias.cooknow

import org.json.JSONObject

/**
  * Created by tobias on 15.05.17.
  */
trait JsonConvertable {
	def toJson: JSONObject
}
