package de.tobias.cooknow

import java.sql.Connection

/**
  * MySQL helper.
  *
  * @author tobias
  */
object MySQLStore {

	def init(connection: Connection) {
		def createTable(sql: String) = {
			val preparedStatement = connection.prepareStatement(sql)
			preparedStatement.execute()
			preparedStatement.close()
		}

		createTable(
			"""CREATE TABLE IF NOT EXISTS `Barcode` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `ingredientID` int(11) DEFAULT NULL,
			  |  `name` varchar(150) DEFAULT NULL,
			  |  `code` varchar(13) DEFAULT NULL,
			  |  `amount` double DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Ingredient` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(100) DEFAULT NULL,
			  |  `unitType` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `IngredientProperty` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `ingredientID` int(11) DEFAULT NULL,
			  |  `propertyID` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `IngredientUse` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `stepID` int(11) DEFAULT NULL,
			  |  `ingredientID` int(11) DEFAULT NULL,
			  |  `amount` float DEFAULT NULL,
			  |  `price` float DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Item` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(100) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `ItemUse` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `stepID` int(11) DEFAULT NULL,
			  |  `itemID` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Market` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(50) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `MarketOffer` (
			  |  `marketID` int(11) NOT NULL DEFAULT '0',
			  |  `name` varchar(300) DEFAULT '',
			  |  `price` float DEFAULT NULL,
			  |  `expires` date DEFAULT NULL
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Property` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(30) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Recipe` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(100) DEFAULT NULL,
			  |  `time` int(11) DEFAULT NULL,
			  |  `difficulty` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Step` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `recipeID` int(11) DEFAULT NULL,
			  |  `order` int(11) DEFAULT NULL,
			  |  `content` text,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Tip` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `title` varchar(200) DEFAULT NULL,
			  |  `content` text,
			  |  `categoryID` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `TipCategory` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(100) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Unit` (
			  |  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
			  |  `name` varchar(20) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
	}
}
