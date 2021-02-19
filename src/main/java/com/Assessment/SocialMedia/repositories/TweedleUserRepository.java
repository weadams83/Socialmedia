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
public interface TweedleUserRepository extends JpaRepository<TweedleUser,Long>{

	List<TweedleUser> queryByDeletedFalse();
	
	Optional<TweedleUser> findByCredentialsUserNameIgnoreCase(String username);
	
	@Query(value = "Select * From tweedle_user t Where t.id IN(Select f.i_follow from i_follow_followed_by f "
			+ "Where f.followed_by = :author_id) And t.deleted=false", nativeQuery = true)
	List<TweedleUser> getMyFollowers(@Param("author_id") Long author_id);

	@Query(value = "Select * From tweedle_user t Where t.id IN(Select f.followed_by from i_follow_followed_by f "
			+ "Where f.i_follow = :author_id) And t.deleted=false", nativeQuery = true)
	List<TweedleUser> getWhoIFollow(@Param("author_id") Long author_id);	
}
