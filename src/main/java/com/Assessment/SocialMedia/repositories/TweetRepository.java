package com.Assessment.SocialMedia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

	@Query(value = " Select * From Tweet t Where t.id IN"
			+ " (SELECT w.id FROM Tweet as t Inner Join i_follow_followed_by as f On t.author_id = f.i_follow"
			+ " Inner Join Tweet as w on w.author_id = f.followed_by"
			+ " WHERE t.author_id = :author_id AND t.deleted=false And w.deleted=false) Or t.author_id=:author_id Order By t.posted Desc", nativeQuery = true)
	List<Tweet> getTweetFeed(@Param("author_id") Long author_id);

	// This is a derived query
	// List<Tweet> findAllByIdAndDeletedFalseOrderByPostedDesc(Long author_id);
	// replaces the below query
	@Query(value = " Select * from Tweet t Where t.author_id = :author_id AND t.deleted = false Order By t.posted DESC", nativeQuery = true)
	List<Tweet> getMyTweets(@Param("author_id") Long author_id);

	List<Tweet> queryByDeletedFalse();

	@Query(value = " Select * from Tweet t Where  t.deleted = false Order By t.posted DESC", nativeQuery = true)
	List<Tweet> getAllTweets();

	@Query(value = "Select * from Tweet t Where t.id In (Select m.mentions FROM mentioned_users m Where mentioned = :author_id) "
			+ "And t.deleted = false Order by t.posted Desc", nativeQuery = true)
	List<Tweet> getMyMentions(@Param("author_id") Long author_id);

	@Query(value = "Select * from tweet t where t.id in (Select j.tweet_id from tweet_hash_join j Inner Join hash_tag h ON h.id = j.hash_tag_id Where h.label = :hTag)"
			+ "	And t.deleted = false Order by t.posted DESC", nativeQuery = true)
	List<Tweet> getTweetsTagged(@Param("hTag") String hTag);

}
