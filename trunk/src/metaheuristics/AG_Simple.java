package metaheuristics;

/**
 * @author Wahabou Abdou
 * wahabou.abdou@univ-fcomte.fr
 *
 * 
 * Lastupdate : March 2011
 */

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import problems.Problem;
import util.*;
import base.operators.crossover.*;
import base.operators.mutation.*;
import base.operators.selection.RandomSelection;
import base.operators.selection.Selection;
import base.operators.selection.Tournament;
import base.variables.*;

public class AG_Simple {

	Problem problem_;
	private Genotype genotype;

	private Individual bestSolution;


	int popSize = 60;
	int nbGenerations = 100;

	Selection selection;
	Crossover crossover;
	Mutation mutation;

	float xProba = (float) 0.8;
	float mProba = (float) 0.01;


	public AG_Simple(Problem problem) {
		// Instance du problem
		problem_ = problem;
		// Instznce de Circle
		genotype = new Genotype(problem);

	} // Mprea

	/**   
	 * Runs of the MPREA algorithm.
	 * @return a set of non dominated solutions as a result of the algorithm execution  
	 * @throws ClassNotFoundException 
	 */  
	public Individual execute() throws ClassNotFoundException {

		List<Individual> popList = new ArrayList<Individual>();
		List<Individual> offspringsPop = new ArrayList<Individual>();

		Population population = new Population(popSize,genotype);
		double valeur_diam[] = new double[3];
		
		double fitnesstotal=0;
		double moyennecourante = 0;
		double moyennetotale = 0;
		
		
		// Création de la 1er population
		for (int i = 0;i<popSize;i++){
			// Evaluation des individus (diam A, B et C et fitness)
			valeur_diam = problem_.evaluate(population.getIndividual(i));	
			
			// On remplie notre List avec les individus générés
			popList.add(population.getIndividual(i));
			
			bestSolution = findBest(popList);

			fitnesstotal = fitnesstotal + population.getIndividual(i).getFitness();
			moyennecourante = fitnesstotal/(i+1);
			
			System.out.println("Individu : "+ i +"\t|\t A : " + valeur_diam[0] + "\t|\t B : " + valeur_diam[1]+ "\t|\t C : " + valeur_diam[2]+ "\t|\t fitness : " + population.getIndividual(i).getFitness()+"\t|\t Bestolution : " + bestSolution.toString()+"\t|\t Fitness Moyen : "+moyennecourante );
		}

		System.out.println("==> Fitness moyenne TOTAL : "+moyennecourante);
		
		
		// Création des N populations suivantes
		//////////////////////////////////////////////////////////////////////////////////
		/*double meilleurdiametreA=0;
		double meilleurdiametreB=0;
		double meilleurdiametreC=0;
		double meilleuridinvididu = 0;
		double meilleuregeneration = 0;
		double valeur[] = new double[3];
		
		for (int u=1; u < nbGenerations;u++){
			
			System.out.println();														

			//bestSolution = 0 ;
			fitnesstotal=0;
										
	
			// Selection	
			Population winner = new Population(genotype);
			Population loser = new Population(genotype);
			
			Selection 	s = new Tournament(population.getAllIndividuals());	
			
			if (popSize%2 == 0){
			
				for (int i = 0; i< popSize/2;i++){	
					Individual winnerloser[] = s.doSelect();
					if (i != popSize){		
						winner.add(winnerloser[0]);
						loser.add(winnerloser[1]);
					}
					else{
						loser.add(winnerloser[1]);
					}
					
				}	
			
			}else{
				
				for (int i = 0; i<= popSize/2;i++){	
					Individual winnerloser[] = s.doSelect();
					if (i != popSize/2){		
						winner.add(winnerloser[0]);
						loser.add(winnerloser[1]);
					}
					else{
						loser.add(winnerloser[1]);
					}
					
				}	
				
			}
			
			
			if ((popSize/2)%2 != 0){
				loser.add(winner.getIndividual(winner.size()-1));
				winner.remove(winner.getIndividual(winner.size()-1));
			}
			
			Population generation2 = new Population(genotype);
			for (int i = 0;i<loser.size();i++){
				generation2.add(loser.getIndividual(i));
			}
				
			Crossover c;	
			
			int k = 0;
			while (k < winner.size()){
				Individual i1 = winner.getIndividual(k);
				Individual i2 = winner.getIndividual(k+1);		
				c = new TwoPointCrossover(i1,i2,crossover); 
				c.doCross(i1, i2);
			
				Individual offspring1 = c.getOffspring(1);
				Individual offspring2 = c.getOffspring(2);
				
				
				// mutation 
				UniformMutation um1 = new UniformMutation(offspring1, mutation);
				UniformMutation um2 = new UniformMutation(offspring2, mutation);
				
				offspring1 = um1.doMutate();
				offspring2 = um2.doMutate();
				
				
				
				generation2.add(offspring1);
				generation2.add(offspring2);		
				k=k+2;
			}
	
			
			for (int i = 0;i<popSize;i++){
				valeur = problem_.evaluate(generation2.getIndividual(i));	
				if (generation2.getIndividual(i).getFitness() > bestSolution){
					bestSolution = generation2.getIndividual(i).getFitness();	
				}
				
				if (MeilleurIndividu.getFitness() < bestSolution){
					MeilleurIndividu = generation2.getIndividual(i);
					meilleurdiametreA = valeur[0];
					meilleurdiametreB = valeur[1];
					meilleurdiametreC = valeur[2];
					meilleuregeneration = u;
					meilleuridinvididu = i;
				}			
				
				
				fitnesstotal = fitnesstotal + generation2.getIndividual(i).getFitness();	
				
				System.out.println("Individu : "+ i +" || Di A : " + valeur[0] + " || Di B : " + valeur[1]+ " || Di C : " + valeur[2]+ "|| fitness : " + generation2.getIndividual(i).getFitness()+ " #### Meilleure solution : " + bestSolution + " #### Fitness Moyen : " + fitnesstotal/(i+1));													
				moyennecourante = fitnesstotal/(i+1);
				
				
			}
			moyennetotale = moyennetotale + moyennecourante;
		
		}
		
		System.out.println();														
		System.out.println();														

		
		System.out.println("Meilleure individu toute génération confondue :  Individu N° " + meilleuridinvididu + " de la génération " + meilleuregeneration + " // diametre A : " + meilleurdiametreA + " // diametre B : " + meilleurdiametreB + " // diametre C : " +  meilleurdiametreB + " ### Fitness " + MeilleurIndividu.getFitness());														
	
		System.out.println();														

		System.out.println("Moyenne totale : " + moyennetotale / nbGenerations );		
		
		*/
		
		//////////////////////////////////////////////////////////////////////////////////
		
		return bestSolution;
	} // execute


	/*
	 * 
	 */

	public Individual findBest(List<Individual> population){
		List<Individual>  sortedPopulation = QuickSort.sort(population);
			if (problem_.isTominize()){
				return sortedPopulation.get(0);
			} else {
				int lastIndex = sortedPopulation.size() - 1;
				return sortedPopulation.get(lastIndex);
			}
	}

	public Individual findBest(Individual indiv1, Individual indiv2){
		if (problem_.isTominize()){
			if(indiv1.getFitness() < indiv2.getFitness()){
				return indiv1;
			} else{
				return indiv2;
			}
		} else {
			if(indiv1.getFitness() > indiv2.getFitness()){
				return indiv1;
			} else{
				return indiv2;
			}
		} 
	}
	
	public Individual getBestSolution(){
		return bestSolution;
	}

}
