package com.censusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateCensusAnalyserTest
{

	private static final String STATE_CENSUS_CSV_FILE_PATH = "E:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "D:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData.csv";
	private static final String WRONG_FILE_TYPE = "E:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData.xml";
	private static final String WRONG_CSV_DELIMITER = "E:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData - WrongDelimter.csv";
	private static final String WRONG_CSV_HEADER = "E:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData - WrongHeader.csv";

	/**
	 * TC1.1
	 */
	@Test
	public void givenStateCensusCSVFile_WhenAnalyse_ShouldReturnNoOfRecords() throws CensusAnalyserException
	{
		StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
		assertEquals(29, censusAnalyser.loadStatesCSVData(STATE_CENSUS_CSV_FILE_PATH));
	}

	/**
	 * TC1.2
	 */
	@Test
	public void givenStateCensusCSVFilePathIncorrect_WhenAnalyse_ShouldThrowWrongCSVFileException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_CSV_FILE_PATH);
		}
		catch (CensusAnalyserException e)
		{
			assertEquals(CensusAnalyserException.ExceptionType.WRONG_CSV_FILE, e.getExceptionType());
		}
	}

	/**
	 * Tc1.3
	 */
	@Test
	public void givenStateCensusCSVFile_WhenFileTypeIncorrect_ShouldThrowException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_FILE_TYPE);
		}
		catch (CensusAnalyserException e)
		{
			assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.getExceptionType());
		}
	}

	/**
	 * TC1.4
	 */
	@Test
	public void givenStateCensusCSVFileWrongDelimiter_WhenAnalyse_ShouldThrowWrongDelimterCSVFileException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_CSV_DELIMITER);
		}
		catch (CensusAnalyserException e)
		{
			assertEquals(CensusAnalyserException.ExceptionType.WRONG_DELIMITER, e.getExceptionType());
		}
	}

	/**
	 * Tc1.5
	 */
	@Test
	public void givenStateCensusCSVFileWrongHeader_WhenAnalyse_ShouldThrowWrongHeaderCSVFileException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_CSV_HEADER);
		}
		catch (CensusAnalyserException e)
		{
			assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER, e.getExceptionType());
		}
	}

}
