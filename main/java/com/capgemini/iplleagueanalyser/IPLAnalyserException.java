package com.capgemini.iplleagueanalyser;

public class IPLAnalyserException extends Exception{
	public enum ExceptionType{
		INVALID_FILE_PATH,INVALID_CLASS_TYPE, INVALID_DELIMITER, INVALID_HEADER,NO_DATA;
	}
	public ExceptionType type;
	
	public IPLAnalyserException(String message,ExceptionType type){
		super(message);
		this.type = type;
	}
}
