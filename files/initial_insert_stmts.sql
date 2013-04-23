create index cast_info_person_index on cast_info(`person_id`) using hash;

create index movie_with_ratings_index on movies_with_ratings(`movie_id`) using hash;

insert into persons_in_movie(person_id,count_of_movies) (select person_id,count(*) from cast_info ci where ci.movie_id in (select movie_id from movies_with_ratings mwr where mwr.`isTraining` = 1) group by ci.person_id);


insert into company_movie(company_id,movie_id)  (select mc.company_id,mc.movie_id from movie_companies mc, `movies_with_ratings` mwr where mwr.`isTraining` = 1 and mc.`movie_id` = mwr.`movie_id`);


insert into company_info_analytics(companyid,count_of_movies)  (select mc.company_id as c,count(*) as count_of_movies from movie_companies mc, `movies_with_ratings` mwr where mwr.`isTraining` = 1 and mc.`movie_id` = mwr.`movie_id` group by mc.company_id);


create index `persons_in_movie` on persons_in_movie(`person_id`) using hash;

