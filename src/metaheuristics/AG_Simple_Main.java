package metaheuristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import problems.*;

import base.variables.*;

/**
 * @author Wahabou Abdou
 * wahabou.abdou@univ-fcomte.fr
 *
 * 
 * Lastupdate : March 2011
 */

public class AG_Simple_Main {

	public static void main(String[] args) {
		Individual bestSolution = null;	
		Problem problem = new Circle();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Taille population : ");
		String str = sc.nextLine();
		int taille_pop = Integer.parseInt(str);
			System.out.println("Nombre de generations : ");
			String str1 = sc.nextLine();
			int nbrgeneration = Integer.parseInt(str);
				System.out.println("Probabilite de croisement : ");
				String str2 = sc.nextLine();
				float tx_cross = Float.parseFloat(str);
					System.out.println("Probabilite de mutation : ");
					String str3 = sc.nextLine();
					float tx_mutation = Float.parseFloat(str);
		AG_Simple algo = new AG_Simple(problem, taille_pop, nbrgeneration, tx_mutation, tx_cross);
		try {
			bestSolution = algo.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("===> La meilleure valeur de fitness trouvee est : " + bestSolution.getFitness());
		//System.out.println("====> Les valeurs sont :  " + bestSolution.getChromosome().);
	}

}
