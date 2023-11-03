package edu.iastate.cs228.hw1;

/**
 * a. If there are 3 or fewer casual users in the neighborhood, then Reseller finds
	it unprofitable to maintain the business and leaves, making the cell Empty.
	b. Also, if there are 3 or more empty cells in the neighborhood, then the Reseller
	leaves, making the cell Empty. Resellers do not like unpopulated regions

 * @author Nathan Krieger
 *
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {

		census(nCensus);

		if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {

			return new Empty(tNew, row, col);

		} else if (nCensus[CASUAL] >= 5) {

			return new Streamer(tNew, row, col);
		}

		return new Reseller(tNew, row, col);
	}

}
