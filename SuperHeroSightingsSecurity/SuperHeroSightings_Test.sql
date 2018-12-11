drop database if exists superherosightings_Test;
create database if not exists superherosightings_Test;

use superherosightings_Test;

create table if not exists SuperHero
(
SuperHeroId int not null auto_increment,
HeroName varchar(50) not null,
SuperPower varchar(50) not null,
Description varchar(50),
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

/************************************************************************************************************************************************/
use superherosightings_Test;

insert into SuperHero(HeroName, SuperPower) values('Aquaman','strength');
insert into SuperHero(HeroName, SuperPower) values('SuperMan','Flying');
insert into SuperHero(HeroName, SuperPower) values('Wolverine','Strength');

insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('AlleyA','AlleyA Description','888 E 63th Street,Atlanta,Georgia','33.748997','-87.387985');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('AllayB','AlleyB Description','856 E 16th Street, Minneapolis, Minnesota','44.977753','-93.265015');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('CoffeeShopA','A coffee shop','6636 E 18th Street,Fresno,California','36.761651','-119.785858');
insert into Location(LocationName, Description, Address, Latitude, Longitude) values ('BankX','A bank','113 E 44th Street,Irvine,California','33.685696','-117.825981');

insert into Organization(OrgName, Description, Address, Phone) values ('Justice League','A base for superheros in Atlantis','123 E 13th Street,Atlanta,Georgia','1231111');
insert into Organization(OrgName, Description, Address, Phone) values ('Super Friends','A base for superheros in Metropolis','145 E 16th Street, Minneapolis, Minnesota', '9996365');
insert into Organization(OrgName, Description, Address, Phone) values ('X-Nation','A base for superheros in Alchemax','583 E 18th Street,Fresno,California', '1125456');

insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (1,1);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (2,1);
insert into SuperHero_Organization(SuperHeroId, OrganizationId) values (3,2);



insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/02/06','12:15:00',1,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/03/25','09:25:00',2,1);
insert into Sighting(SightDate, SightTime, SuperHeroId, LocationId) values ('2018/06/13','06:45:00',3,3);

/******************************************************************************************************************************************************/

/*superheros and their sighting locations */
use superherosightings_Test;

select superhero.HeroName, location.LocationName, sighting.SightDate,location.Description, location.Address, location.Latitude, location.Longitude 
from superHero
join sighting on  sighting.SuperHeroId= superhero.SuperHeroId
join location on sighting.locationId=location.locationId
group by superhero.SuperHeroId;

/*superheros and their organizations*/
select superhero.HeroName, superhero.SuperPower, organization.OrgName, organization.Description, organization.Address, organization.phone
from superhero
inner join superhero_organization on superhero_organization.SuperHeroId = superhero.SuperHeroId
inner join organization on superhero_organization.OrganizationId= organization.OrganizationId;

/*all members of a particular organization */
select superhero.HeroName, organization.OrgName, organization.Description, organization.Address, organization.phone
from organization
inner join superhero_organization on superhero_organization.OrganizationId= organization.OrganizationId
inner join superhero on superhero_organization.SuperHeroId = superhero.SuperHeroId
where organization.OrganizationId=1;