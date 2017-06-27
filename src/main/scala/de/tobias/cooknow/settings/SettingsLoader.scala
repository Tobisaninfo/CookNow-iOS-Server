package de.tobias.cooknow.settings

import java.io.IOException
import java.nio.file.Path

/**
  * Handles a settings object, to deserialize it.
  *
  * @author tobias
  */
trait SettingsLoader {

	/**
	  * Load a file and create a settings object.
	  *
	  * @param path path of settings file.
	  * @throws IOException file could not be read
	  * @return settings object.
	  */
	@throws[IOException]
	def load(path: Path): Settings
}
