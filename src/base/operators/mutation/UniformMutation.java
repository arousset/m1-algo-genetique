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
		
		// On générère un chiffre aléatoire pour la selection du point
		double random = Math.random();
		int totalbits = 0;
		int point1 = (int) (Math.random()*totalbits);
		int point2 = (int) (Math.random()*totalbits);
		String indivuduwc = "";
		String individumuted_partial =  "";
		String individu_identiquep1;
		String individu_identiquep2;
		String portion_muted;
		String individu_muted;
		String new_val_chro;
		
		// On récupère le génotype
		Individual muted = new Individual(individual.getChromosome().getGenotype());
		
		// Sinon impossible
		if(random <= probability) {
			// On récupère le nombre de bits
			for (int i = 0; i < individual.getChromosome().getGenotype().length(); i++){
				totalbits = totalbits + individual.getChromosome().getGenotype().getGene(i).getNbBits();
			}
			
			// On choisit 2 point différents
			while (point2 == point1){
				point2 = (int) (Math.random()*totalbits);
			}
			
			// Si supérieur on switch les 2 points
			if (point1 > point2){
				int tmp = point2;
				point2 = point1;
				point1 = tmp;	
			}
			
			// On effectue la mutations
			for (int i = 0; i < individual.getChromosome().getGenotype().length(); i++){
				indivuduwc =  indivuduwc + individual.getAllele(i).getStringValue();
				individumuted_partial = individumuted_partial + muted.getAllele(i).getStringValue();
			}	
							
			individu_identiquep1 = indivuduwc.substring(0, point1);			
			individu_identiquep2 = indivuduwc.substring(point2);
			portion_muted = individumuted_partial.substring(point1, point2);
			individu_muted = individu_identiquep1 + portion_muted + individu_identiquep2;			
			
			// On set les nouvelles valeurs
			for (int i = 0; i < individual.getChromosome().getGenotype().length(); i++){
				new_val_chro = individu_muted.substring(0,individual.getChromosome().getGenotype().getGene(i).getNbBits());				
				individual.setStringAllele(i, new_val_chro);		
				individu_muted = individu_muted.substring(individual.getChromosome().getGenotype().getGene(i).getNbBits());				
			}	
		}
		return individual;
	}
}
