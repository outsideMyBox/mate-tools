package is2.parser;

import is2.data.DataFES;

import java.util.ArrayList;

/**
 *
 */
public class ParallelRearranges {

	private ArrayList<ParallelRearrange> pe = new ArrayList<ParallelRearrange>();

	// list of parent child combinations
	protected ArrayList<ParallelRearrange.PA> parents = new ArrayList<ParallelRearrange.PA>();
	protected ArrayList<ParallelRearrange.PA> order = new ArrayList<ParallelRearrange.PA>();

	public void clear() {
		pe.clear();
	}

	public void clearOrder() {
		order.clear();
	}

	public ArrayList<ParallelRearrange.PA> getOrder() {
		return order;
	}

	public boolean add(boolean[][] isChild, short[] pos, DataFES x, short[] s, short[] ts) {
		return pe.add(new ParallelRearrange(this, isChild, pos, x, s, ts));
	}

	public ArrayList<ParallelRearrange> getParallelRearranges() {
		return pe;
	}

	/**
	 * Add a child-parent combination which are latter explored for rearrangement
	 *
	 * @param p2
	 * @param ch2
	 * @param pa
	 */
	public void addInParentsAndOrder(float p2, short ch2, short pa) {
		ParallelRearrange.PA px = new ParallelRearrange.PA(p2, ch2, pa);
		parents.add(px);
		order.add(px);
	}

	protected ParallelRearrange.PA getPA() {
		synchronized (parents) {
			if (parents.size() == 0) return null;
			return parents.remove(parents.size() - 1);
		}
	}
}
