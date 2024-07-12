package com.davi.template.exceptions.file;

public class ReadFileException extends RuntimeException {
	public ReadFileException(String partnersFilePath) {
		super(String.format("Cannot read file: %s", partnersFilePath));
	}
}
