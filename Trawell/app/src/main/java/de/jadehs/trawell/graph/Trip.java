package de.jadehs.trawell.graph;

import java.util.List;

/**
 * Created by luxn on 09.05.2017.
 */

public class Trip {

	String tripName;

	Route route;

	DayTime startTime;
	DayTime endTime;

	Duration duration;
	TrainType type;

	public Trip(String tripName, Route route, DayTime startTime, DayTime endTime) {
		this.tripName = tripName;
		this.route = route;
		this.startTime = startTime;
		this.endTime = endTime;

		setType();
		
		this.duration = new Duration(startTime, endTime);

	}

	private void setType() {
		switch (this.tripName.split(" ")[0]) {
		case "TGV":
			this.type = TrainType.TGV;
			break;
		case "ICE":
			this.type = TrainType.ICE;
			break;
		case "THA":
			this.type = TrainType.THA;
			break;
		case "AVE":
			this.type = TrainType.AVE;
			break;
		case "TER":
			this.type = TrainType.TER;
			break;
		case "EC":
			this.type = TrainType.EC;
			break;
		}

	}

	public String getTripName() {
		return tripName;
	}

	void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public Route getRoute() {
		return route;
	}

	void setRoute(Route route) {
		this.route = route;
	}

	public DayTime getStartTime() {
		return startTime;
	}

	void setStartTime(DayTime startTime) {
		this.startTime = startTime;
	}

	public DayTime getEndTime() {
		return endTime;
	}

	void setEndTime(DayTime endTime) {
		this.endTime = endTime;
	}

	public Duration getDuration() {
		return duration;
	}

	void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s: %s->%s", this.route, this.tripName, this.startTime, this.endTime);
	}

}
