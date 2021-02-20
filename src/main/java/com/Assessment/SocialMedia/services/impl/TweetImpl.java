package com.Assessment.SocialMedia.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.DTOs.HashTagResponseDTO;
import com.Assessment.SocialMedia.DTOs.PostTweetDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserRequestDTO;
import com.Assessment.SocialMedia.DTOs.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.DTOs.TweetResponseDTO;
import com.Assessment.SocialMedia.entities.HashTag;
import com.Assessment.SocialMedia.entities.TweedleUser;
import com.Assessment.SocialMedia.entities.Tweet;
import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.mappers.HashTagMapper;
import com.Assessment.SocialMedia.mappers.TweedleUserMapper;
import com.Assessment.SocialMedia.mappers.TweetMapper;
import com.Assessment.SocialMedia.repositories.HashTagRepository;
import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.repositories.TweetRepository;
import com.Assessment.SocialMedia.services.TweetService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweetImpl implements TweetService {
	private TweedleUserRepository tUserRepo;
	private TweedleUserMapper tUserMap;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private HashTagMapper hTagMapper;
	private HashTagRepository hTagRepo;

	// utility function find hashtags
	public List<String> findHashTagContent(String content) {
		List<String> retList = new ArrayList<>();
		Pattern pattern = Pattern.compile("#\\S+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			retList.add(matcher.group().substring(1));
		}
		return retList;
	}

	// utility function find mentions
	public List<String> findMentions(String content) {
		List<String> retList = new ArrayList<>();
		Pattern pattern = Pattern.compile("@\\S+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			retList.add(matcher.group().substring(1));
		}
		return retList;
	}

	public void findSetHashTags(Tweet tweet) {
		List<String> hTags = findHashTagContent(tweet.getContent());
		Optional<HashTag> findTag = Optional.of(new HashTag());
		List<Tweet> tempTweetList = new ArrayList<Tweet>();
		List<HashTag> tweetHashT = new ArrayList<>();
		tweetRepository.saveAndFlush(tweet);
		for (String eachTag : hTags) {
			findTag = hTagRepo.findByLabelIgnoreCase(eachTag);
			if (findTag.isEmpty()) {
				HashTag newTag = new HashTag();
				tempTweetList = new ArrayList<>();
				tempTweetList.add(tweet);
				newTag.setLabel(eachTag);
				newTag.setLastUsed(new Timestamp(System.currentTimeMillis()));
				tweetHashT.add(newTag);
				newTag.setTweets(tempTweetList);
				hTagRepo.save(newTag);
			} else {
				tempTweetList = findTag.get().getTweets();
				tempTweetList.add(tweet);
				findTag.get().setLastUsed(new Timestamp(System.currentTimeMillis()));
				findTag.get().setTweets(tempTweetList);
				tweetHashT.add(findTag.get());
				hTagRepo.save(findTag.get());
			}
		}
		tweet.setHashtags(tweetHashT);
		tweetRepository.saveAndFlush(tweet);
	}

	public void findSetMentions(Tweet tweet) {
		List<String> mentions = findMentions(tweet.getContent());
		List<TweedleUser> mentioned = new ArrayList<>();
		Optional<TweedleUser> findUser = Optional.of(new TweedleUser());
		for (String eachMention : mentions) {
			findUser = tUserRepo.findByCredentialsUserNameIgnoreCase(eachMention);
			if (findUser.isPresent()) {
				mentioned.add(findUser.get());
			}
		}
		tweet.setMentions(mentioned);
		tweetRepository.saveAndFlush(tweet);
	}


	@Override
	public List<TweetResponseDTO> getAllTweets() {

		List<Tweet> result = tweetRepository.getAllTweets();
		return tweetMapper.entitiesToResponseDTOs(result);
	}

	@Override
	public TweetResponseDTO getTweetByID(Long id) {
		Optional<Tweet> findId = tweetRepository.findById(id);

		if (findId.isEmpty()) {
			throw new NotFoundException("No such tweet exists.");
		}
		if (findId.get().isDeleted()) {
			throw new NotFoundException("This tweet has been deleted.");
		}
		return tweetMapper.entityToResponseDTO(findId.get());
	}
	

	@Override
	public TweetResponseDTO deleteTweet(Long id, TweedleUserRequestDTO tweetUserRequestDTO) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet With id: %d does not exsist", id));
		}
		if (!findTweet.get().getAuthor().getCredentials().getUserName()
				.equals(tweetUserRequestDTO.getCredentials().getUserName())) {
			throw new BadRequestException("Username Incorrect");
		}
		if (!findTweet.get().getAuthor().getCredentials().getPassword()
				.equals(tweetUserRequestDTO.getCredentials().getPassword())) {
			throw new BadRequestException("Password Incorrect");
		}
		findTweet.get().setDeleted(true);
		tweetRepository.saveAndFlush(findTweet.get());
		return tweetMapper.entityToResponseDTO(findTweet.get());
	}

	public void postLike(Long id, TweedleUserRequestDTO tweedleUserRequestDTO) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet with id: %d does not exist.", id));
		}
		if (findTweet.get().isDeleted()) {
			throw new NotFoundException(String.format("Tweet with id: %d has been deleted.", id));
		}
		Optional<TweedleUser> findUser = tUserRepo
				.findByCredentialsUserNameIgnoreCase(tweedleUserRequestDTO.getCredentials().getUserName());

		if (findUser.isEmpty()) {
			throw new NotFoundException("User could not be found.");
		}
		if (!findUser.get().getCredentials().getPassword()
				.equals(tweedleUserRequestDTO.getCredentials().getPassword())) {
			throw new BadRequestException("Password is incorrect.");
		}
		if (findUser.get().isDeleted()) {
			throw new NotFoundException("User has been deleted");
		}
		List<Tweet> likedTweets = findUser.get().getLikedTweets();
		if (!likedTweets.contains(findTweet.get())) {
			likedTweets.add(findTweet.get());
			findUser.get().setLikedTweets(likedTweets);
			tUserRepo.saveAndFlush(findUser.get());
		}
	}
	
	@Override
	public List<HashTagResponseDTO> getUsersTweetTags(Long id) {
		Optional<Tweet>findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet With id: %d does not exsist", id));
		}
		if (findTweet.get().isDeleted()) {
			throw new BadRequestException("Tweet was deleted");
		}
		List<HashTag> hashTags=findTweet.get().getHashtags();
		return hTagMapper.entitiesToResponseDTOs(hashTags);
		
		
	}

	@Override
	public TweetResponseDTO postTweet(PostTweetDTO postTweetDTO) {
		Optional<TweedleUser> findUser = tUserRepo
				.findByCredentialsUserNameIgnoreCase(postTweetDTO.getCredentials().getUserName());

		if (findUser.isEmpty()) {
			throw new NotFoundException("User could not be found.");
		}
		if (findUser.get().isDeleted()) {
			throw new NotFoundException("User has been deleted");
		}
		
		Tweet newTweet = new Tweet();
		newTweet.setContent(postTweetDTO.getContent());
		newTweet.setAuthor(findUser.get());
		findSetHashTags(newTweet);
		findSetMentions(newTweet);
		tweetRepository.saveAndFlush(newTweet);
		return tweetMapper.entityToResponseDTO(newTweet);
	}

