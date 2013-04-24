select reservation_id, trip_number, to_char(trip_date, 'DD-MON-YYYY'), seat_class, card_num 
from customer_reserved natural join reservation natural join credit_card_reserved 
where customer_id = 893264;
/* why isn't this working??? */
/*
ORA-25155: column used in NATURAL join cannot have qualifier
25155. 00000 -  "column used in NATURAL join cannot have qualifier"
*Cause:    Columns that are used for a named-join (either a NATURAL join
           or a join with a USING clause) cannot have an explicit qualifier.
*Action:   Remove the qualifier.
Error at Line: 4 Column: 31
*/