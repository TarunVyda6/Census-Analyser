package com.censusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;

public class StateCensusAnalyserTest
{

	private static final String STATE_CENSUS_CSV_FILE_PATH = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData - IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "D:\\javaworkspace\\IndiaStateCensusData - IndiaStateCensusData.csv";
	private static final String WRONG_FILE_TYPE = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData - IndiaStateCensusData.txt";
	private static final String WRONG_CSV_DELIMITER = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData - IndiaStateCensusData - WrongDelimter.csv";
	private static final String WRONG_CSV_HEADER = "C:\\Users\\Tarun vyda\\git\\statecensusanalyser\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData - IndiaStateCensusData - WrongHeader.csv";

	/**
	 * TC1.1
	 */
	@Test
	public void givenStateCensusCSVFile_WhenAnalyse_ShouldReturnNoOfRecords() throws CSVException
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
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.WRONG_CSV_FILE, e.getExceptionType());
		}
	}

	/**
	 * Tc1.3
	 */
	@Test
	public void givenStateCensusCSVFileTypeIncorrect_WhenAnalyse_ShouldThrowWrongFileTypeException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_FILE_TYPE);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.WRONG_FILE_TYPE, e.getExceptionType());
		}
	}

	/**
	 * TC1.4
	 */
	@Test
	public void givenStateCensusCSVFileWrongDelimiter_WhenAnalyse_ShouldThrowWrongDelimterFileException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_CSV_DELIMITER);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.INVALIDFILEDATA, e.getExceptionType());
		}
	}

	/**
	 * Tc1.5
	 */
	@Test
	public void givenStateCensusCSVFileWrongHeader_WhenAnalyse_ShouldThrowWrongHeaderFileException()
	{
		try
		{
			StateCensusAnalyser censusAnalyzer = new StateCensusAnalyser();
			censusAnalyzer.loadStatesCSVData(WRONG_CSV_HEADER);
		}
		catch (CSVException e)
		{
			assertEquals(CSVException.ExceptionType.INVALIDFILEDATA, e.getExceptionType());
		}
	}

	@Test
	public void givenStateCensusCSVFile_WhenAnalyse_shouldReturnSortedStates() throws CSVException
	{
		StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
		String sortedCensusByState = stateCensusAnalyser.sortCensusByState(STATE_CENSUS_CSV_FILE_PATH);
		CSVStateCensus[] csvStateCensus = new Gson().fromJson(sortedCensusByState, CSVStateCensus[].class);
		assertEquals("Andhra Pradesh", csvStateCensus[0].state);
		assertEquals("West Bengal", csvStateCensus[csvStateCensus.length - 1].state);

	}

	@Test
	public void givenStateCensusCSVFile_WhenAnalyse_ShouldReturnSortedPopulationInDescending() throws CSVException
	{
		StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
		String sortedCensusByPopulation = stateCensusAnalyser
				.sortCensusByPopulationInDescending(STATE_CENSUS_CSV_FILE_PATH);
		CSVStateCensus[] sortedPopulationInDescending = new Gson().fromJson(sortedCensusByPopulation,
				CSVStateCensus[].class);
		assertEquals("Sikkim", sortedPopulationInDescending[sortedPopulationInDescending.length - 1].state);
		assertEquals("Uttar Pradesh", sortedPopulationInDescending[0].state);
	}

	

}
