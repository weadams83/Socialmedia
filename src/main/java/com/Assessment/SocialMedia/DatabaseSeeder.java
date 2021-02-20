package com.Assessment.SocialMedia;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.entities.Profile;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.entities.UserCredentials;
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
//		Uncomment to seed database, don't forget to modify application.properties (create-drop or update)
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser arwen = new TweedleUser();
		Profile profile = new Profile();
		profile.setEmail("arwen@gmail.com");
		profile.setFirstName("Arwen");
		profile.setLastName("Undomniel");
		profile.setPhone("828-743-6897");
		UserCredentials arwenPass = new UserCredentials();
		arwenPass.setUserName("Arwen");
		arwenPass.setPassword("EveningStar");
		arwen.setCredentials(arwenPass);
		arwen.setProfile(profile);
		Tweet tweet = new Tweet();
		tweet.setContent("OMG, @Eowyn have you seen gandalf's beard! #DadBod #WielderOfTheSecretFire");
		tweet.setAuthor(arwen);
		
		List<String> findContent = UtilityFunctions.findHashTagContent(tweet.getContent());
		HashTag hashTag = new HashTag();
		hashTag.setLabel(findContent.get(0).substring(1));
		hashTag.setLastUsed(new Timestamp(System.currentTimeMillis()));
		
		HashTag hashTag2 = new HashTag();
		hashTag2.setLabel(findContent.get(1).substring(1));
		hashTag2.setLastUsed(new Timestamp(System.currentTimeMillis()));
		
		List<HashTag> arwensHashTags = new ArrayList<HashTag>();
		List<Tweet> arwensTweets = new ArrayList<Tweet>();
		
		TweedleUser eowyn = new TweedleUser();
		Profile eProfile = new Profile();
		eProfile.setEmail("eowyn@yahoo.com");
		eProfile.setFirstName("Eowyn");
		eProfile.setLastName("Eorl");
		eProfile.setPhone("919-453-6784");
		UserCredentials eowynPass = new UserCredentials();
		eowynPass.setPassword("IamNoMan");
		eowynPass.setUserName("Eowyn");
		eowyn.setCredentials(eowynPass);
		eowyn.setProfile(eProfile);
		userRepo.saveAndFlush(eowyn);
		List<String> findMentions = UtilityFunctions.findMentions(tweet.getContent());
		List<TweedleUser> findMentioned = new ArrayList<>();
		findMentioned.add(userRepo.findByCredentialsUserNameIgnoreCase(findMentions.get(0).substring(1)).get());
		
		arwensHashTags.add(hashTag);
		arwensHashTags.add(hashTag2);
		userRepo.saveAndFlush(arwen);
		tweet.setMentions(findMentioned);
		tweet = tweetRepo.saveAndFlush(tweet);
		tweet.setHashtags(arwensHashTags);

		arwensTweets.add(tweet);
		hashTag.setTweets(arwensTweets);
		hashTag2.setTweets(arwensTweets);
		hashTagRepo.saveAndFlush(hashTag);
		hashTagRepo.saveAndFlush(hashTag2);
		tweetRepo.saveAndFlush(tweet);
//----------------------------------------------------------------------------------------------------------------//
		
		
		Tweet tweet2 = new Tweet();
		tweet2.setContent("You should see his instagram, Galadriel is a huge fan! Too bad he's all #YouShallNotPass");
		tweet2.setAuthor(eowyn);
		tweet2.setInReplyTo(tweet);
		
		findContent = UtilityFunctions.findHashTagContent(tweet2.getContent());
		HashTag hashTag3 = new HashTag();
		hashTag3.setLabel(findContent.get(0).substring(1));
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
		List<TweedleUser> fans = new ArrayList<>();
		fans.add(arwen);
		eowyn.setFollowedBy(fans);
		userRepo.saveAndFlush(eowyn);
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser gimli = new TweedleUser();
		Profile gProfile = new Profile();
		gProfile.setEmail("gimli@gmail.com");
		gProfile.setFirstName("Gimli");
		gProfile.setLastName("Gloin");
		gProfile.setPhone("422-483-4876");		
		UserCredentials gimliPass = new UserCredentials();
		gimliPass.setPassword("NaturalSprinter");
		gimliPass.setUserName("Gimli");
		gimli.setCredentials(gimliPass);
		gimli.setProfile(gProfile);
		
		Tweet tweet3 = new Tweet();
		tweet3.setContent("Psshh that still only counts as one follower! #Legolas41");
		tweet3.setAuthor(gimli);
		tweet3.setInReplyTo(tweet2);
		
		findContent = UtilityFunctions.findHashTagContent(tweet3.getContent());
		HashTag hashTag4 = new HashTag();
		hashTag4.setLabel(findContent.get(0).substring(1));
		hashTag4.setLastUsed(new Timestamp(System.currentTimeMillis()));

		List<HashTag> gimliHashTags = new ArrayList<HashTag>();
		List<Tweet> gimliTweets = new ArrayList<Tweet>();
		
		gimliHashTags.add(hashTag4);
		userRepo.saveAndFlush(gimli);
		tweetRepo.saveAndFlush(tweet3);
		tweet3.setHashtags(gimliHashTags);
		
		gimliTweets.add(tweet3);
		hashTag4.setTweets(gimliTweets);

		hashTagRepo.saveAndFlush(hashTag4);
		tweetRepo.saveAndFlush(tweet3);
		
//----------------------------------------------------------------------------------------------------------------//
		TweedleUser legolas= new TweedleUser();
		Profile lProfile = new Profile();
		lProfile.setEmail("legolas@protonmail.com");
		lProfile.setFirstName("Legolas");
		lProfile.setLastName("Thranduil");
		lProfile.setPhone("643-512-7845");
		UserCredentials legolasPass = new UserCredentials();
		legolasPass.setPassword("Ninji");
		legolasPass.setUserName("Legolas");
		legolas.setCredentials(legolasPass);
		legolas.setProfile(lProfile);
		
		Tweet tweet4 = new Tweet();
		tweet4.setAuthor(legolas);
		tweet4.setRepostOf(tweet3);;
			
		userRepo.saveAndFlush(legolas);
		tweetRepo.saveAndFlush(tweet4);
		
		fans.clear();
		fans.add(gimli);
		legolas.setFollowedBy(fans);
		userRepo.saveAndFlush(legolas);
		fans.clear();
		fans.add(legolas);
		gimli.setFollowedBy(fans);
		userRepo.saveAndFlush(gimli);
//---------------------------------------------------------------------------------------------------------------------------------//
		TweedleUser galadriel = new TweedleUser();
		Profile galProfile = new Profile();
		galProfile.setEmail("galadriel@greenwood.com");
		galProfile.setFirstName("Galadriel");
		galProfile.setLastName("Lady of Light");
		galProfile.setPhone("743-589-3625");
		UserCredentials galadrielPass = new UserCredentials();
		galadrielPass.setUserName("Galadriel");
		galadrielPass.setPassword("Celeborn");
		galadriel.setCredentials(galadrielPass);
		galadriel.setProfile(galProfile);
		List<Tweet> likedTweets = new ArrayList<>();
		likedTweets.add(tweet);
		galadriel.setLikedTweets(likedTweets);

		userRepo.saveAndFlush(galadriel);

	}

}
