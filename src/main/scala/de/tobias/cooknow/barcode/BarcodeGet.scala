package de.tobias.cooknow.barcode

import spark.{Request, Response, Route}

/**
  * Created by tobias on 10.05.17.
  */
class BarcodeGet extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val barcode = request.queryParams("ean")
		val name = BarcodeParser.getProductName(barcode)

		name
	}
}
