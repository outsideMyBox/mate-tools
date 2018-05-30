package is2.parser;

import is2.data.DataFES;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * @author Dr. Bernd Bohnet, 30.08.2009
 * 
 * This class implements a parallel edge rearrangement for non-projective parsing;
 * The linear method was first suggest by Rayn McDonald et. al. 2005.  
 */
final public class ParallelRearrange implements Callable<Object> {

	// new parent child combination to explore
	final static class PA {
		final float p;
		final short ch, pa;
		public float max;
		public short wh;
		public short nPar;
		public short nType;
		public PA(float p2, short ch2, short pa2) { p=p2; ch=ch2;pa=pa2;}
	}

	// best new parent child combination, found so far
	public float max;

	// some data from the dependency tree
	//private EdgesC edges;	
	private short[] pos;
	private DataFES x;
	private boolean[][] isChild ;	
	public short[] heads,types;
	
	// child, new parent, new label
	public short wh,nPar,nType;

	private ParallelRearranges parallelRearranges;
	
	/**
	 * Initialize the parallel rearrange thread
	 *
	 * @param parallelRearranges
	 * @param isChild2 is a child
	 * @param pos the part-of-speech 
	 * @param x the data
	 * @param s the heads
	 * @param ts the types
	 */
	public ParallelRearrange(ParallelRearranges parallelRearranges,
	                         boolean[][] isChild2,short[] pos, DataFES x, short[] s, short[] ts) {
		
		heads =new short[s.length];
		System.arraycopy(s, 0,  heads, 0, s.length);

		types =new short[ts.length];
		System.arraycopy(ts, 0,  types, 0, ts.length);

		isChild=isChild2;
	    //edges = edgesC;
		this.parallelRearranges = parallelRearranges;
		this.pos =pos;
		this.x=x;
	}


	@Override
	public Object call() {
		
		// check the list of new possible parents and children for a better combination
		while(true) {
			PA px = parallelRearranges.getPA();
			if (px==null) break;

			float max=0;
			short pa =px.pa, ch =px.ch;
			
			if(ch == pa || pa == heads[ch] || isChild[ch][pa]) continue;

			short oldP = heads[ch], oldT = types[ch]; 

			heads[ch]=pa;

			short[] labels = Edges.get(pos[pa], pos[ch]);

			for(int l=0;l<labels.length;l++) {

				types[ch]=labels[l];

				float p_new = Extractor.encode3(pos, heads, types, x);

				if(max < p_new-px.p ) {
					max = p_new-px.p; wh = ch; nPar = pa; nType = labels[l] ;
					px.max=max;
					px.wh=ch;
					px.nPar = pa;
					px.nType =labels[l];
				}
			}
			heads[ch]= oldP; types[ch]=oldT;
		}
		return null;
	}



	
}
