package de.tobias.cooknow.server.settings

/**
  * Created by tobias on 05.02.17.
  */
object SettingsHandler {

	def loader = new PropertiesSettingsHandler()

	def saver = new PropertiesSettingsHandler()

}
