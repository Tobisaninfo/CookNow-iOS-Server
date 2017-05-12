package de.tobias.cooknow.market

import java.sql.Connection

import de.tobias.cooknow.model.market.Market
import org.json.JSONArray
import spark.{Request, Response, Route}

/**
  * Created by tobias on 12.05.17.
  */
class MarketList(val conn: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val stat = conn.prepareStatement("SELECT * FROM Market")
		val result = stat.executeQuery()

		var list = List[Market]()
		while (result.next()) {
			val id = result.getInt("id")
			val name = result.getString("name")

			val market = new Market(id, name)
			list ::= market
		}

		result.close()
		stat.close()

		val jsonArray = new JSONArray()
		list.map(_.toJson).foreach(jsonArray.put)
		jsonArray
	}
}
