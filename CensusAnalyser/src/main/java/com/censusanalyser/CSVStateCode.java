package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode
{

	@CsvBindByName(column = "State Name", required = true)
	public String stateName;
	@CsvBindByName(column = "StateCode", required = false)
	public String stateCode;

	@Override
	public String toString()
	{
		return "CSVStateCode [state=" + stateName + ", stateCode=" + stateCode + "]";
	}
}
