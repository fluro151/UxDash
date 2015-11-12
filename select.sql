select * from SESSIONS
select * from BOUNCES

select BOUNCES.sourceValue, SESSIONS.sourceValue
from BOUNCES join SESSIONS
on BOUNCES.sourceTimeStamp = SESSIONS.sourceTimeStamp
where sourceTimeStamp = '2015-11-07'

