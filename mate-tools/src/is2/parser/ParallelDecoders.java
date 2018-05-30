package is2.parser;

import is2.data.DataFES;

import java.util.ArrayList;

/**
 *
 */
public class ParallelDecoders {

	protected static class DSet {
		short w1, w2;
	}

	private ArrayList<DSet> sets = new ArrayList<DSet>();

	private ArrayList<ParallelDecoder> pe = new ArrayList<ParallelDecoder>();

	synchronized protected DSet get() {
		synchronized (sets) {
			if (sets.size() == 0) return null;
			return sets.remove(sets.size() - 1);
		}
	}

	public void addInSets(short w1, short w2) {
		DSet ds = new DSet();
		ds.w1 = w1;
		ds.w2 = w2;
		sets.add(ds);
	}

	boolean add(short[] pos, DataFES d, Open o[][][][], Closed c[][][][], int length) {
		return pe.add(new ParallelDecoder(this, pos, d, o, c, length));
	}


	ArrayList<ParallelDecoder> getParallelDecoders() {
		return pe;
	}


}
