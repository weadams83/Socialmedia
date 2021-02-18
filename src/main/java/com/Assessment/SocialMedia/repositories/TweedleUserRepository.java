package com.Assessment.SocialMedia.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Assessment.SocialMedia.entities.TweedleUser;

@Repository
public interface TweedleUserRepository extends JpaRepository<TweedleUser,Long>{

	Optional<List<TweedleUser>> queryByDeletedFalse();
	
	Optional<TweedleUser> findByCredentialsUserNameIgnoreCase(String username);

}
