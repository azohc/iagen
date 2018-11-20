package iagen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.NQueensGenAlgoUtil;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;

public class AritGeneticaDemo {

	public static final int DIEZ = 10;
	public static final int VEINTICINCO = 25;
	public static final int CINCUENTA = 50;
	
	public static final double MUTATION_PROBABILITY = 0.15d;
	
	public static void main(String[] args) {

		newArtiGeneticaDemo(DIEZ);
		newArtiGeneticaDemo(VEINTICINCO);
		newArtiGeneticaDemo(CINCUENTA);

	}
	
	public static void newArtiGeneticaDemo(int rango){
		System.out.println("\nArithmetics GeneticAlgorithm  -->");
		try {
			FitnessFunction<Integer> fitnessFunction = AritGeneticaUtil.getFitnessFunction();
			GoalTest goalTest = AritGeneticaUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(AritGeneticaUtil.generateRandomIndividual(rango));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(rango, 
					AritGeneticaUtil.getFiniteAlphabetForRange(rango), MUTATION_PROBABILITY);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("Max Time (1 second) Best Individual=\n"
					+ AritGeneticaUtil.getExpression(bestIndividual));
			System.out.println("Rango           = " + rango);
			//System.out.println("# Board Layouts = " + (new BigDecimal(rango)).pow(rango));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);

			System.out.println("");
			System.out.println("Goal Test Best Individual=\n" + AritGeneticaUtil.getExpression(bestIndividual));
			System.out.println("Board Size      = " + rango);
			//System.out.println("# Board Layouts = " + (new BigDecimal(rango)).pow(rango));
			System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
