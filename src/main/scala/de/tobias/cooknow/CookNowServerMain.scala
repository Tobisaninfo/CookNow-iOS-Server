package de.tobias.cooknow

import java.nio.file.Paths
import java.sql.DriverManager

import de.tobias.cooknow.barcode.BarcodeGet
import de.tobias.cooknow.ingredient.IngredientList
import de.tobias.cooknow.recipe.{RecipeGet, RecipeList}
import de.tobias.cooknow.server.settings.SettingsHandler
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

	// Recipe
	path("/recipe", () => {
		get("/", new RecipeList(databaseConnection))
		get("/:id", new RecipeGet(databaseConnection))
	})

	path("/ingredient", () => {
		get("/", new IngredientList(databaseConnection))
		get("/:id", new RecipeGet(databaseConnection))
	})

	// Ingredient

	// Market

	// Barcode
	get("/barcode", new BarcodeGet())

	// DEBUG
	exception(classOf[Exception], (exception, _, _) => {
		exception.printStackTrace()
		halt(500, s"internal error: ${exception.getLocalizedMessage}")
	})
}
