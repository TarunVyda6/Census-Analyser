package com.censusanalyser;

public class CensusAnalyserException extends Exception
{
	enum ExceptionType 
	{
		WRONG_CSV_FILE, WRONG_FILE_TYPE, WRONG_DELIMITER, WRONG_HEADER
	}

	private ExceptionType exceptionType;

	public ExceptionType getExceptionType()
	{
		return exceptionType;
	}

	public CensusAnalyserException(ExceptionType exception) 
	{
		this.exceptionType = exception;
	}

	public CensusAnalyserException(String message, ExceptionType exception) 
	{
		this.exceptionType = exception;
	}

}
