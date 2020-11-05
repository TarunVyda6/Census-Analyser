package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode
{

	@CsvBindByName(column = "State Name")
	private String stateName;
	@CsvBindByName(column = "State Code")
	private String stateCode;

	@Override
	public String toString()
	{
		return "CSVStateCode [state=" + stateName + ", stateCode=" + stateCode + "]";
	}
}
