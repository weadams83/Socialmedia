package com.Assessment.SocialMedia.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.Tweet;

public interface TweetRepository  extends JpaRepository<Tweet,Long>{

}
