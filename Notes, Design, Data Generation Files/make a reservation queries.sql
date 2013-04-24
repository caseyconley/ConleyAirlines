
select leg_id , seat_number
from leg L natural join plane_seats P
where seat_class = seatClass
and leg_id = legID
where not exists
	(select seat_number 
	from reserved_seat
	where leg_id = L.leg_id)
order by seat_number asc;

"insert into reserved_seat values (" + reservationID + ", " + tripToReserve + ", " + seatClass + ", " + customerID + ", " + legId + ", " + seat_number + ")"

/* insert into reservation */
"insert into reservation values ("+reservationID+", "+tripToReserve+", '"+tripDate+"', '"+seatClass+"', "+tripPrice+")"

/* insert into customer_reserved */
"insert into customer_reserved values ("+customerID+", "+reservationID+", "+tripToReserve+", '"+seatClass+"')"

/* insert into credit_card_reserved */
"insert into credit_card_reserved values ("+cardNum+", "+reservationID+", "+tripToReserve+", '"+seatClass+"')"