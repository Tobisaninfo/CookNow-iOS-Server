package de.tobias.cooknow.settings

import java.io.IOException
import java.nio.file.Path

/**
  * Handles a settings object, to serialize it.
  *
  * @author tobias
  */
trait SettingsSaver {

	/**
	  * Save the object to a given path.
	  *
	  * @param settings settings object.
	  * @param path     path to save the settings.
	  * @throws IOException File write operation failed.
	  */
	@throws[IOException]
	def save(settings: Settings, path: Path)

	/**
	  * Create a default settings file on disk.
	  *
	  * @param path path to save the settings.
	  * @throws IOException File write operation failed.
	  */
	@throws[IOException]
	def default(path: Path): Unit = save(new Settings(), path)
}
