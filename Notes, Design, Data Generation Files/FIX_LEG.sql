drop table leg_of_trip;
drop table leg_to;
drop table leg_from;
drop table reserved_seat;
drop table leg;

create table LEG
	(LEG_ID numeric(6),
	PLANE_ID numeric(6),
	PILOT_ID numeric(10),
	TIME_DEPARTURE date,
	TIME_ARRIVAL date,
	primary key(LEG_ID),
	foreign key (PLANE_ID) references PLANE on delete cascade,
	foreign key (PILOT_ID) references PILOT on delete set null);
  
create table RESERVED_SEAT
	(RESERVATION_ID numeric(10),
	TRIP_NUMBER numeric(4),
	SEAT_CLASS varchar(8),
	CUSTOMER_ID numeric(10),
	LEG_ID numeric(6),
	SEAT_NUMBER numeric(3),
	primary key (CUSTOMER_ID, LEG_ID, SEAT_NUMBER),
	foreign key (LEG_ID) references LEG(LEG_ID),
	foreign key(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) references RESERVATION(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS),
	foreign key(CUSTOMER_ID) references CUSTOMER(ID));
  
create table LEG_TO
	(LEG_ID numeric(6),
	AIRPORT varchar(3),
	foreign key (AIRPORT) references AIRPORT(CALLSIGN) on delete cascade,
	foreign key (LEG_ID) references LEG(LEG_ID) on delete cascade,
	primary key (LEG_ID, AIRPORT));
	
create table LEG_FROM
	(LEG_ID numeric(6),
	AIRPORT varchar(3),
	foreign key (AIRPORT) references AIRPORT(CALLSIGN) on delete cascade,
	foreign key (LEG_ID) references LEG(LEG_ID) on delete cascade,
	primary key (LEG_ID, AIRPORT));
	
create table LEG_OF_TRIP
	(TRIP_NUMBER numeric(4),
	TRIP_DATE date,
	LEG_ID numeric(6),
	foreign key (TRIP_NUMBER, TRIP_DATE) references TRIP(TRIP_NUMBER, TRIP_DATE),
	foreign key (LEG_ID) references LEG(LEG_ID),
	primary key (TRIP_NUMBER, TRIP_DATE, LEG_ID));