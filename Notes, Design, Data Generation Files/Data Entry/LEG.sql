insert into leg (leg_id, plane_id, pilot_id, time_arrival, time_departure)
values (1, 999237, 1526344666, 
  TO_DATE('05-MAY-2013 08:30 A.M.','DD-MON-YYYY HH:MI A.M.'), 
  TO_DATE('05-MAY-2013 012:30 A.M.','DD-MON-YYYY HH:MI A.M.')); /*jfk to ord*/
insert into leg (leg_id, plane_id, pilot_id, time_arrival, time_departure)
values (2, 226074, 5405771001, 
  TO_DATE('05-MAY-2013 01:30 P.M.','DD-MON-YYYY HH:MI A.M.'), 
  TO_DATE('05-MAY-2013 03:30 P.M.','DD-MON-YYYY HH:MI A.M.')); /*ord to sfo*/
insert into leg (leg_id, plane_id, pilot_id, time_arrival, time_departure)
values (3, 296554, 6363870250, TO_DATE('05-MAY-2013 10:30 A.M.','DD-MON-YYYY HH:MI A.M.'), 
  TO_DATE('05-MAY-2013 01:00 P.M.','DD-MON-YYYY HH:MI A.M.')); /*ord to lax*/
insert into leg (leg_id, plane_id, pilot_id, time_arrival, time_departure)
values (4, 822239, 8932379549, TO_DATE('05-MAY-2013 02:00 P.M.','DD-MON-YYYY HH:MI A.M.'), 
  TO_DATE('05-MAY-2013 04:30 P.M.','DD-MON-YYYY HH:MI A.M.')); /*sfo to ord*/