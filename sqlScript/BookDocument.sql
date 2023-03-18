drop table DVD;
drop table Document;
drop table Abonee;
drop sequence docSeq;
drop sequence aboneeSeq;
drop sequence DVDSeq;

CREATE SEQUENCE docSeq;
CREATE SEQUENCE aboneeSeq;

CREATE TABLE Abonee(
   numAbonee INT,
   anneeNaissance INT NOT NULL,
   nom VARCHAR(60) NOT NULL,
   PRIMARY KEY(numAbonee)
);

CREATE TABLE DVD(
   numDoc INT PRIMARY KEY,
   estEmprunte VARCHAR(1) NOT NULL,
   estRetourne VARCHAR(1) NOT NULL,
   titre VARCHAR(130) NOT NULL,
   numAbonee INT REFERENCES Abonee(numAbonee),
   estAdulte VARCHAR(1) NOT NULL
);

ALTER TABLE DVD
add constraint ck_Y_N_estEmprunte CHECK(UPPER(estEmprunte)='Y' or UPPER(estEmprunte)='N');
ALTER TABLE DVD
add constraint ck_Y_N_estRetourne CHECK(UPPER(estRetourne)='Y' or UPPER(estRetourne)='N');
ALTER TABLE DVD
add constraint ck_Y_N_estAdulte CHECK(UPPER(estAdulte)='Y' or UPPER(estAdulte)='N');
ALTER TABLE DVD
ADD CONSTRAINT CK_ABONEE_EMP_RE CHECK ((numAbonee IS NULL AND UPPER(estRetourne) = 'Y' AND UPPER(estEmprunte)='N') OR (numAbonee IS NOT NULL AND UPPER(estRetourne) = 'N' AND UPPER(estEmprunte)='Y'));

insert into Abonee values (aboneeSeq.nextval, '2002','Anxian');
insert into Abonee values (aboneeSeq.nextval, '1950','Ilie');
insert into Abonee values (aboneeSeq.nextval, '1990','Martin');
insert into Abonee values (aboneeSeq.nextval, '2000','Brette');
insert into Abonee values (aboneeSeq.nextval, '2010','Ziane');
insert into Abonee values (aboneeSeq.nextval,  '1967','Darche');

insert into DVD values (docSeq.nextval, 'Y', 'N', 'LXT', 1,'Y');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Wechat', null, 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Instagram', null , 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Face De Book', null , 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Discorde ', null , 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'XingXian', null , 'Y');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'xianHe', null , 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Yes Smile ', null , 'Y');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Mou pi pi', null , 'N');
insert into DVD values (docSeq.nextval, 'N', 'Y', 'Gu Gu', null , 'Y');

-- SELECT TNAME FROM tab WHERE tname NOT LIKE '%$%' AND tname NOT LIKE '%log%';