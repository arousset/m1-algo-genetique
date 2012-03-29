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

import com.sun.org.apache.xerces.internal.util.XML11Char;
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


	int popSize = 1000;
	int nbGenerations = 100;

	Selection selection;
	Crossover crossover;
	Mutation mutation;

	float xProba = (float) 0.8;
	float mProba = (float) 0.2;


	public AG_Simple(Problem problem, int taille_pop, int nbrgeneration, float tx_mutation, float tx_cross) {
		// on récupère les params
		popSize = taille_pop;
		nbGenerations = nbrgeneration;
		xProba = tx_cross;
		mProba = tx_mutation;
		
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
		List<Individual> winner_selection = new ArrayList<Individual>();
		Population population = new Population(popSize,genotype);
		double valeur_diam[] = new double[3];
		double fitnesstotal=0;
		double moyennecourante = 0;
		double moyennetotale = 0;
		int taille_population_init=0;
		int best_ind=0;
		int gene=0;
		
		double M_diama=0;
		double M_diamb=0;
		double M_diamc=0;
		
		// Création de la 1er population
		for (int i = 0;i<popSize;i++){
			// Evaluation des individus (diam A, B et C et fitness)
			valeur_diam = problem_.evaluate(population.getIndividual(i));
			// On remplie notre List avec les individus générés
			popList.add(population.getIndividual(i));		
			bestSolution = findBest(popList);
			fitnesstotal = fitnesstotal + population.getIndividual(i).getFitness();
			moyennecourante = fitnesstotal/(i+1);
			
			System.out.println("Gen: "+gene+"\t|\t Individu :"+ i +"\t|\t A : " + valeur_diam[0] + "\t|\t B : " + valeur_diam[1]+ "\t|\t C : " + valeur_diam[2]+ "\t|\t fitness : " + population.getIndividual(i).getFitness()+"\t|\t Bestolution fitness : " + bestSolution.getFitness()+"\t|\t Fitness Moyen : "+moyennecourante );
		}
		
		for(int i=0;i<popSize;i++){
			if (population.getIndividual(i).getFitness() >= bestSolution.getFitness()){
				best_ind = i;
				M_diama=valeur_diam[0];
				M_diamb=valeur_diam[1];
				M_diamc=valeur_diam[2];
			}
		}
		
		moyennetotale = moyennetotale + moyennecourante;
		System.out.println("==> Fitness moyenne TOTAL : "+moyennetotale);
		
		// on garde la taille de la population initial
		taille_population_init = population.size();
		
		// Création des N populations suivantes
		for (int j=1; j<nbGenerations;j++){
			fitnesstotal=0;
			
			selection = new Tournament(popList); //ShuffleList	
			// La population est impair
			if (popSize%2 == 0){
				for (int i = 0; i< popSize/2;i++){
					// Tournement pour récupérer pop/2
					winner_selection.add(selection.doSelect());
				}	
			}else{
				// la population est pair donc on rajoute 1 individu du trounoi et on retirera un enfant
				// afin de garder la taille de la population initial
				for (int i = 0; i<=popSize/2+1;i++){	
					// Tournement pour récupérer pop/2+1
					winner_selection.add(selection.doSelect());
				}	
			}
			
			// On supprimme de notre population initial les perdants du tournoi pour garder que les 
			// winner et pour pouvoir accepter les enfants
			for(int i=0; i<population.size();i++){
				// vide toutes la population
				population.remove(population.getIndividual(i));
			}
				for(int i=0;i<winner_selection.size();i++){
					// On possede notre population avec les winners
					population.add(winner_selection.get(i));
				}
			
			int ii = 0;
			Individual parent1;
			Individual parent2;
			Individual offspring1;
			Individual offspring2;
			
			while (ii < (taille_population_init/2)){
				parent1 = population.getIndividual(ii);
				parent2 = population.getIndividual(ii+1);	
				
				crossover = new TwoPointCrossover(parent1,parent2,xProba); 
				crossover.doCross(parent1, parent2);
				
				// recupère les enfants issu des parents
				offspring1 = crossover.getOffspring(1);
				offspring2 = crossover.getOffspring(2);
				
				// Mutation 
				UniformMutation unim1 = new UniformMutation(offspring1, mProba);
				UniformMutation unim2 = new UniformMutation(offspring2, mProba);
				
				offspring1 = unim1.doMutate();
				offspring2 = unim2.doMutate();
				
				population.add(offspring1);
				population.add(offspring2);	
					
				// Pas de 2 car 2 parents pour Croisement
				ii=ii+2;
			}
			
			for (int i = 0;i<popSize;i++){
				valeur_diam = problem_.evaluate(population.getIndividual(i));
				if (population.getIndividual(i).getFitness() > bestSolution.getFitness()){
					best_ind = i;
					gene=j;
					M_diama=valeur_diam[0];
					M_diamb=valeur_diam[1];
					M_diamc=valeur_diam[2];
				}
				
				population.getIndividual(i).setEvaluated(true);
				offspringsPop.add(population.getIndividual(i));
							
				fitnesstotal = fitnesstotal + population.getIndividual(i).getFitness();	
				
				System.out.println("Gen: "+j+"\t|\t Individu :"+ i +"\t|\t A : " + valeur_diam[0] + "\t|\t B : " + valeur_diam[1]+ "\t|\t C : " + valeur_diam[2]+ "\t|\t fitness : " + population.getIndividual(i).getFitness()+"\t|\t Bestolution fitness : " + bestSolution.getFitness()+"\t|\t Fitness Moyen : "+moyennecourante );													
				moyennecourante = fitnesstotal/(i+1);
			}
			moyennetotale = moyennetotale + moyennecourante;
			bestSolution = findBest(offspringsPop);	
		}
		System.out.println();
		System.out.println("Moyenne fitness totale : " + moyennetotale / nbGenerations );
		System.out.println("Meilleur individu ==> BestId : "+best_ind+" | Gen : "+gene+" | Diam A : " + M_diama + " | Diam B : " + M_diamb+ " | Diam C : " + M_diamc+ " | fitness : "+bestSolution.getFitness());
		
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
