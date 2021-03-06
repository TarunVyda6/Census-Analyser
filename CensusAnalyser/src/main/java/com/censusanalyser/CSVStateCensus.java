package com.censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCensus
{
	@CsvBindByName(column = "State", required = true)
	public String state;

	@CsvBindByName(column = "Population", required = true)
	public long population;

	@CsvBindByName(column = "AreaInSqKm", required = true)
	public long areaInSqKm;

	@CsvBindByName(column = "DensityPerSqKm", required = false)
	public int densityPerSqKm;

	@Override
	public String toString()
	{
		return "IndiaCensusCSV{" + "State='" + state + '\'' + ", Population='" + population + '\'' + ", AreaInSqKm='"
				+ areaInSqKm + '\'' + ", DensityPerSqKm='" + densityPerSqKm + '\'' + '}';
	}
}
