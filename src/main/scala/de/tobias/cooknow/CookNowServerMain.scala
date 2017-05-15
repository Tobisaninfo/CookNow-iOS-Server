package de.tobias.cooknow

import java.nio.file.Paths
import java.sql.DriverManager

import de.tobias.cooknow.barcode.BarcodeGet
import de.tobias.cooknow.ingredient.{IngredientGet, IngredientList}
import de.tobias.cooknow.market.{MarketList, MarketOfferList}
import de.tobias.cooknow.recipe.{RecipeGet, RecipeList}
import de.tobias.cooknow.server.settings.SettingsHandler
import de.tobias.cooknow.transformer.JsonTransformer
import spark.Spark._

/**
  * Created by tobias on 02.05.17.
  */
object CookNowServerMain extends App {

	// Settings
	val settings = SettingsHandler.loader.load(Paths.get("settings.properties"))

	// Database
	private val databaseUrl = s"jdbc:mysql://${settings.db_host}:${settings.db_port}/${settings.db_database}?" +
		s"autoReconnect=true&wait_timeout=86400&serverTimezone=Europe/Berlin"
	private val databaseConnection = DriverManager.getConnection(databaseUrl, settings.db_username, settings.db_password)

	port(8001)
	secure("deploy/keystore.jks", settings.keystorePassword, null, null)

	// Static Images
	/*
	 * Image Location: x -> id
	 *		Recipe: /images/recipe/x.png
	 *		Ingredient: /images/ingredient/x.png
	 *		MarketLogo: /images/market/x.png
	 */
	externalStaticFileLocation(settings.download_folder)

	// Recipe
	path("/recipe", () => {
		get("/", new RecipeList(databaseConnection), new JsonTransformer)
		get("/:id", new RecipeGet(databaseConnection), new JsonTransformer)
	})

	// Ingredient
	path("/ingredient", () => {
		get("/", new IngredientList(databaseConnection), new JsonTransformer)
		get("/:id", new IngredientGet(databaseConnection), new JsonTransformer)
	})

	// Market
	path("/market", () => {
		get("/", new MarketList(databaseConnection), new JsonTransformer)

		path("/offer", () => {
			get("/:id", new MarketOfferList(databaseConnection), new JsonTransformer)
		})
	})

	// Barcode
	get("/barcode", new BarcodeGet(databaseConnection), new JsonTransformer)

	// DEBUG
	exception(classOf[Exception], (exception, _, _) => {
		exception.printStackTrace()
		halt(500, s"internal error: ${exception.getLocalizedMessage}")
	})
}
