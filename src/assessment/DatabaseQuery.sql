
select  T.user_id, U.username , T.training_id, T.training_date , count(*) count
from SYSTEM_USER U left join Training_details T on U.user_id = T.user_id
group by T.training_id, T.user_id, U.username , T.training_date
having  count(*) > 1
order by "training_date" desc;
