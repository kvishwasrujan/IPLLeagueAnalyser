package com.capgemini.iplleagueanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import com.capgemini.indianstatecensusanalyser.customexception.CSVBuilderFactory;
import com.capgemini.indianstatecensusanalyser.customexception.ICSVBuilder;
import com.capgemini.iplleagueanalyser.model.Batting;
import com.capgemini.iplleagueanalyser.model.Bowling;
import com.capgemini.iplleagueanalyser.service.FlexibleSort;
import com.opencsv.exceptions.CsvException;

/**
 * @author vishw
 *
 */
public class IPLAnalyser {
	List<Batting> battingList;
	List<Bowling> bowlingList;

	/**
	 * @param battingDataPath
	 * @return battingList.size()
	 * @throws IPLAnalyserException
	 */
	public int loadBattingData(String battingDataPath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(battingDataPath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			try {
				battingList = csvBuilder.getCSVFileList(reader, Batting.class);
			} catch (CsvException e) {
				throw new IPLAnalyserException("Invalid class", IPLAnalyserException.ExceptionType.INVALID_CLASS_TYPE);
			}
		} catch (IOException e) {
			throw new IPLAnalyserException("Invalid file location",
					IPLAnalyserException.ExceptionType.INVALID_FILE_PATH);
		}
		return battingList.size();
	}

	/**
	 * @param bowlingDataPath
	 * @return bowlingList.size()
	 * @throws IPLAnalyserException
	 */
	public int loadBowlingData(String bowlingDataPath) throws IPLAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(bowlingDataPath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			try {
				bowlingList = csvBuilder.getCSVFileList(reader, Bowling.class);
			} catch (CsvException e) {
				throw new IPLAnalyserException("Invalid class", IPLAnalyserException.ExceptionType.INVALID_CLASS_TYPE);
			}

		} catch (IOException e1) {
			throw new IPLAnalyserException("Invalid file location",
					IPLAnalyserException.ExceptionType.INVALID_FILE_PATH);
		}
		return bowlingList.size();
	}

	/**
	 * @param <T>
	 * @param order
	 * @param playerType
	 * @return sortedList
	 * @throws IPLAnalyserException
	 */
	public <T> List<T> getSortedList(FlexibleSort.Order order, String playerType) throws IPLAnalyserException {
		FlexibleSort flexibleSort = new FlexibleSort(order);
		List<T> sortedList;
		if (playerType.equals("Batsman")) {
			if (battingList == null || battingList.size() == 0)
				throw new IPLAnalyserException("No batting list data", IPLAnalyserException.ExceptionType.NO_DATA);
			sortedList = (List<T>) battingList;
		} else if (playerType.equals("Bowler")) {
			if (bowlingList == null || bowlingList.size() == 0)
				throw new IPLAnalyserException("No bowling list data", IPLAnalyserException.ExceptionType.NO_DATA);
			sortedList = (List<T>) bowlingList;
		} else
			throw new IPLAnalyserException("Wrong player type", IPLAnalyserException.ExceptionType.WRONG_PLAYER_TYPE);
		Collections.sort(sortedList, flexibleSort);
		System.out.println(sortedList);
		return sortedList;
	}

}
