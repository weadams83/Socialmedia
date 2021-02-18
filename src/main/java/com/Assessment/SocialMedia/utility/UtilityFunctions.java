package com.Assessment.SocialMedia.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilityFunctions {
	
	public static List<String> findHashTagContent(String content){
		List<String> retList = new ArrayList<>();
		Pattern pattern = Pattern.compile("#\\S+");
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()) {
			retList.add(matcher.group());
		}
		return retList;
	}
	
	public static List<String> findMentions(String content){
		List<String> retList = new ArrayList<>();
		Pattern pattern = Pattern.compile("@\\S+");
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()) {
			retList.add(matcher.group());
		}
		return retList;
	}


}
