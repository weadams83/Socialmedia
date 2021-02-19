package com.Assessment.SocialMedia.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.entities.Tweet;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

	Optional<HashTag> findByLabelIgnoreCase(String label);	

}
