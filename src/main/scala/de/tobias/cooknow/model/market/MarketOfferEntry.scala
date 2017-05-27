package de.tobias.cooknow.model.market

import java.sql
import java.sql.Connection
import java.util.Date

import de.tobias.cooknow.JsonConverter
import org.json.JSONObject

/**
  * Created by tobias on 11.05.17.
  */
class MarketOfferEntry(val name: String, val price: Float, val expires: Date) extends JsonConverter {
	def insert(connection: Connection, market: Market): Unit = {
		val stat = connection.prepareStatement("INSERT INTO MarketOffer (marketID, name, price, expires) VALUES (?, ?, ?, ?)")
		stat.setInt(1, market.id)
		stat.setString(2, name)
		stat.setFloat(3, price)
		stat.setDate(4, new sql.Date(expires.getTime))
		stat.executeUpdate()
	}

	def toJson: JSONObject = {
		val jsonObject = new JSONObject()
		jsonObject.put("name", name)
		jsonObject.put("price", price)
		jsonObject.put("expires", expires)
		jsonObject
	}

	override def toString = s"MarketOfferEntry($name, $price)"
}

object MarketOfferEntry {
	def dropTable(connection: Connection):Unit = {
		val stat = connection.prepareStatement("DELETE FROM MarketOffer")
		stat.executeUpdate()
	}

	def apply(marketID: Int, conn: Connection): List[MarketOfferEntry] = {
		val stat = conn.prepareStatement("SELECT * FROM MarketOffer WHERE marketID = ?")
		stat.setInt(1, marketID)
		val result = stat.executeQuery()

		var list = List[MarketOfferEntry]()

		while (result.next()) {
			val name = result.getString("name")
			val price = result.getFloat("price")
			val expires = result.getDate("expires")

			val entry = new MarketOfferEntry(name, price, expires)
			list ::= entry
		}

		result.close()
		stat.close()
		list
	}
}
