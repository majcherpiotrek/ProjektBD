insert into wyniki_zawodow(id_zawody, nr_rejestracyjny,miejsce_pojedyncza_eliminacja, miejsce_podwojna_eliminacja, zdobyte_punkty_rankingowe,sezon_sezon)
values((select id_zawody from zawody where id_zawody = 12016),(select nr_rejestracyjny from zawodnicy where nr_rejestracyjny=101), 3,3,75,(select sezon_sezon from zawody where id_zawody=12016));
