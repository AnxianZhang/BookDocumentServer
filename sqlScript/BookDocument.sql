drop table DVD;
drop table Document;
drop table Abonee;
drop sequence docSeq;
drop sequence aboneeSeq;
drop sequence DVDSeq;

CREATE SEQUENCE docSeq;
CREATE SEQUENCE aboneeSeq;
CREATE SEQUENCE DVDSeq;

CREATE TABLE Abonee(
   numAbonee INT,
   dateNaissance DATE NOT NULL,
   nom VARCHAR(60) NOT NULL,
   PRIMARY KEY(numAbonee)
);

CREATE TABLE Document(
   numDoc INT,
   estEmprunte VARCHAR(1) NOT NULL,
   estRetourne VARCHAR(1) NOT NULL,
   titre VARCHAR(130) NOT NULL,
   numAbonee INT,
   PRIMARY KEY(numDoc),
   FOREIGN KEY(numAbonee) REFERENCES Abonee(numAbonee)
);

CREATE TABLE DVD(
   numDVD INT,
   estAdulte VARCHAR(1) NOT NULL,
   numDoc INT NOT NULL,
   PRIMARY KEY(numDVD),
   FOREIGN KEY(numDoc) REFERENCES Document(numDoc)
);

ALTER TABLE Document
add constraint ck_Y_N_estEmprunte CHECK(UPPER(estEmprunte)='Y' or UPPER(estEmprunte)='N');
ALTER TABLE Document
add constraint ck_Y_N_estRetourne CHECK(UPPER(estRetourne)='Y' or UPPER(estRetourne)='N');
ALTER TABLE DVD
add constraint ck_Y_N_estAdulte CHECK(UPPER(estAdulte)='Y' or UPPER(estAdulte)='N');

insert into Abonee values (aboneeSeq.nextval, DATE '1950-02-30','Ilie' );
insert into Abonee values (aboneeSeq.nextval, DATE '1990-03-26','Martin' );
insert into Abonee values (aboneeSeq.nextval, DATE '2000-04-19','Brette' );
insert into Abonee values (aboneeSeq.nextval, DATE '2010-05-31','Ziane' );
insert into Abonee values (aboneeSeq.nextval, DATE '1967-06-07','Darche' );

insert into Document values (docSeq.nextval, 'N', 'Y', 'LXT', 1 );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Wechat', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Instagram', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Face De Book', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Discorde ', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'XingXian', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'xianHe', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Yes Smile ', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Mou pi pi', null );
insert into Document values (docSeq.nextval, 'N', 'Y', 'Gu Gu', null );

insert into DVD values (DVDSeq.nextval, 'Y', 1 );
insert into DVD values (DVDSeq.nextval, 'N', 2 );
insert into DVD values (DVDSeq.nextval, 'N', 3 );
insert into DVD values (DVDSeq.nextval, 'N', 4 );
insert into DVD values (DVDSeq.nextval, 'N', 5 );
insert into DVD values (DVDSeq.nextval, 'Y', 6 );
insert into DVD values (DVDSeq.nextval, 'N', 7 );
insert into DVD values (DVDSeq.nextval, 'Y', 8 );
insert into DVD values (DVDSeq.nextval, 'N', 9 );
insert into DVD values (DVDSeq.nextval, 'Y', 10 );