package com.capgemini.iplleagueanalyser.service;

import java.util.Comparator;
import java.util.List;

import com.capgemini.iplleagueanalyser.model.Batting;
import com.capgemini.iplleagueanalyser.model.Bowling;

/**
 * @author vishw
 *
 */
public class FlexibleSort<T> implements Comparator<T> {
	public enum Order {
		BAT_AVG, BAT_SR, BOUNDARIES, SR_AND_BOUNDARIES, AVG_AND_SR, RUNS_AND_AVG, BOWL_AVG, BOWL_SR, ECONOMY,
		BOWL_SR_AND_ECON, BOWL_SR_AND_WICKET_HAULS, BOWL_AVG_AND_SR, BOWL_WKTS_AND_AVG, MAX100_AND_BAT_AVG,
		ZERO_100AND50_BEST_AVG
	}

	public Order sortingBy;

	public FlexibleSort(Order sortingBy) {
		this.sortingBy = sortingBy;
	}

	@Override
	public int compare(T o1, T o2) {
		Batting bat1 = null, bat2 = null;
		Bowling bowl1 = null, bowl2 = null;
		if (o1.getClass().equals(Batting.class)) {
			bat1 = (Batting) o1;
			bat2 = (Batting) o2;
		} else if (o1.getClass().equals(Bowling.class)) {
			bowl1 = (Bowling) o1;
			bowl2 = (Bowling) o2;
		}

		switch (sortingBy) {
		case BAT_AVG:
			if ((bat1).getAvg().contains("-"))
				bat1.setAvg("0");
			return (int) setValue(Double.parseDouble(bat2.getAvg()) - Double.parseDouble((bat1.getAvg())));
		case BAT_SR:
			if (bat1.getStrikeRate().contains("-"))
				bat1.setStrikeRate("0");
			return (int) setValue(
					Double.parseDouble(bat2.getStrikeRate()) - Double.parseDouble((bat1.getStrikeRate())));
		case BOUNDARIES:
			return (Integer.parseInt(bat2.getFours()) + Integer.parseInt(bat2.getSixes()))
					- (Integer.parseInt(bat1.getFours()) + Integer.parseInt(bat1.getSixes()));

		case SR_AND_BOUNDARIES:
			if (bat1.getStrikeRate().contains("-"))
				bat1.setStrikeRate("0");
			double value = Double.parseDouble(bat2.getStrikeRate()) - Double.parseDouble((bat1.getStrikeRate()));
			if (value == 0) {
				return (Integer.parseInt(bat2.getFours()) + Integer.parseInt(bat2.getSixes()))
						- (Integer.parseInt(bat1.getFours()) + Integer.parseInt(bat1.getSixes()));
			}
			value = setValue(value);
			return (int) value;

		case AVG_AND_SR:
			if (bat1.getAvg().contains("-"))
				bat1.setAvg("0");
			value = setValue(Double.parseDouble(bat2.getAvg()) - Double.parseDouble((bat1.getAvg())));
			if (value == 0) {
				return (int) setValue(
						Double.parseDouble(bat2.getStrikeRate()) - Double.parseDouble((bat1.getStrikeRate())));
			}
			return (int) value;

		case RUNS_AND_AVG:
			if (bat1.getAvg().contains("-"))
				bat1.setAvg("0");
			value = Integer.parseInt(bat2.getRuns()) - Integer.parseInt(bat1.getRuns());
			if (value == 0) {
				return (int) setValue(Double.parseDouble(bat2.getAvg()) - Double.parseDouble((bat1.getAvg())));
			}
			return (int) value;

		case BOWL_AVG:
			if (bowl1.getAvg().contains("-"))
				bowl1.setAvg("999999");
			value = setValue(Double.parseDouble(bowl1.getAvg()) - Double.parseDouble((bowl2.getAvg())));
			return (int) value;

		case BOWL_SR:
			if (bowl1.getStrikeRate().contains("-"))
				bowl1.setStrikeRate("999999");
			value = setValue(Double.parseDouble(bowl1.getStrikeRate()) - Double.parseDouble((bowl2.getStrikeRate())));
			return (int) value;

		case ECONOMY:
			value = setValue(Double.parseDouble(bowl1.getEconomy()) - Double.parseDouble((bowl2.getEconomy())));
			return (int) value;

		case BOWL_SR_AND_ECON:
			if (bowl1.getStrikeRate().contains("-"))
				bowl1.setStrikeRate("999999");
			value = setValue(Double.parseDouble(bowl1.getStrikeRate()) - Double.parseDouble((bowl2.getStrikeRate())));
			if (value == 0) {
				if (bowl1.getAvg().contains("-"))
					bowl1.setAvg("999999");
				return (int) setValue(Double.parseDouble(bowl1.getAvg()) - Double.parseDouble((bowl1.getAvg())));
			}
			return (int) value;

		case BOWL_SR_AND_WICKET_HAULS:
			if (bowl1.getStrikeRate().contains("-"))
				bowl1.setStrikeRate("999999");
			value = setValue(Double.parseDouble(bowl1.getStrikeRate()) - Double.parseDouble((bowl2.getStrikeRate())));
			if (value == 0) {
				return ((Integer.parseInt(bowl1.getFiveWickets()) + Integer.parseInt(bowl1.getFourWickets()))
						- (Integer.parseInt(bowl2.getFiveWickets()) + Integer.parseInt(bowl2.getFourWickets())));
			}
			return (int) value;

		case BOWL_AVG_AND_SR:
			if (bowl1.getAvg().contains("-"))
				bowl1.setAvg("999999");
			value = setValue(Double.parseDouble(bowl1.getAvg()) - Double.parseDouble((bowl2.getAvg())));
			if (value == 0) {
				if (bowl1.getStrikeRate().contains("-"))
					bowl1.setStrikeRate("999999");
				if (bowl2.getStrikeRate().contains("-"))
					bowl2.setStrikeRate("999999");
				return (int) setValue(
						Double.parseDouble(bowl1.getStrikeRate()) - Double.parseDouble((bowl2.getStrikeRate())));
			}
			return (int) value;

		case BOWL_WKTS_AND_AVG:
			value = Integer.parseInt(bowl2.getWickets()) - Integer.parseInt(bowl1.getWickets());
			if (value == 0) {
				if (bowl1.getAvg().contains("-"))
					bowl1.setAvg("999999");
				if (bowl2.getAvg().contains("-"))
					bowl2.setAvg("999999");
				return (int) setValue(Double.parseDouble(bowl1.getAvg()) - Double.parseDouble((bowl2.getAvg())));
			}
			return (int) value;

		case MAX100_AND_BAT_AVG:
			value = Integer.parseInt(bat2.getHundreds()) - Integer.parseInt(bat1.getHundreds());
			if (value == 0) {
				if ((bat1).getAvg().contains("-"))
					bat1.setAvg("0");
				if (bat2.getAvg().contains("-"))
					bat2.setAvg("0");
				return (int) setValue(Double.parseDouble(bat2.getAvg()) - Double.parseDouble((bat1.getAvg())));
			}
			return (int) value;

		case ZERO_100AND50_BEST_AVG:
			if ((bat1).getAvg().contains("-"))
				bat1.setAvg("0");
			return (int) setValue(Double.parseDouble(bat2.getAvg()) - Double.parseDouble((bat1.getAvg())));
		}
		return 0;
	}

	public double setValue(double value) {
		if (value > 0)
			value = 1;
		else if (value < 0)
			value = -1;
		return value;
	}
}
