package iagen;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class ArithmeticExpressionDemo {
	
	private static final int individualLength = 11;
	
	private static int objectiveResult;


	protected static final double MUTATION_PROBABILITY = 0.15d;
	
	public static void main(String[] args) {
		
		readObjectiveResult();

	
		System.out.println("\n\n~~~  ArithmeticExpressionDemo: Basic Genetic Algorithm ~~~");
		originalArithmeticExpression();

		
		System.out.println("\n\n~~~  ArithmeticExpressionDemo: Cross probability = 0.7 ~~~");
		cruceArithmeticExpression(0.7d);

		
		
		System.out.println("\n\n~~~  ArithmeticExpressionDemo: Cross probability = 0.8 ~~~");
		cruceArithmeticExpression(0.8d);

		
		System.out.println("\n\n~~~  ArithmeticExpressionDemo: Dos hijos de un cruce ~~~");
		dosHijosArithmeticExpression();

		
		System.out.println("\n\n~~~  ArithmeticExpressionDemo: Cruce no destructivo ~~~");
		noDestructivoArithmeticExpression();


	}
	
	private static void readObjectiveResult() {

		System.out.print("Choose a number in the range (100, 1000) to be the target result: ");
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

	public static void originalArithmeticExpression(){
		System.out.println("\nArithmetic Expression Genetic Algorithm -->");
		try {
			FitnessFunction<Integer> fitnessFunction = ArithmeticExpressionUtil.getFitnessFunction();
			GoalTest goalTest = ArithmeticExpressionUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(ArithmeticExpressionUtil.generateRandomIndividual());
			}
			
			ArithmeticExpressionUtil.setObjectiveResult(objectiveResult);
			
			OriginalArithmeticExpressionGenAlgo<Integer> ga = new OriginalArithmeticExpressionGenAlgo<Integer>(individualLength, 
					ArithmeticExpressionUtil.getFiniteAlphabet(), MUTATION_PROBABILITY);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("\tMax Time (1 second) Best Individual:\n\t\t"
					+ ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
			
			System.out.println("");
			System.out.println("\tBest Individual (no time limit):\n\t\t" + ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cruceArithmeticExpression(double crossProb){
		System.out.println("\nArithmetics Genetic Algorithm -->");
		try {
			FitnessFunction<Integer> fitnessFunction = ArithmeticExpressionUtil.getFitnessFunction();
			GoalTest goalTest = ArithmeticExpressionUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(ArithmeticExpressionUtil.generateRandomIndividual());
			}
			
			ArithmeticExpressionUtil.setObjectiveResult(objectiveResult);
			
			CruceArithmeticExpressionGenAlgo<Integer> ga = new CruceArithmeticExpressionGenAlgo<Integer>(individualLength, 
					ArithmeticExpressionUtil.getFiniteAlphabet(), MUTATION_PROBABILITY, crossProb);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("\tMax Time (1 second) Best Individual:\n\t\t"
					+ ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
			
			System.out.println("");
			System.out.println("\tBest Individual (no time limit):\n\t\t" + ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dosHijosArithmeticExpression(){
		System.out.println("\nArithmetics Genetic Algorithm -->");
		try {
			FitnessFunction<Integer> fitnessFunction = ArithmeticExpressionUtil.getFitnessFunction();
			GoalTest goalTest = ArithmeticExpressionUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(ArithmeticExpressionUtil.generateRandomIndividual());
			}
			
			ArithmeticExpressionUtil.setObjectiveResult(objectiveResult);
			
			DosHijosArithmeticExpressionGenAlgo<Integer> ga = new DosHijosArithmeticExpressionGenAlgo<Integer>(individualLength, 
					ArithmeticExpressionUtil.getFiniteAlphabet(), MUTATION_PROBABILITY);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("\tMax Time (1 second) Best Individual:\n\t\t"
					+ ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
			
			System.out.println("");
			System.out.println("\tBest Individual (no time limit):\n\t\t" + ArithmeticExpressionUtil.getExpression(bestIndividual));			//System.out.println("# Board Layouts = " + (new BigDecimal(rango)).pow(rango));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public static void noDestructivoArithmeticExpression(){
		System.out.println("\nArithmetics Genetic Algorithm -->");
		try {
			FitnessFunction<Integer> fitnessFunction = ArithmeticExpressionUtil.getFitnessFunction();
			GoalTest goalTest = ArithmeticExpressionUtil.getGoalTest();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(ArithmeticExpressionUtil.generateRandomIndividual());
			}
			
			ArithmeticExpressionUtil.setObjectiveResult(objectiveResult);
			
			NoDestructivoArithmeticExpressionGenAlgo<Integer> ga = new NoDestructivoArithmeticExpressionGenAlgo<Integer>(individualLength, 
					ArithmeticExpressionUtil.getFiniteAlphabet(), MUTATION_PROBABILITY);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);

			System.out.println("\tMax Time (1 second) Best Individual:\n\t\t"
					+ ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
			
			System.out.println("");
			System.out.println("\tBest Individual (no time limit):\n\t\t" + ArithmeticExpressionUtil.getExpression(bestIndividual));
			System.out.print("\tFitness         = "); 
			System.out.printf("%.4f", fitnessFunction.apply(bestIndividual));
			System.out.println("\n\tIs Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("\tPopulation Size = " + ga.getPopulationSize());
			System.out.println("\tIterations      = " + ga.getIterations());
			System.out.println("\tTook            = " + ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
