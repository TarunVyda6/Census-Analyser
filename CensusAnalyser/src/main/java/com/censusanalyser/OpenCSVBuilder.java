package com.censusanalyser;

import java.io.Reader;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder
{
	public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException
	{
		try
		{
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withType(csvClass).withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean.iterator();
		}
		catch (IllegalStateException e)
		{
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
		}
	}
}
