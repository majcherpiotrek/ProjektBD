CREATE DATABASE `windsurfingDB` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `administratorzy` (
  `login` varchar(45) NOT NULL,
  `haslo` varchar(45) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ranking_sezonu` (
  `nr_rejestracyjny_zawodnika` int(11) NOT NULL,
  `suma_punktow_rankingowych` int(11) DEFAULT NULL,
  `sezon_sezon` int(11) NOT NULL,
  PRIMARY KEY (`nr_rejestracyjny_zawodnika`,`sezon_sezon`),
  KEY `nr_rejestracyjny zawodnika_idx` (`nr_rejestracyjny_zawodnika`),
  KEY `fk_ranking_sezonu_sezon1_idx` (`sezon_sezon`),
  CONSTRAINT `fk_ranking_sezonu_sezon1` FOREIGN KEY (`sezon_sezon`) REFERENCES `sezon` (`sezon`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `nr_rejestracyjny zawodnika` FOREIGN KEY (`nr_rejestracyjny_zawodnika`) REFERENCES `zawodnicy` (`nr_rejestracyjny`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sezon` (
  `sezon` int(11) NOT NULL,
  PRIMARY KEY (`sezon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `wyniki_zawodow` (
  `id_zawody` int(11) NOT NULL,
  `nr_rejestracyjny` int(11) NOT NULL,
  `miejsce_pojedyncza_eliminacja` int(11) DEFAULT NULL,
  `miejsce_podwojna_eliminacja` int(11) DEFAULT NULL,
  `zdobyte_punkty_rankingowe` int(11) DEFAULT NULL,
  `sezon_sezon` int(11) NOT NULL,
  PRIMARY KEY (`id_zawody`,`nr_rejestracyjny`),
  KEY `nr_rejestracyjny_idx` (`nr_rejestracyjny`),
  KEY `sezon_sezon` (`sezon_sezon`),
  CONSTRAINT `id_zawody` FOREIGN KEY (`id_zawody`) REFERENCES `zawody` (`id_zawody`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `nr_rejestracyjny` FOREIGN KEY (`nr_rejestracyjny`) REFERENCES `zawodnicy` (`nr_rejestracyjny`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `wyniki_zawodow_ibfk_1` FOREIGN KEY (`sezon_sezon`) REFERENCES `sezon` (`sezon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `zawodnicy` (
  `nr_rejestracyjny` int(11) NOT NULL,
  `imie` varchar(45) DEFAULT NULL,
  `nazwisko` varchar(45) DEFAULT NULL,
  `plec` varchar(10) DEFAULT NULL,
  `narodowosc` varchar(45) DEFAULT NULL,
  `marka_desek` varchar(45) DEFAULT NULL,
  `marka_zagli` varchar(45) DEFAULT NULL,
  `sponsorzy` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nr_rejestracyjny`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `zawody` (
  `id_zawody` int(11) NOT NULL,
  `nazwa_zawodow` varchar(45) DEFAULT NULL,
  `miejsce_rozegrania` varchar(45) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  `pula_nagrod` int(11) DEFAULT NULL,
  `sezon_sezon` int(11) NOT NULL,
  PRIMARY KEY (`id_zawody`),
  KEY `fk_zawody_sezon1_idx` (`sezon_sezon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

