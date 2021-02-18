package com.Assessment.SocialMedia.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;

@Repository
public interface TweetRepository  extends JpaRepository<Tweet,Long>{

	@Query(value = " Select * From Tweet t Where t.id IN"
			+ " (SELECT w.id FROM Tweet as t Inner Join i_follow_followed_by as f On t.author_id = f.i_follow"
			+ " Inner Join Tweet as w on w.author_id = f.followed_by"
			+ " WHERE t.author_id = :author_id AND t.deleted=false And w.deleted=false) Or t.author_id=:author_id Order By t.posted Desc",
            nativeQuery = true)
	Optional<List<Tweet>> getTweetFeed(@Param("author_id") Long author_id);
	
	@Query(value = " Select * from Tweet t Where t.author_id = :author_id AND t.deleted = false Order By t.posted DESC",
            nativeQuery = true)
	Optional<List<Tweet>> getMyTweets(@Param("author_id") Long author_id);


	Optional<List<Tweet>> queryByDeletedFalse();
	
	@Query(value = " Select * from Tweet t Where  t.deleted = false Order By t.posted DESC",
            nativeQuery = true)
	Optional<List<Tweet>> getAllTweets();




}
