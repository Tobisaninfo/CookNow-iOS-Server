package de.tobias.cooknow.server.settings

import java.io.IOException
import java.nio.file.Path

/**
  * Created by tobias on 05.02.17.
  */
trait SettingsLoader {

	@throws[IOException]
	def load(path: Path): Settings
}
