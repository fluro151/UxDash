select * from SESSIONS
select * from BOUNCES

select BOUNCES.sourceName, SESSIONS.sourceName
from BOUNCES join SESSIONS
lfet join bounces on bounces.id = sessions.id
where bounces.sourceName = 'LATTE'

