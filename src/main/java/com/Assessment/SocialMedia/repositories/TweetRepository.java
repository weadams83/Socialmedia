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

	@Query(value = "Select * from tweet t where t.in_reply_to_id = :tweet_id And t.deleted = false", nativeQuery = true)
	List<Tweet> repliesToTweet(@Param("tweet_id") Long tweet_id);

	@Query(value = "Select * from tweet t where t.repost_of_id = :tweet_id And t.deleted = false", nativeQuery = true)
	List<Tweet> repostsToTweet(@Param("tweet_id") Long tweet_id);
	
	@Query(value = "with recursive find_tree as ("
		+ "			   	select t.id,t.content,t.deleted,t.posted,t.author_id,t.in_reply_to_id,t.repost_of_id from tweet as t"
		+ "			   	where t.id = :tweet_id AND t.deleted=false union all select child.id, child.content,child.deleted,"
		+ "				child.posted,child.author_id,child.in_reply_to_id,child.repost_of_id from tweet as child"
		+ "			   	join find_tree as parent on parent.id = child.in_reply_to_id)"
		+ "				select * from find_tree order by find_tree.posted asc;", nativeQuery = true)
	List<Tweet> getAfterContext(@Param("tweet_id") Long tweet_id);
	
	@Query(value = "with recursive find_tree as ("
			+ "   select t.id,t.content,t.deleted,t.posted,t.author_id,t.in_reply_to_id,t.repost_of_id"
			+ "   from tweet as t"
			+ "   where t.id = :tweet_id AND t.deleted=false"
			+ "    union all"
			+ "   select parent.id, parent.content,parent.deleted,parent.posted,parent.author_id,parent.in_reply_to_id,parent.repost_of_id from tweet as parent"
			+ "   join find_tree as child on child.in_reply_to_id = parent.id) select * from find_tree order by find_tree.posted asc;", nativeQuery = true)
	List<Tweet> getBeforeContext(@Param("tweet_id") Long tweet_id);
	

}
