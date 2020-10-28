package com.capgemini.iplleagueanalyser.service;

import java.util.Comparator;

import com.capgemini.iplleagueanalyser.model.Batting;

/**
 * @author vishw
 *
 */
public class FlexibleSort implements Comparator<Batting> {
	public enum Order {
		AVG, SR, BOUNDARIES
	}

	public Order sortingBy;

	public FlexibleSort(Order sortingBy) {
		this.sortingBy = sortingBy;
	}

	@Override
	public int compare(Batting b1, Batting b2) {
		switch (sortingBy) {
		case AVG:
			if (b1.getAvg().contains("-"))
				b1.setAvg("0");
			return (int) (Double.parseDouble(b2.getAvg()) - Double.parseDouble((b1.getAvg())));
		case SR:
			if (b1.getStrikeRate().contains("-"))
				b1.setStrikeRate("0");
			return (int) (Double.parseDouble(b2.getStrikeRate()) - Double.parseDouble((b1.getStrikeRate())));
		case BOUNDARIES:
			return (Integer.parseInt(b2.getFours()) + Integer.parseInt(b2.getSixes()))
					- (Integer.parseInt(b1.getFours()) + Integer.parseInt(b1.getSixes()));
		}
		return 0;
	}
}
