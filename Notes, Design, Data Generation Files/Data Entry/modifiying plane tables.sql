drop table PILOT_CERTIFICATION;
drop table PLANE_TYPE;


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