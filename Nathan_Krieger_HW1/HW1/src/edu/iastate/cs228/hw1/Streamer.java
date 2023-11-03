package edu.iastate.cs228.hw1;

/**
 * a. If there is any reseller in the neighborhood, the reseller causes outage for the
	streamer as well.
	b. Otherwise, if there is any Outage in the neighborhood, then the streamer leaves
	and the cell becomes Empty.
	
 * @author Nathan Krieger
 *
 */
public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {

		super(p, r, c);

	}

	@Override
	public State who() {

		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {

			return new Reseller(tNew, row, col);

		} else if (nCensus[RESELLER] > 0) {

			return new Outage(tNew, row, col);

		} else if (nCensus[OUTAGE] > 0) {

			return new Empty(tNew, row, col);

		}

		return new Streamer(tNew, row, col);
	}

}
