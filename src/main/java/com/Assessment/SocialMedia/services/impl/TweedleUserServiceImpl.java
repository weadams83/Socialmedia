package com.Assessment.SocialMedia.services.impl;

import org.springframework.stereotype.Service;

import com.Assessment.SocialMedia.repositories.TweedleUserRepository;
import com.Assessment.SocialMedia.services.TweedleUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TweedleUserServiceImpl implements TweedleUserService{
	private TweedleUserRepository tUserRepo;
}
