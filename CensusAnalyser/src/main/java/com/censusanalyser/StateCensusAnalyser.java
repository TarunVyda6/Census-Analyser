package com.censusanalyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

public class StateCensusAnalyser
{

	private List<CSVStateCensus> censusCSVList;
	private List<CSVStateCode> stateCodeList;

	/**
	 * @param stateCensusCsvFilePath
	 * @return count of states
	 * @throws CSVException
	 */
	public int loadStatesCSVData(String stateCensusCsvFilePath) throws CSVException
	{

		String[] file = stateCensusCsvFilePath.split("[.]");

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(stateCensusCsvFilePath)))
		{
			if (!file[1].equalsIgnoreCase("csv"))
			{
				throw new CSVException(CSVException.ExceptionType.WRONG_FILE_TYPE);
			}

			ICSVBuilder<CSVStateCensus> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);

			return censusCSVList.size();
		}
		catch (IOException e)
		{
			throw new CSVException("Incorrect csv file path", CSVException.ExceptionType.WRONG_CSV_FILE);
		}
		catch (RuntimeException e)
		{
			throw new CSVException("invalid file data", CSVException.ExceptionType.INVALIDFILEDATA);
		}
	}

	/**
	 * @param stateCodeCsvFilePath
	 * @return count of states
	 * @throws CSVException
	 */
	public int loadStateCodeData(String stateCodeCsvFilePath) throws CSVException
	{
		String[] file = stateCodeCsvFilePath.split("[.]");

		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(stateCodeCsvFilePath)))
		{
			if (!file[1].equals("csv"))
			{
				throw new CSVException("Wrong File Type", CSVException.ExceptionType.WRONG_FILE_TYPE);
			}

			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			stateCodeList = csvBuilder.getCSVFileList(bufferedReader, CSVStateCode.class);
			return stateCodeList.size();

		}
		catch (IOException e)
		{
			throw new CSVException("Incorrect csv file path", CSVException.ExceptionType.WRONG_CSV_FILE);
		}
		catch (IllegalStateException e)
		{
			throw new CSVException(e.getMessage(), CSVException.ExceptionType.WRONG_FILE_TYPE);
		}

		catch (RuntimeException e)
		{
			throw new CSVException(e.getMessage(), CSVException.ExceptionType.INVALIDFILEDATA);
		}
	}

	private <E> int getCount(Iterator<E> iterator)
	{
		int numOfEntries = 0;
		Iterable<E> csvIterable = () -> iterator;
		numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}

	/**
	 * @param csvFilePath
	 * @return string of census sorted by state
	 * @throws CSVException
	 */
	public String sortCensusByState(String csvFilePath) throws CSVException
	{
		loadStatesCSVData(csvFilePath);
		if (censusCSVList == null || censusCSVList.size() == 0)
			throw new CSVException("No Census data found", CSVException.ExceptionType.NO_CENSUS_DATA);
		Collections.sort(censusCSVList, Comparator.comparing(census -> census.state));
		return new Gson().toJson(censusCSVList);

	}

	/**
	 * @param stateCodeCsvFilePath
	 * @return string of census sorted by state code
	 * @throws CSVException
	 */
	public String sortStateCodeDataByStateCode(String stateCodeCsvFilePath) throws CSVException
	{
		loadStateCodeData(stateCodeCsvFilePath);
		if (stateCodeList == null || stateCodeList.size() == 0)
			throw new CSVException("No Census data found", CSVException.ExceptionType.NO_CENSUS_DATA);
		Collections.sort(stateCodeList, Comparator.comparing(census -> census.stateCode));
		return new Gson().toJson(stateCodeList);
	}

	/**
	 * @param stateCensusCsvFilePath
	 * @return string of census sorted by population
	 * @throws CSVException
	 */
	public String sortCensusByPopulationInDescending(String stateCensusCsvFilePath) throws CSVException
	{
		loadStatesCSVData(stateCensusCsvFilePath);
		Collections.sort(censusCSVList, Comparator.comparing(census -> census.population));
		Collections.reverse(censusCSVList);
		return new Gson().toJson(censusCSVList);

	}

	/**
	 * @param stateCensusCsvFilePath
	 * @return string of census sorted by population Density
	 * @throws CSVException
	 */
	public String sortCensusByPopulationDensity(String stateCensusCsvFilePath) throws CSVException
	{
		loadStatesCSVData(stateCensusCsvFilePath);
		Collections.sort(censusCSVList, Comparator.comparing(census -> census.densityPerSqKm));
		Collections.reverse(censusCSVList);
		return new Gson().toJson(censusCSVList);
	}

	/**
	 * @param stateCensusCsvFilePath
	 * @return string of census sorted by area
	 * @throws CSVException
	 */
	public String sortCensusDataByArea(String stateCensusCsvFilePath) throws CSVException
	{
		loadStatesCSVData(stateCensusCsvFilePath);
		if (censusCSVList == null || censusCSVList.size() == 0)
			throw new CSVException("No Census data found", CSVException.ExceptionType.NO_CENSUS_DATA);
		Collections.sort(censusCSVList, Comparator.comparing(census -> census.areaInSqKm));
		Collections.reverse(censusCSVList);
		return new Gson().toJson(censusCSVList);
	}

}