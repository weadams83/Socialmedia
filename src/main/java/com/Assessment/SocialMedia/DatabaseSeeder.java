package com.Assessment.SocialMedia;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.repositories.HashTagRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.utility.UtilityFunctions;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder  implements CommandLineRunner{
	private HashTagRepository hashTagRepo;
	private TweetRepository tweetRepo;
	private TweedleUserRepository userRepo;
	
	@Override
	public void run(String... args) throws Exception {
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser arwen = new TweedleUser();
		arwen.setUserName("Arwen");
		//initialize user.profile
		//initialize user.credentials
		Tweet tweet = new Tweet();
		tweet.setContent("OMG, look at gandalf's beard! #DadBod #WielderOfTheSecretFire");
		tweet.setAuthor(arwen);
		
		List<String> findContent = UtilityFunctions.findHashTagContent(tweet.getContent());
		HashTag hashTag = new HashTag();
		hashTag.setLabel(findContent.get(0));
		hashTag.setLastUsed(new Timestamp(System.currentTimeMillis()));
		
		HashTag hashTag2 = new HashTag();
		hashTag2.setLabel(findContent.get(1));
		hashTag2.setLastUsed(new Timestamp(System.currentTimeMillis()));
		
		List<HashTag> arwensHashTags = new ArrayList<HashTag>();
		List<Tweet> arwensTweets = new ArrayList<Tweet>();
		
		arwensHashTags.add(hashTag);
		arwensHashTags.add(hashTag2);
		userRepo.saveAndFlush(arwen);
		tweet = tweetRepo.saveAndFlush(tweet);
		tweet.setHashtags(arwensHashTags);
		
		arwensTweets.add(tweet);
		hashTag.setTweets(arwensTweets);
		hashTag2.setTweets(arwensTweets);
		hashTagRepo.saveAndFlush(hashTag);
		hashTagRepo.saveAndFlush(hashTag2);
		tweetRepo.saveAndFlush(tweet);
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser eowyn = new TweedleUser();
		eowyn.setUserName("Eowyn");
		//initialize user.profile
		//initialize user.credentials
		
		Tweet tweet2 = new Tweet();
		tweet2.setContent("You should see his instagram, Galadriel is a huge fan! Too bad he's all #YouShallNotPass");
		tweet2.setAuthor(eowyn);
		tweet2.setInReplyTo(tweet);
		
		findContent = UtilityFunctions.findHashTagContent(tweet2.getContent());
		HashTag hashTag3 = new HashTag();
		hashTag3.setLabel(findContent.get(0));
		hashTag3.setLastUsed(new Timestamp(System.currentTimeMillis()));

		List<HashTag> eowynHashTags = new ArrayList<HashTag>();
		List<Tweet> eowynTweets = new ArrayList<Tweet>();
		
		eowynHashTags.add(hashTag3);
		userRepo.saveAndFlush(eowyn);
		tweetRepo.saveAndFlush(tweet2);
		tweet2.setHashtags(eowynHashTags);
		
		eowynTweets.add(tweet2);
		hashTag3.setTweets(eowynTweets);
		hashTagRepo.saveAndFlush(hashTag3);
		tweetRepo.saveAndFlush(tweet2);
		
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser gimli = new TweedleUser();
		gimli.setUserName("Gimli");
		//initialize user.profile
		//initialize user.credentials
		
		Tweet tweet3 = new Tweet();
		tweet3.setContent("Psshh that still only counts as one follower! #Legolas41");
		tweet3.setAuthor(gimli);
		tweet3.setInReplyTo(tweet2);
		
		findContent = UtilityFunctions.findHashTagContent(tweet3.getContent());
		HashTag hashTag4 = new HashTag();
		hashTag4.setLabel(findContent.get(0));
		hashTag4.setLastUsed(new Timestamp(System.currentTimeMillis()));

		List<HashTag> gimliHashTags = new ArrayList<HashTag>();
		List<Tweet> gimliTweets = new ArrayList<Tweet>();
		
		gimliHashTags.add(hashTag3);
		userRepo.saveAndFlush(gimli);
		tweetRepo.saveAndFlush(tweet3);
		tweet3.setHashtags(gimliHashTags);
		
		gimliTweets.add(tweet3);
		hashTag4.setTweets(gimliTweets);

		hashTagRepo.saveAndFlush(hashTag4);
		tweetRepo.saveAndFlush(tweet3);
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser legolas= new TweedleUser();
		legolas.setUserName("Legolas");
		//initialize user.profile
		//initialize user.credentials
		
		Tweet tweet4 = new Tweet();
		tweet4.setAuthor(legolas);
		tweet4.setRepostOf(tweet3);;
			
		userRepo.saveAndFlush(legolas);
		tweetRepo.saveAndFlush(tweet4);
	}

}
