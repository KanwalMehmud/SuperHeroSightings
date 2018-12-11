create database if not exists SuperHeroSightings;

use SuperHeroSightings;

create table if not exists SuperHero
(
SuperHeroId int not null auto_increment,
HeroName varchar(50) not null,
SuperPower varchar(50) not null,
Description varchar(50) not null,
primary key (SuperHeroId)
);

create table if not exists Location
(
LocationId int not null auto_increment,
LocationName varchar(50),
Description varchar(50),
Address varchar(50),
Latitude decimal(10,6) not null,
Longitude decimal(10,6) not null,
primary key (LocationId)
);

create table if not exists Organization
(
OrganizationId int not null auto_increment,
OrgName varchar(50) not null,
Description varchar(50),
Address varchar(50) not null,
phone varchar(10),
primary key(OrganizationId)
);



create table if not exists SuperHero_Organization
(
SuperHeroId int not null,
OrganizationId int not null,
primary key(SuperHeroId,OrganizationId ),
foreign key(SuperHeroId) references SuperHero(SuperHeroId),
foreign key(OrganizationId) references Organization(OrganizationId)
);

create table if not exists Sighting
(
SightingId int not null auto_increment,
SightDate date not null,
SightTime time,
SuperHeroId int not null,
LocationId int not null,
primary key(SightingId),
foreign key(SuperHeroId) references SuperHero(SuperHeroId),
foreign key(LocationId) references Location(LocationId)
);


CREATE TABLE IF NOT EXISTS `users` (
 `user_id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(20) NOT NULL,
 `password` varchar(100) NOT NULL,
 `enabled` tinyint(1) NOT NULL,
 PRIMARY KEY (`user_id`),
 KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;


INSERT INTO `users` (`user_id`, `username`, `password`, `enabled`) VALUES
(1, 'admin', 'password', 1),
(2, 'user', 'password', 1);


CREATE TABLE IF NOT EXISTS `authorities` (
 `username` varchar(20) NOT NULL,
 `authority` varchar(20) NOT NULL,
 KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities` (`username`, `authority`) VALUES
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER'),
('user', 'ROLE_USER');


ALTER TABLE `authorities`
 ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`);


UPDATE `SuperHeroSightings`.`users`
SET
`password` = '$2a$10$wkr6moeM8pDTfyBpDYUDqOi9UqwS/oNX4MoF/LrwIo/gpithPS/T6'
WHERE `user_id` = 1;


UPDATE `SuperHeroSightings`.`users`
SET
`username`='newUser',
`password` = '$2a$10$wkr6moeM8pDTfyBpDYUDqOi9UqwS/oNX4MoF/LrwIo/gpithPS/T6'
WHERE `user_id` = 5;

select * from authorities;
SELECT * FROM users;


/************************************************************************************************************************************************/

insert into SuperHero(HeroName, SuperPower, Description) values('Aquaman','strength', 'hero');
insert into SuperHero(HeroName, SuperPower, Description) values('SuperMan','Flying', 'hero');
insert into SuperHero(HeroName, SuperPower, Description) values('Wolverine','Strength', 'hero');
insert into SuperHero(HeroName, SuperPower, Description) values('Flash','Speed', 'hero');
insert into SuperHero(HeroName, SuperPower, Description) values('Magneto ','magnetism', 'villain');

insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('AlleyA','AlleyA Description','888 E 63th Street,Atlanta,Georgia','33.748997','-87.387985');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('AlleyB','AlleyB Description','856 E 16th Street, Minneapolis, Minnesota','44.977753','-93.265015');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('CoffeeShopA','A coffee shop','6636 E 18th Street,Fresno,California','36.761651','-119.785858');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('BankX','A bank','113 E 44th Street,Irvine,California','33.685696','-117.825981');

insert into Organization(OrgName, Description, Address, Phone) values ('Justice League','A base for superheros in Atlantis','123 E 13th Street,Atlanta,Georgia','1231111');
insert into Organization(OrgName, Description, Address, Phone) values ('Super Friends','A base for superheros in Metropolis','145 E 16th Street, Minneapolis, Minnesota', '9996365');
insert into Organization(OrgName, Description, Address, Phone) values ('X-Nation','A base for superheros in Alchemax','583 E 18th Street,Fresno,California', '1125456');

insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (1,1);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (1,2);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (1,3);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (2,1);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (2,2);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (2,3);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (3,1);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (3,2);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (3,3);



insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/02/06','12:15:00',1,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/03/25','09:25:00',2,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2017/06/13','06:45:00',3,3);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2017/02/06','12:15:00',1,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2012/03/25','09:25:00',2,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2013/09/23','06:45:00',4,3);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2014/02/16','12:15:00',1,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2017/03/20','09:25:00',5,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/07/13','06:45:00',4,3);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/02/20','12:15:00',1,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/05/25','09:25:00',4,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/01/13','06:45:00',3,3);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/02/06','12:15:00',2,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2016/03/25','09:25:00',2,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/01/13','06:45:00',5,3);
/******************************************************************************************************************************************************/

select sighting.SightingId , superhero.HeroName,  location.LocationName, location.Address, location.Latitude, location.Longitude, sighting.SightDate, sighting.SightTime
from superhero
inner join sighting on sighting.SuperHeroId = superhero.SuperHeroId
inner join location on sighting.LocationId = location.LocationId
order  by sighting.SightDate DESC , sighting.SightTime DESC limit 10;



select * from sighting;

select * from sighting order  by sighting.SightDate DESC , sighting.SightTime DESC limit 10;


select * from sighting limit 5;


select superhero.SuperHeroId, HeroName, SuperPower, Description
from superHero
inner join sighting on sighting.SuperHeroId = superhero.SuperHeroId
where sightingId= 1;


select sighting.SightingId, LocationName, Description, Address, Latitude, Longitude
from location
inner join sighting on sighting.LocationId = location.LocationId
where sightingId=9;


/*superheros and their sighting locations */
select superhero.HeroName, location.LocationName, sighting.SightDate,location.Description, location.Address, location.Latitude, location.Longitude 
from superHero
join sighting on  sighting.SuperHeroId= superhero.SuperHeroId
join location on sighting.locationId=location.locationId
group by superhero.SuperHeroId;

/*superheros and their organizations*/
select superhero.HeroName, superhero.SuperPower,superhero.Description
from superhero
inner join superhero_organization on superhero_organization.SuperHeroId = superhero.SuperHeroId
inner join organization on superhero_organization.OrganizationId= organization.OrganizationId
where superhero_organization.OrganizationId=1
;

/*all members of a particular organization */
select superhero.HeroName, organization.OrgName, organization.Description, organization.Address, organization.phone
from organization
inner join superhero_organization on superhero_organization.OrganizationId= organization.OrganizationId
inner join superhero on superhero_organization.SuperHeroId = superhero.SuperHeroId
where organization.OrganizationId=1;

drop database SuperHeroSightings;
