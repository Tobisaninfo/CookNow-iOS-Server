package de.tobias.cooknow.settings

/**
  * CookNow Server Settings class.
  *
  * @author tobias
  */
class Settings {

	/**
	  * Database Host. Default value = localhost.
	  */
	val db_host: String = "localhost"
	/**
	  * Database Port. Default value = 3306.
	  */
	val db_port: Int = 3306
	/**
	  * Database username. Default value = root.
	  */
	val db_username: String = "root"
	/**
	  * Database user password. Default value = password.
	  */
	val db_password: String = "password"
	/**
	  * Database name. Default value = CookNow
	  */
	val db_database: String = "CookNow"

	/**
	  * Folder for Images. Images are inside /res/[]/[id].jpg. Default value = ./
	  */
	val download_folder: String = "./"

	/**
	  * Java Keystore Password for HTTPS Server. Default value = password.
	  */
	val keystorePassword = "password"
}
