package edu.iastate.cs228.hw1;

/**
 * 
 * a. If there is any reseller in its neighborhood, then the reseller causes outage in the
	casual user cell. Thus, the state of the cell changes from C (Casual) to O
	(Outage).
	b. Otherwise, if there is any neighbor who is a streamer, then the casual user also
	becomes a streamer, in the hopes of making it big on the internet.
 * 
 * @author Nathan Krieger
 *
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {

		super(p, r, c);

	}

	@Override
	public State who() {

		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {

			return new Reseller(tNew, row, col);
		} else if (nCensus[RESELLER] != 0) {

			return new Outage(tNew, row, col);

		} else if (nCensus[STREAMER] != 0) {

			return new Streamer(tNew, row, col);

		} else if (nCensus[CASUAL] >= 5) {

			return new Streamer(tNew, row, col);
		}

		return new Casual(tNew, row, col);
	}

}
