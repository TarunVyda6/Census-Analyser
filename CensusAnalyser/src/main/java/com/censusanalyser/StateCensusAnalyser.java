package com.censusanalyser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser
{

	public int loadStatesCSVData(String stateCensusCsvFilePath) throws CensusAnalyserException
	{

		int noOfStates = 0;

		String[] file = stateCensusCsvFilePath.split("[.]");

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(stateCensusCsvFilePath)))
		{
			if (!file[1].equalsIgnoreCase("csv"))
			{
				System.out.println(file[1]);
				throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}

			checkDelimiterHeader(stateCensusCsvFilePath);
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

	private void checkDelimiterHeader(String stateCensusCsvFilePath) throws CensusAnalyserException
	{

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(stateCensusCsvFilePath)))
		{
			String header = reader.readLine();
			String[] columnsForGivenHeader = header.split(",");
			boolean isRightHeaders = columnsForGivenHeader[0].equals("State")
					&& columnsForGivenHeader[1].equals("Population") && columnsForGivenHeader[2].equals("AreaInSqKm")
					&& columnsForGivenHeader[3].equals("DensityPerSqKm");
			if (!isRightHeaders)
			{
				throw new CensusAnalyserException("Invalid CSV header",
						CensusAnalyserException.ExceptionType.WRONG_HEADER);
			}
			while (true)
			{
				String line = reader.readLine();
				String[] columnsForGivenDelimeter = line.split(",");
				if (columnsForGivenDelimeter.length < 4)
				{
					throw new CensusAnalyserException("Invalid delimiter",
							CensusAnalyserException.ExceptionType.WRONG_DELIMITER);
				}
			}

		}
		catch (IOException | NullPointerException e)
		{
		}
	}
}