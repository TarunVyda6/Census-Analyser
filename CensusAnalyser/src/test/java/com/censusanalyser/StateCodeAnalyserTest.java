package com.censusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;

public class StateCodeAnalyserTest
{
	private static final String STATE_CODE_CSV_FILE_PATH = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCode - IndiaStateCode.csv";
	private static final String WRONG_CSV_FILE_PATH = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\State.csv";
	private static final String WRONG_FILE_TYPE = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCode - IndiaStateCode.txt";
	private static final String WRONG_CSV_HEADER = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCode - IndiaStateCode - HeaderWrong.csv";
	private static final String WRONG_CSV_DELIMITER = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCode - IndiaStateCode - DelimiterWrong.csv";
	StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();

	/**
	 * TC2.1
	 */
	@Test
	public void givenStateCodeCSVFile_WhenAnalyse_ShouldReturnNumberOfRecords() throws CSVException
	{
		assertEquals(37, stateCensusAnalyser.loadStateCodeData(STATE_CODE_CSV_FILE_PATH));
	}

	/**
	 * TC2.2
	 */
	@Test
	public void givenStateCodeCSVFile_WhenAnalyse_ShouldThrowWrongCSVFileException()
	{
		try
		{
			stateCensusAnalyser.loadStateCodeData(WRONG_CSV_FILE_PATH);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.WRONG_CSV_FILE, e.getExceptionType());
		}
	}

	/**
	 * TC2.3
	 */
	@Test
	public void givenStateCodeCSVFileTypeIncorrect_WhenAnalyse_ShouldThrowWrongFileTypeException()
	{
		try
		{
			stateCensusAnalyser.loadStateCodeData(WRONG_FILE_TYPE);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.WRONG_FILE_TYPE, e.getExceptionType());
		}
	}

	/**
	 * TC2.4
	 */
	@Test
	public void givenStateCodeCSVFileWrongDelimiter_WhenAnalyse_ShouldThrowWrongDelimterFileException()
	{
		try
		{
			stateCensusAnalyser.loadStateCodeData(WRONG_CSV_DELIMITER);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.INVALIDFILEDATA, e.getExceptionType());
		}
	}

	/**
	 * TC2.5
	 */
	@Test
	public void givenStateCodeCSVFileWrongHeader_WhenAnalyse_ShouldThrowWrongHeaderFileException()
	{
		try
		{
			stateCensusAnalyser.loadStateCodeData(WRONG_CSV_HEADER);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.INVALIDFILEDATA, e.getExceptionType());
		}
	}

	@Test
	public void givenStateCodeCSVFile_WhenAnalyse_ShouldReturnSortedStateCode() throws CSVException
	{
		String sortedCensusDataByStateCode = stateCensusAnalyser.sortStateCodeDataByStateCode(STATE_CODE_CSV_FILE_PATH);
		CSVStateCode[] csvStateCode = new Gson().fromJson(sortedCensusDataByStateCode, CSVStateCode[].class);
		assertEquals("AD", csvStateCode[0].stateCode);
		assertEquals("WB", csvStateCode[csvStateCode.length - 1].stateCode);
	}

}
