package base.operators.crossover;

import base.variables.Individual;

public class TwoPointCrossover extends Crossover {

	public TwoPointCrossover(Individual parent1, Individual parent2) {
		super(parent1, parent2);
		// TODO Auto-generated constructor stub
	}

	public TwoPointCrossover(Individual parent1, Individual parent2, float xProbability) {
		super(parent1, parent2, xProbability);
	}
	
	@Override
	public void doCross(Individual parent1, Individual parent2) {
		// TODO Auto-generated method stub
		double random = Math.random();
		int totalbits = 0;
		String parent1WtcutOff = "";
		String parent2WtcutOff = "";
		String p1_before ="";
		String p2_before ="";
		String p1_after="";
		String p2_after="";
		String p1_between="";
		String p2_between="";
		String p1_final="";
		String p2_final="";
		
		if (random <= getXProbability()){
			for (int i = 0; i < parent1.getChromosome().getGenotype().length(); i++){
				totalbits = totalbits + parent1.getChromosome().getGenotype().getGene(i).getNbBits();
			}
			
			int cutOffPoint = (int) (Math.random()*totalbits);
			// on rajoute un point de crossover
			int cutOffPoint2 = (int) (Math.random()*totalbits);
			
			// On prend une val différente du 1er point
			while (cutOffPoint2 == cutOffPoint){
				cutOffPoint2 = (int) (Math.random()*totalbits);
			}
			
			if (cutOffPoint > cutOffPoint2){
				int tmp = cutOffPoint2;
				cutOffPoint2 = cutOffPoint;
				cutOffPoint = tmp;
			}
			
			for (int i = 0; i < parent1.getChromosome().getGenotype().length(); i++){
				parent1WtcutOff =  parent1WtcutOff + parent1.getAllele(i).getStringValue();
				parent2WtcutOff =  parent2WtcutOff + parent2.getAllele(i).getStringValue();
			}					
			
			// avant
			p1_before = parent1WtcutOff.substring(0, cutOffPoint);			
			p2_before = parent2WtcutOff.substring(0, cutOffPoint);
			
			// après
			p1_after = parent1WtcutOff.substring(cutOffPoint2);
			p2_after= parent2WtcutOff.substring(cutOffPoint2);		
			
			p1_between = parent1WtcutOff.substring(cutOffPoint, cutOffPoint2);	;
			p2_between = parent2WtcutOff.substring(cutOffPoint, cutOffPoint2);	
			
			p1_final = p1_before + p2_between + p1_after;
			p2_final = p2_before + p1_between + p2_after;
		
			for (int i = 0; i < parent1.getChromosome().getGenotype().length(); i++){
				offspring1.setStringAllele(i, p1_final.substring(0,parent1.getChromosome().getGenotype().getGene(i).getNbBits()));
				offspring2.setStringAllele(i, p2_final.substring(0,parent1.getChromosome().getGenotype().getGene(i).getNbBits()));	
					p1_final = p1_final.substring(parent1.getChromosome().getGenotype().getGene(i).getNbBits());
					p2_final = p2_final.substring(parent1.getChromosome().getGenotype().getGene(i).getNbBits());
			}		

		}else{
			offspring1 = parent1.copy();
			offspring2 = parent2.copy();
		}
	}

}
