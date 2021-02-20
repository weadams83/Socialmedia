package com.Assessment.SocialMedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.HashTag;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

	Optional<HashTag> findByLabelIgnoreCase(String label);

}
