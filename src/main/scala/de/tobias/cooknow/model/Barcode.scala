package de.tobias.cooknow.model

import java.sql.Connection

import de.tobias.cooknow.JsonConvertable
import org.json.JSONObject

/**
  * Created by tobias on 12.05.17.
  */
class Barcode(val code: String, val name: String, val price: Float = -1) extends JsonConvertable  {
	def insert(connection: Connection) = {
		val stat = connection.prepareStatement("INSERT INTO Barcode (name, code, price) VALUES (?, ?, ?)")
		stat.setString(1, name)
		stat.setString(2, code)
		stat.setFloat(3, price)
		stat.executeUpdate()
		stat.close()
	}

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("code", code)
		jsonObject.put("name", name)
		jsonObject.put("price", price)
		jsonObject
	}

}

object Barcode {
	def apply(ean: String, connection: Connection): Barcode = {
		val stat = connection.prepareStatement("SELECT * FROM Barcode WHERE code = ?")
		stat.setString(1, ean)
		val result = stat.executeQuery()

		val product = if (result.next()) {
			val name = result.getString("name")
			val price = result.getFloat("price")

			result.close()
			stat.close()

			new Barcode(ean, name, price)
		} else {
			null
		}
		product
	}
}
