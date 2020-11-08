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

			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List censusCSVList = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);

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
			Iterator<CSVStateCode> stateCodeIterator = csvBuilder.getCSVFileIterator(bufferedReader,
					CSVStateCode.class);
			return getCount(stateCodeIterator);

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
		String[] file = csvFilePath.split("[.]");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
		{
			if (!file[1].equals("csv"))
			{
				throw new CSVException("Wrong File type", CSVException.ExceptionType.WRONG_FILE_TYPE);
			}
			List<CSVStateCensus> stateCensusList = CSVBuilderFactory.createCSVBuilder().getCSVFileList(reader,
					CSVStateCensus.class);
			Collections.sort(stateCensusList, Comparator.comparing(census -> census.state));

			return new Gson().toJson(stateCensusList);
		}
		catch (IOException e)
		{
			throw new CSVException("Incorrect csv file path", CSVException.ExceptionType.WRONG_CSV_FILE);
		}
		catch (RuntimeException e)
		{
			throw new CSVException(e.getCause().getMessage(), CSVException.ExceptionType.INVALIDFILEDATA);
		}

	}

}