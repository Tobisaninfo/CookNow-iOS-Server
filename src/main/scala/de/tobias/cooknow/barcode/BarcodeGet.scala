package de.tobias.cooknow.barcode

import java.sql.Connection

import de.tobias.cooknow.model.Barcode
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 10.05.17.
  */
class BarcodeGet(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val barcode = request.queryParams("ean")

		val stat = connection.prepareStatement("SELECT * FROM Barcode WHERE code = ?")
		stat.setString(1, barcode)
		val result = stat.executeQuery()

		val product = if (result.next()) {
			val name = result.getString("name")
			val price = result.getFloat("price")

			result.close()
			stat.close()

			new Barcode(barcode, name, price)
		} else {
			val product = BarcodeParser.getProduct(barcode)
			if (product != null) {
				val stat = connection.prepareStatement("INSERT INTO Barcode (name, code, price) VALUES (?, ?, ?)")
				stat.setString(1, product.name)
				stat.setString(2, product.code)
				stat.setFloat(3, product.price)
				stat.executeUpdate()
				stat.close()
			}
			product
		}

		if (product == null) {
			Spark.halt(400, "Bad Request: Product not found")
		}
		product.toJson
	}
}
