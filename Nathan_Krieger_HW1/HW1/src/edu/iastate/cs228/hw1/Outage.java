package edu.iastate.cs228.hw1;

/**
 * An Outage cell becomes an Empty cell, meaning internet access is restored
	on the billing cycle after an outage.

 * @author Nathan Krieger
 *
 */
public class Outage extends TownCell {

	public Outage(Town p, int r, int c) {

		super(p, r, c);

	}

	@Override
	public State who() {

		return State.OUTAGE;
	}

	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if (nCensus[CASUAL] >= 5) {

			return new Streamer(tNew, row, col);
		}

		return new Empty(tNew, row, col);
	}

}
