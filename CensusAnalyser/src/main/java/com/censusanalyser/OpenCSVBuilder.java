package com.censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder<E> implements ICSVBuilder
{
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVException
	{
		return getCSVBean(reader, csvClass).iterator();

	}

	@Override
	public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVException
	{

		return getCSVBean(reader, csvClass).parse();
	}

	private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVException
	{
		try
		{
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withType(csvClass).withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean;

		}
		catch (IllegalStateException e)
		{
			throw new CSVException("invalid file data", CSVException.ExceptionType.INVALIDFILEDATA);
		}
	}

}
