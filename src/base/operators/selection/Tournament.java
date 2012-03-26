package base.operators.selection;

/**
 * Tournament Selection based on the C-language implementation of SGA by R.
 * Smith, D. Goldberg and J. Earickson.
 */

import java.util.*;

import base.variables.*;
public class Tournament extends Selection{

	private int[] tourneylist;

	private int tourneypos;

	private int tourneysize;


	/**
	 * @param 
	 * 
	 */

	public Tournament(List<Individual> _pop) {
		super(_pop);
		tourneylist = new int[population.size()];
		tourneysize = 2;
		preselect();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see operators.Selector#preselect()
	 */
	public void preselect() {
		shuffleList();
		tourneypos = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see operators.Selector#select()
	 */
	public Individual doSelect() {
		int choice, winner = 0;

		if (tourneypos != population.size()-1 ){
			
		}else{
			
		}
		
		return population.get(winner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see operators.Selector#reset()
	 */
	public void reset() {
		//!??!
	}

	/*
	 * 
	 */
	private void shuffleList() {
		// shuffleList existe déja ?! mais il faut passer en paramètre population donc je fait comme cela mais shuffleList serait mieux
		Collections.shuffle(population);
	}

}