//	Creates a new simple tweet, with the author set to the user identified by
//	the credentials in the request body. If the given credentials do not match an 
//	active user in the database, an error should be sent in lieu of a response.
//
//	The response should contain the newly-created tweet.
//
//	Because this always creates a simple tweet, it must have a content property and 
//	may not have inReplyTo or repostOf properties.
//
//	IMPORTANT: when a tweet with content is created, the server must process the 
//	tweet's content for @{username} mentions and #{hashtag} tags. There is no way 
//	to create hashtags or create mentions from the API, so this must be handled automatically!
//	
//	

	@Override
	public List<TweedleUserResponseDTO> getUsersLikedTweet(Long id) {

		Optional<Tweet> findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty() || findTweet.get().isDeleted()) {
			throw new NotFoundException("No such tweet exists or has been deleted.");
		}
		List<TweedleUser> userLiked = tUserRepo.getAllLikes(id);

		return tUserMap.entitiesToResponseDTOs(userLiked);
	}

	@Override
	public TweetResponseDTO postReply(Long id, PostTweetDTO postTweetDTO) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);
		if (findTweet.isEmpty()) {
			throw new NotFoundException(String.format("Tweet with id: %d doesn't exist.", id));
		}
		if (findTweet.get().isDeleted()) {
			throw new NotFoundException(String.format("Tweet with id: %d has been deleted.", id));
		}
		Optional<TweedleUser> findUser = tUserRepo
				.findByCredentialsUserNameIgnoreCase(postTweetDTO.getCredentials().getUserName());
		if (findUser.isEmpty()) {
			throw new NotFoundException(
					String.format("User with name: %s doesn't exist.", postTweetDTO.getCredentials().getUserName()));
		}
		if (!findUser.get().getCredentials().getPassword().equals(postTweetDTO.getCredentials().getPassword())) {
			throw new BadRequestException("Wrond password.");
		}
		if (postTweetDTO.getContent() == null || postTweetDTO.getContent().length() == 0) {
			throw new BadRequestException("Your tweet must have content.");
		}
		Tweet newTweet = new Tweet();
		newTweet.setContent(postTweetDTO.getContent());
		newTweet.setInReplyTo(findTweet.get());
		newTweet.setAuthor(findUser.get());
		findSetHashTags(newTweet);
		findSetMentions(newTweet);
		tweetRepository.saveAndFlush(newTweet);
		return tweetMapper.entityToResponseDTO(newTweet);
	}
}
