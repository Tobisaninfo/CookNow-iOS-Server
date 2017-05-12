package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.MarketOfferEntry
import org.json.JSONArray
import spark.{Request, Response, Route}

/**
  * Created by tobias on 12.05.17.
  */
class MarketOfferList(val conn: Connection) extends Route{
	override def handle(request: Request, response: Response): AnyRef = {
		val marketId = request.params(":id").toInt

		val stat = conn.prepareStatement("SELECT * FROM MarketOffer WHERE marketID = ?")
		stat.setInt(1, marketId)
		val result = stat.executeQuery()

		var list = List[MarketOfferEntry]()

		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")
			val price = result.getFloat("price")
			val expires = result.getDate("expires")

			val entry = new MarketOfferEntry(id, name, price, expires)
			list ::= entry
		}

		result.close()
		stat.close()

		val jsonArray = new JSONArray()
		list.map(_.toJson).foreach(jsonArray.put)
		jsonArray
	}
}
