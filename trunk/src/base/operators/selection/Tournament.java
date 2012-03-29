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
		int winner = 0;
		Individual indi1;
		Individual indi2;
		shuffleList();
		
		// permet de prendre 2 individus dans la population
		int alea1=(int) Math.random()*population.size();
		int alea2=(int) Math.random()*population.size();
				
		indi1 = population.get(alea1);
		indi2 = population.get(alea2);
		
		
		// Tournament
		// La selection se fait par tournoi deterministe 
		if(indi1.getFitness() > indi2.getFitness()) {
			winner = alea1;
		} else {
			winner = alea2;
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
