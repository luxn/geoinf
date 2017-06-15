package de.jadehs.trawell.graph;

public enum TrainType {
	TGV(300), ICE(250), THA(300), AVE(250), TER(150), EC(200);

	private final int speed;

	TrainType(int speed) {
		this.speed = speed;
	}

	private int speed() {
		return speed;
	}

	Duration calcDuration(double distance) {
		double dur = distance / speed();
		return new Duration(dur);
	}
}
