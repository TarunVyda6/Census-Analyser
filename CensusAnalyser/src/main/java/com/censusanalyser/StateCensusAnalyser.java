package com.censusanalyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser
{

	/**
	 * @param stateCensusCsvFilePath
	 * @return count of states
	 * @throws CensusAnalyserException
	 */
	public int loadStatesCSVData(String stateCensusCsvFilePath) throws CensusAnalyserException
	{

		int noOfStates = 0;

		String[] file = stateCensusCsvFilePath.split("[.]");

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(stateCensusCsvFilePath)))
		{
			if (!file[1].equalsIgnoreCase("csv"))
			{
				throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}

			checkDelimiter(stateCensusCsvFilePath);
			checkHeader(stateCensusCsvFilePath);
			CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(CSVStateCensus.class).withIgnoreLeadingWhiteSpace(true);
			CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
			Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();
			Iterable<CSVStateCensus> iterable = () -> censusCSVIterator;
			noOfStates = (int) StreamSupport.stream(iterable.spliterator(), false).count();

			return noOfStates;
		}
		catch (IOException e)
		{
			throw new CensusAnalyserException("Incorrect csv file path",
					CensusAnalyserException.ExceptionType.WRONG_CSV_FILE);
		}

		catch (IllegalStateException e)
		{
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
		}
	}

	/**
	 * @param stateCodeCsvFilePath
	 * @return count of states
	 * @throws CensusAnalyserException
	 */
	public int loadStateCodeData(String stateCodeCsvFilePath) throws CensusAnalyserException
	{
		int noOfStates = 0;
		String[] file = stateCodeCsvFilePath.split("[.]");

		try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(stateCodeCsvFilePath)))
		{
			if (!file[1].equals("csv"))
			{
				throw new CensusAnalyserException("Wrong File Type",
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}

			checkDelimiterStateCode(stateCodeCsvFilePath);
			checkHeaderStateCode(stateCodeCsvFilePath);

			CsvToBeanBuilder<CSVStateCode> csvToBeanBuilder = new CsvToBeanBuilder<>(bufferedReader);
			CsvToBean csvToBean = csvToBeanBuilder.withType(CSVStateCode.class).withIgnoreLeadingWhiteSpace(true)
					.build();

			Iterator<CSVStateCode> stateCodeIterator = csvToBean.iterator();
			Iterable<CSVStateCode> iterable = () -> stateCodeIterator;
			noOfStates = (int) StreamSupport.stream(iterable.spliterator(), false).count();
			return noOfStates;

		}
		catch (IOException e)
		{
			throw new CensusAnalyserException("Incorrect csv file path",
					CensusAnalyserException.ExceptionType.WRONG_CSV_FILE);
		}
		catch (IllegalStateException e)
		{
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
		}

	}

	/**
	 * @param checks for delimter exception for state census
	 * @throws CensusAnalyserException
	 */
	public void checkDelimiter(String csvFilePath) throws CensusAnalyserException
	{
		try
		{
			BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath));
			while (true)
			{
				String line = br.readLine();
				String[] Linecolumns = line.split(",");
				if (Linecolumns.length < 4)
				{
					throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
				}
			}
		}
		catch (NullPointerException | IOException e)
		{
			e.getMessage();
		}

	}

	/**
	 * @param checks for header exception for state census
	 * @throws CensusAnalyserException
	 */
	public void checkHeader(String csvFilePath) throws CensusAnalyserException
	{
		try
		{
			BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath));
			String FirstLine = br.readLine();
			String[] columns = FirstLine.split(",");
			boolean isCorrect = columns[0].equals("State") && columns[1].equals("Population")
					&& columns[2].equals("AreaInSqKm") && columns[3].equals("DensityPerSqKm");
			if (!isCorrect)
			{
				throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_HEADER);
			}

		}
		catch (NullPointerException | IOException e)
		{
			e.getMessage();
		}

	}

	/**
	 * @param checks for delimter exception for state code
	 * @throws CensusAnalyserException
	 */
	public void checkDelimiterStateCode(String csvFilePath) throws CensusAnalyserException
	{
		try
		{
			BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath));
			while (true)
			{
				String line = br.readLine();
				String[] Linecolumns = line.split(",");
				if (Linecolumns.length < 4)
				{
					throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
				}
				break;
			}
		}
		catch (NullPointerException | IOException e)
		{
			e.getMessage();
		}

	}

	/**
	 * @param checks for header exception for state census
	 * @throws CensusAnalyserException
	 */
	public void checkHeaderStateCode(String csvFilePath) throws CensusAnalyserException
	{
		try
		{
			BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath));
			String FirstLine = br.readLine();
			String[] columns = FirstLine.split(",");
			boolean isCorrect = columns[1].equals("State Name") && columns[3].equals("StateCode");
			if (!isCorrect)
			{
				throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_HEADER);
			}

		}
		catch (NullPointerException | IOException e)
		{
			e.getMessage();
		}

	}

}