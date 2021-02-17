package com.Assessment.SocialMedia.controllers.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.Assessment.SocialMedia.exceptions.BadRequestException;
import com.Assessment.SocialMedia.exceptions.NotFoundException;
import com.Assessment.SocialMedia.model.ErrorDTO;

@ControllerAdvice(basePackages = {"com.Assessment.SocialMedia.controllers"})
@ResponseBody
public class TweedleUserControllerAdvice {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorDTO handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
		return new ErrorDTO(notFoundException.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ErrorDTO handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
		return new ErrorDTO(badRequestException.getMessage());
	}
}
