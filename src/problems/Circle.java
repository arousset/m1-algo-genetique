package problems;

import base.variables.Individual;


/**
 * @author ROUSSET Alban & KAWCZAK Clément
 *
 * Last update March 2012
 * 
 */
public class Circle extends Problem {

	/** 
	 * Constructor. 
	 */
	public Circle() {
		super();
        problemName_ = "Cercle";
        numberOfVariables_ = 2;
        
        lowerLimit_ = new double [numberOfVariables_];
        upperLimit_ = new double [numberOfVariables_];
        precision_ = new int [numberOfVariables_];
        
        
        // For A
        lowerLimit_[0] = 10.0 ;
        upperLimit_[0] = 80.0;
        precision_[0] = 1;

        // For B
        lowerLimit_[1] = 0.0 ;
        upperLimit_[1] = 100.0;
        precision_[1] = 1;
        
        super.setObjective("maximize");
	}
	
	@Override
	public double[] evaluate(Individual individual) {

		double diametreA = individual.getAllele(0).getDoubleValue();
        double bprime = individual.getAllele(1).getDoubleValue();
        double diametreB = (bprime/(Math.pow(2,7)-1))*(90-diametreA-10)+10; 
        double diametreC = 100 - diametreA - diametreB;
        double surfaceD = 50 * 50 * Math.PI;
        double surfaceA = (diametreA/2) *  (diametreA/2) * Math.PI;
        double surfaceB = (diametreB/2) *  (diametreB/2) * Math.PI;
        double surfaceC = (diametreC/2) *  (diametreC/2) * Math.PI;    
        double fitness = surfaceD - (surfaceA + surfaceB + surfaceC) ;    

        individual.setFitness(fitness);
        
        double valeur[] = new double[3];
        valeur[0]=diametreA;
        valeur[1]=diametreB;
        valeur[2]=diametreC;    
        
        return valeur;
	}

		
}
