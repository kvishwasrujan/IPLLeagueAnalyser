package com.capgemini.iplleagueanalyser;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.iplleagueanalyser.model.Batting;
import com.google.gson.Gson;


/**
 * @author vishw
 *
 */
public class IPLAnalyserTest  {
	private static final String BATTING_DATA_PATH = "C:\\Users\\vishw\\eclipse-workspace\\iplleagueanalyser\\src\\main\\java\\lib\\WP DP Data_01 IPL2019FactsheetMostRuns.csv";
	private static final String BOWLING_DATA_PATH = "C:\\Users\\vishw\\eclipse-workspace\\iplleagueanalyser\\src\\main\\java\\lib\\WP DP Data_02 IPL2019FactsheetMostWkts.csv"; 
	private IPLAnalyser iplAnalyser;
	
	@Before
	public void init() {
		iplAnalyser = new IPLAnalyser();
	}
	
	@Test
    public void givenBattingDataCSVFile_ShouldLoadBattingData() throws IPLAnalyserException{
        int totalRecords = iplAnalyser.loadBattingData(BATTING_DATA_PATH);
        assertEquals(100, totalRecords);
    }
	
	@Test
	public void givenBowlingDataCSVFile_ShouldLoadBowlingData() throws IPLAnalyserException {
		int totalRecords = iplAnalyser.loadBowlingData(BOWLING_DATA_PATH);
		assertEquals(99, totalRecords);
	}
	
	@Test
	public void givenBattingData_WhenSortedByAvg_ShouldReturnHighestAvgFirst() throws IPLAnalyserException {
		String sortedBattingData = "";
		iplAnalyser.loadBattingData(BATTING_DATA_PATH);
		sortedBattingData = iplAnalyser.battingAvgWiseSortedData();
		Double[] battingData = new Gson().fromJson(sortedBattingData, Double[].class);
		assertEquals(83.2, battingData[0].doubleValue(),0.0);
	}
}
