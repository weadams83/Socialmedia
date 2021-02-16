package com.Assessment.SocialMedia.utility;

import java.util.ArrayList;
import java.util.List;

public final class UtilityFunctions {
	
	public static List<String> findHashTagContent(String content){
		List<String> retList = new ArrayList<>();
		String split[] = content.split("#");
		for(int i=1; i<split.length; i++) {
			retList.add(split[i]);
		}
		return retList;
	}

}
