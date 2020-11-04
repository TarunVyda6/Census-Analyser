package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus
{

	@CsvBindByName(column = "State")
	public String state;

	@CsvBindByName(column = "Population")
	public long population;

	@CsvBindByName(column = "AreaInSqKm")
	public long areaInSqKm;

	@CsvBindByName(column = "DensityPerSqKm")
	public int densityPerSqKm;

	@Override
	public String toString()
	{
		return "IndiaCensusCSV{" + "State='" + state + '\'' + ", Population='" + population + '\'' + ", AreaInSqKm='"
				+ areaInSqKm + '\'' + ", DensityPerSqKm='" + densityPerSqKm + '\'' + '}';
	}
}
