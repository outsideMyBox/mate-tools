package is2.parser;

import is2.data.Cluster;
import is2.data.DataFES;
import is2.data.F2SF;
import is2.data.Instances;

import java.util.ArrayList;

/**
 *
 */
public class ParallelExtracts {

	private ArrayList<ParallelExtract> pe = new ArrayList<ParallelExtract>();

	protected ArrayList<ParallelExtract.DSet> sets = new ArrayList<ParallelExtract.DSet>();

	public boolean add(Extractor e, Instances is, int i, DataFES d, F2SF para, Cluster cluster) {
		return pe.add(new ParallelExtract(this, e, is, i, d, (F2SF) para.clone(), cluster));
	}

	public ArrayList<ParallelExtract> getParallelExtracts() {
		return pe;
	}

	protected ParallelExtract.DSet getSet() {

		synchronized (sets) {
			if (sets.size() == 0) return null;
			return sets.remove(sets.size() - 1);
		}
	}

	public void addInSets(int w1, int w2) {
		ParallelExtract.DSet ds = new ParallelExtract.DSet();
		ds.w1 = w1;
		ds.w2 = w2;
		sets.add(ds);
	}
}
