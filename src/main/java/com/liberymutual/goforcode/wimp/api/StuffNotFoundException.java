package com.liberymutual.goforcode.wimp.api;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such")  // 404
public class StuffNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

}
