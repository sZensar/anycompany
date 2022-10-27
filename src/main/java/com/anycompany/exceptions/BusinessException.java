package com.anycompany.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	private String details;

}
