package com.Assessment.SocialMedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Assessment.SocialMedia.model.TweedleUserResponseDTO;
import com.Assessment.SocialMedia.services.TweedleUserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class TweedleUserController {
	private TweedleUserService userServ;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<TweedleUserResponseDTO> getAllUsers(){
		return userServ.getAllUsers();
	}
}
