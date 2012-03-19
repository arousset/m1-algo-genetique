package base.operators.mutation;

import base.variables.Individual;

/**
 * @author Wahabou Abdou
 * wahabou.abdou@univ-fcomte.fr
 *
 * 
 * June 2010
 * 
 * Lastupdate : March 2011
 */

public class UniformMutation extends Mutation{

	public UniformMutation(Individual individual) {
		super(individual);
	}
	
	public UniformMutation(Individual individual, float probability) {
		super(individual, probability);
	}

	@Override
	public Individual doMutate() {
		
		/*
		 *  Inserez votre code ici
		 */
		
		return individual;
	}

}
