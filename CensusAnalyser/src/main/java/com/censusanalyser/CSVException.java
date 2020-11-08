package com.censusanalyser;

public class CSVException extends Exception
{
	public enum ExceptionType
	{
		WRONG_CSV_FILE, WRONG_FILE_TYPE, INVALIDFILEDATA, NO_CENSUS_DATA;
	}

	public ExceptionType exceptionType;

	public ExceptionType getExceptionType()
	{
		return exceptionType;
	}

	public CSVException(ExceptionType exception)
	{
		this.exceptionType = exception;
	}

	public CSVException(String message, ExceptionType exception)
	{
		this.exceptionType = exception;
	}

}
