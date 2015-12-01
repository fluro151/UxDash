ALTER TABLE SESSIONS
ADD sourceName varchar(255);

ALTER TABLE BOUNCES
ADD sourceName varchar(255);

UPDATE SESSIONS
SET sourceName = 'LATTE';

UPDATE BOUNCES
SET sourceName = 'LOOP11';


delete from SESSIONS
where 'value' = null ;

delete from BOUNCES
where 'value' = null ;

