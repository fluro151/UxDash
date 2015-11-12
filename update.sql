ALTER TABLE SESSIONS
ADD fileSource varchar(255);

ALTER TABLE BOUNCES
ADD fileSource varchar(255);

UPDATE SESSIONS
SET fileSource='LATTE'
WHERE id=*;

UPDATE BOUNCES
SET fileSource='LOOP11'
WHERE id=*;

delete from SESSIONS
where sourceValue = null

delete from BOUNCES
where sourceValue = null

drop table BOUNCES