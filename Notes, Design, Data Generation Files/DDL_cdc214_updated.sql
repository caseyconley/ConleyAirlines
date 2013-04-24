drop table PILOT;
drop table CUSTOMER;
drop table PLANE;
drop table PLANE_TYPE;
drop table IS_TYPE;
drop table PILOT_CERTIFICATION;
drop table CREDIT_CARD;
drop table BILLING_INFO;
drop table AIRPORT;
drop table TRIP;
drop table LEG;
drop table RESERVATION;
drop table PLANE_SEATS;
drop table RESERVED_SEAT;
drop table CUSTOMER_RESERVED;
drop table CREDIT_CARD_RESERVED;
drop table LEG_TO;
drop table LEG_FROM;
drop table LEG_OF_TRIP;

create table PILOT
	(ID numeric(10),
	FIRST_NAME varchar(30) not null,
	LAST_NAME varchar(30) not null,
	primary key(ID));
	
create table CUSTOMER
	(ID numeric(10),
	FIRST_NAME varchar(30) not null,
	LAST_NAME varchar(30) not null,
	primary key(ID));

create table PLANE
	(PLANE_ID numeric(6),
	DATE_PUT_IN_SERVICE date,
	MILES_FLOWN numeric(8),
	primary key(PLANE_ID));

create table PLANE_TYPE
	(TYPE numeric(3),
  NUM_SEATS numeric(3),
	primary key(TYPE));
  
create table IS_TYPE
  (PLANE_ID numeric(6),
  TYPE numeric(3),
  foreign key (PLANE_ID) references PLANE(PLANE_ID) on delete cascade,
  foreign key (TYPE) references PLANE_TYPE(TYPE) on delete cascade,
  primary key (PLANE_ID, TYPE));

create table PILOT_CERTIFICATION
	(PILOT_ID numeric(10),
	TYPE numeric(3),
	foreign key(PILOT_ID) references PILOT(ID) on delete cascade,
	foreign key(TYPE) references PLANE_TYPE(TYPE) on delete cascade,
	primary key(PILOT_ID, TYPE));
	
create table CREDIT_CARD
	(CARD_NUM numeric(16), /* between 1000000000000000 and 9999999999999999*/
	SECURITY_CODE numeric(3) not null, /* between 100 and 999 */
	EXPIRATION_DATE numeric(4) not null, /* between 1000 and 9999 */
	STREET_ADDRESS varchar(30) not null,
	CITY varchar(20) not null,
	STATE varchar(2) not null,
	ZIP numeric(5) not null,
	primary key(CARD_NUM));
	
create table BILLING_INFO
	(CARD_NUM numeric(16),
	CUSTOMER_ID numeric(10),
	primary key(CUSTOMER_ID, CARD_NUM),
	foreign key(CARD_NUM) references CREDIT_CARD(CARD_NUM) on delete cascade,
	foreign key(CUSTOMER_ID) references CUSTOMER(ID) on delete cascade);
	
create table AIRPORT
	(CALLSIGN varchar(3) unique,
	CITY varchar(20) not null);

create table TRIP
	(TRIP_NUMBER numeric(4),
	TRIP_DATE date,
	START_AIRPORT varchar(3),
	END_AIRPORT varchar(3),
	PRICE numeric(5),
	primary key (TRIP_NUMBER, TRIP_DATE),
	foreign key (START_AIRPORT) references AIRPORT(CALLSIGN) on delete cascade,
	foreign key (END_AIRPORT) references AIRPORT(CALLSIGN) on delete cascade);
	
create table LEG
	(LEG_ID numeric(6),
	PLANE_ID numeric(6),
	PILOT_ID numeric(10),
	TIME_DEPARTURE timestamp,
	TIME_ARRIVAL timestamp,
	PRICE numeric(4),
	primary key(LEG_ID),
	foreign key (PLANE_ID) references PLANE on delete cascade,
	foreign key (PILOT_ID) references PILOT on delete set null);

create table RESERVATION
	(RESERVATION_ID numeric(10),
	TRIP_NUMBER numeric(4),
	TRIP_DATE date,
	SEAT_CLASS varchar(8) check (SEAT_CLASS in ('first', 'economy', 'business')),
	PRICE numeric(5),
	primary key(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS),
	foreign key(TRIP_NUMBER, TRIP_DATE) references TRIP(TRIP_NUMBER, TRIP_DATE) on delete cascade);	

create table PLANE_SEATS
	(PLANE_ID numeric(6),
	SEAT_CLASS varchar(8) check (SEAT_CLASS in ('first', 'economy', 'business')),
	SEAT_NUMBER numeric(3),
	foreign key (PLANE_ID) references PLANE(PLANE_ID) on delete cascade,
	primary key (PLANE_ID, SEAT_CLASS));

create table RESERVED_SEAT
	(RESERVATION_ID numeric(10),
	TRIP_NUMBER numeric(4),
	SEAT_CLASS varchar(8),
	CUSTOMER_ID numeric(10),
	LEG_ID numeric(6),
	SEAT_NUMBER numeric(3),
	primary key (CUSTOMER_ID, LEG_ID, SEAT_NUMBER),
	foreign key (LEG_ID) references LEG(LEG_ID),
	foreign key(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) references RESERVATION(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) on delete cascade,
	foreign key(CUSTOMER_ID) references CUSTOMER(ID)) on delete cascade;
	
create table CUSTOMER_RESERVED
	(CUSTOMER_ID numeric(10),
	RESERVATION_ID numeric(10),
	TRIP_NUMBER numeric(4),
	SEAT_CLASS varchar(8),
	foreign key(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) references 
		RESERVATION(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) on delete cascade,
	foreign key(CUSTOMER_ID) references CUSTOMER(ID) on delete cascade,
	primary key(CUSTOMER_ID, RESERVATION_ID));
	
create table CREDIT_CARD_RESERVED
	(CARD_NUM numeric(16),
	RESERVATION_ID numeric(10),
	TRIP_NUMBER numeric(4),
	SEAT_CLASS varchar(8),
	foreign key(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) references 
    RESERVATION(RESERVATION_ID, TRIP_NUMBER, SEAT_CLASS) on delete cascade,
	foreign key(CARD_NUM) references CREDIT_CARD(CARD_NUM) on delete cascade,
	primary key(CARD_NUM, RESERVATION_ID));
	
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
	foreign key (TRIP_NUMBER, TRIP_DATE) references TRIP(TRIP_NUMBER, TRIP_DATE) on delete cascade,
	foreign key (LEG_ID) references LEG(LEG_ID) on delete cascade,
	primary key (TRIP_NUMBER, TRIP_DATE, LEG_ID));
	

	
	


	