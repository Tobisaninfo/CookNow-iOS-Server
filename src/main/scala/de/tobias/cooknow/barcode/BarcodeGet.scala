package de.tobias.cooknow.barcode

import java.sql.Connection

import de.tobias.cooknow.model.Barcode
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 10.05.17.
  */
class BarcodeGet(val connection: Connection) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val ean = request.queryParams("ean")

		var product = Barcode(ean, connection)

		if (product == null) {
			product = BarcodeParser.getProduct(ean)
			if (product != null) {
				product.insert(connection)
			}
		}

		if (product == null) {
			Spark.halt(400, "Bad Request: Product not found")
		}
		product
	}
}
