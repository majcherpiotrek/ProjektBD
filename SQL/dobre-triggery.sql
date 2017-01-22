USE `windsurfingDB`;

DELIMITER $$

DROP TRIGGER IF EXISTS windsurfingDB.zawodnicy_BEFORE_DELETE$$
USE `windsurfingdb`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `windsurfingDB`.`zawodnicy_BEFORE_DELETE` BEFORE DELETE ON `zawodnicy` FOR EACH ROW
BEGIN

delete from wyniki_zawodow
where nr_rejestracyjny = OLD.nr_rejestracyjny;
 
END$$
DELIMITER ;

USE `windsurfingDB`;

DELIMITER $$

DROP TRIGGER IF EXISTS windsurfingDB.wyniki_zawodow_AFTER_INSERT$$
USE `windsurfingDB`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `windsurfingdb`.`wyniki_zawodow_AFTER_INSERT` AFTER INSERT ON `wyniki_zawodow` FOR EACH ROW
BEGIN 
if(not exists(SELECT * FROM ranking_sezonu where ranking_sezonu.sezon_sezon = new.sezon_sezon and ranking_sezonu.nr_rejestracyjny_zawodnika = new.nr_rejestracyjny))
then	
	insert into ranking_sezonu(nr_rejestracyjny_zawodnika,suma_punktow_rankingowych,sezon_sezon)
	values(new.nr_rejestracyjny, new.zdobyte_punkty_rankingowe,new.sezon_sezon);
else
	update ranking_sezonu
	set suma_punktow_rankingowych = (select sum(zdobyte_punkty_rankingowe) from wyniki_zawodow where sezon_sezon = new.sezon_sezon and nr_rejestracyjny = new.nr_rejestracyjny)
	where ranking_sezonu.sezon_sezon = new.sezon_sezon and ranking_sezonu.nr_rejestracyjny_zawodnika = new.nr_rejestracyjny;
end if;
END$$
DELIMITER ;
USE `windsurfingDB`;

DELIMITER $$

DROP TRIGGER IF EXISTS windsurfingDB.wyniki_zawodow_AFTER_UPDATE$$
USE `windsurfingDB`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `windsurfingDB`.`wyniki_zawodow_AFTER_UPDATE` AFTER UPDATE ON `wyniki_zawodow` FOR EACH ROW
BEGIN 
if(not exists(SELECT * FROM ranking_sezonu where ranking_sezonu.sezon_sezon = new.sezon_sezon and ranking_sezonu.nr_rejestracyjny_zawodnika = new.nr_rejestracyjny))
then	
	insert into ranking_sezonu(nr_rejestracyjny_zawodnika,suma_punktow_rankingowych,sezon_sezon)
	values(new.nr_rejestracyjny, new.zdobyte_punkty_rankingowe,new.sezon_sezon);
else
	update ranking_sezonu
	set suma_punktow_rankingowych = (select sum(zdobyte_punkty_rankingowe) from wyniki_zawodow where sezon_sezon = new.sezon_sezon and nr_rejestracyjny = new.nr_rejestracyjny)
	where ranking_sezonu.sezon_sezon = new.sezon_sezon and ranking_sezonu.nr_rejestracyjny_zawodnika = new.nr_rejestracyjny;
end if;
END$$
DELIMITER ;
USE `windsurfingDB`;

DELIMITER $$

DROP TRIGGER IF EXISTS windsurfingdb.wyniki_zawodow_BEFORE_DELETE$$
USE `windsurfingdb`$$
CREATE DEFINER=`root`@`localhost` TRIGGER `windsurfingdb`.`wyniki_zawodow_BEFORE_DELETE` After DELETE ON `wyniki_zawodow` FOR EACH ROW
BEGIN 
if(not exists(SELECT * FROM wyniki_zawodow where wyniki_zawodow.nr_rejestracyjny = OLD.nr_rejestracyjny and wyniki_zawodow.sezon_sezon = OLD.sezon_sezon))
then
	delete from ranking_sezonu
    where ranking_sezonu.nr_rejestracyjny_zawodnika = OLD.nr_rejestracyjny and ranking_sezonu.sezon_sezon = OLD.sezon_sezon;
else
	update ranking_sezonu
	set suma_punktow_rankingowych = (select sum(zdobyte_punkty_rankingowe) from wyniki_zawodow where sezon_sezon = OLD.sezon_sezon and nr_rejestracyjny = OLD.nr_rejestracyjny)
	where ranking_sezonu.sezon_sezon = OLD.sezon_sezon and ranking_sezonu.nr_rejestracyjny_zawodnika = OLD.nr_rejestracyjny;
end if;
END$$
DELIMITER ;
