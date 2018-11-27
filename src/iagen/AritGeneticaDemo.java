package iagen;

import java.io.InputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class AritGeneticaDemo {

	public static final int DIEZ = 10;
	public static final int VEINTICINCO = 25;
	public static final int CINCUENTA = 50;
	
	private static final int individualLength = 11;
	
	private static int objectiveResult;


	protected static final double MUTATION_PROBABILITY = 0.15d;
	
	public static void main(String[] args) {
		
		readObjectiveResult();
		newArtiGeneticaDemo(DIEZ);
		newArtiGeneticaDemo(VEINTICINCO);
		newArtiGeneticaDemo(CINCUENTA);

	}
	
	private static void readObjectiveResult() {

		System.out.print("Enter a number in the range (100, 1000) be obtained by the algorithm: ");
		Scanner s = new Scanner(System.in);
		boolean validInput = false;
		while(!validInput) {
			try {
				validInput = true;
				objectiveResult = s.nextInt();		
				if(objectiveResult <= 100 || objectiveResult >= 1000) {
					validInput = false;
					System.out.print("Please enter an integer value in the range (100, 1000): ");
				}
			} catch (Exception e) {
				System.err.println("Exception arised due to user input.");
				e.printStackTrace();
				throw e;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void newArtiGeneticaDemo(int range){
		System.out.println("\nArithmetics Genetic Algorithm -->");
		try {
			FitnessFunction<Integer> fitnessFunction = AritGeneticaUtil.getFitnessFunction();
			GoalTest goalTest = AritGeneticaUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(AritGeneticaUtil.generateRandomIndividual(range));
			}
			
			AritGeneticaUtil.setObjectiveResult(objectiveResult);
			
			AritGeneticAlgorithm<Integer> ga = new AritGeneticAlgorithm<Integer>(individualLength, 
					AritGeneticaUtil.getFiniteAlphabetForRange(range), MUTATION_PROBABILITY);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("\tMax Time (1 second) Best Individual =\n\t\t"
					+ AritGeneticaUtil.getExpression(bestIndividual));
			System.out.println("\tRange           = [1," + range + "]");
			//System.out.println("# Board Layouts = " + (new BigDecimal(rango)).pow(rango));
			System.out.println("\tFitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
			
			System.out.println("");
			System.out.println("\tGoal Test Best Individual =\n\t\t" + AritGeneticaUtil.getExpression(bestIndividual));
			System.out.println("\tRange           = [1," + range + "]");
			//System.out.println("# Board Layouts = " + (new BigDecimal(rango)).pow(rango));
			System.out.println("\tFitness         = " + fitnessFunction.apply(bestIndividual));
			System.out.println("\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations       = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
