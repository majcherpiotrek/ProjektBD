insert into zawody 
(id_zawody, nazwa_zawodow, miejsce_rozegrania, data, pula_nagrod, sezon_sezon )
values ('22016','PWA Fuerteventura', 'Hiszpania','2016-07-20','50000', (SELECT sezon FROM sezon where sezon=2016));

