package iagen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;
import aima.core.util.CancelableThread;

@SuppressWarnings("hiding")
public class DosHijosArithmeticExpressionGenAlgo<Integer> extends GeneticAlgorithm<Integer>{
	
	public DosHijosArithmeticExpressionGenAlgo(int individualLength, Collection<Integer> finiteAlphabet, double mutationProbability) {
		super(individualLength, finiteAlphabet, mutationProbability);
	}
	
	
	public Individual<Integer> geneticAlgorithm(Collection<Individual<Integer>> initPopulation, FitnessFunction<Integer> fitnessFn,
			GoalTest goalTest, long maxTimeMilliseconds) {
		Individual<Integer> bestIndividual = null;

		// Create a local copy of the population to work with
		List<Individual<Integer>> population = new ArrayList<Individual<Integer>>(initPopulation);
		// Validate the population and setup the instrumentation
		validatePopulation(population);
		updateMetrics(population, 0, 0L);

		long startTime = System.currentTimeMillis();

		// repeat
		int itCount = 0;
		do {
			population = nextGeneration(population, fitnessFn);
			bestIndividual = retrieveBestIndividual(population, fitnessFn);

			updateMetrics(population, ++itCount, System.currentTimeMillis() - startTime);

			// until some individual is fit enough, or enough time has elapsed
			if (maxTimeMilliseconds > 0L && (System.currentTimeMillis() - startTime) > maxTimeMilliseconds)
				break;
			if (CancelableThread.currIsCanceled())
				break;
		} while (!goalTest.isGoalState(bestIndividual));

		// return the best individual in population, according to FITNESS-FN
		return bestIndividual;
	}
	
	protected Individual<Integer> mutate(Individual<Integer> child) {
		int mutateOffset = randomOffset(individualLength);
		
		int alphaOffset = (mutateOffset % 2 == 0) ? new Random().nextInt(finiteAlphabet.size() - 4)  : new Random().nextInt(4) + finiteAlphabet.size() - 4;
		
		List<Integer> mutatedRepresentation = new ArrayList<Integer>(child.getRepresentation());

		mutatedRepresentation.set(mutateOffset, finiteAlphabet.get(alphaOffset));

		Individual<Integer> mutatedChild = new Individual<Integer>(mutatedRepresentation);
		
		return mutatedChild;
	}
	
	
	protected List<Individual<Integer>> nextGeneration(List<Individual<Integer>> population, FitnessFunction<Integer> fitnessFn) {
		// new_population <- empty set
		List<Individual<Integer>> newPopulation = new ArrayList<Individual<Integer>>(population.size());
		// for i = 1 to SIZE(population) do
		for (int i = 0; i < population.size() / 2; i++) {
			
			// x <- RANDOM-SELECTION(population, FITNESS-FN)
			Individual<Integer> x = randomSelection(population, fitnessFn);
			// y <- RANDOM-SELECTION(population, FITNESS-FN)
			Individual<Integer> y = randomSelection(population, fitnessFn);

			// reproduce utiliza un indice generado aleatoriamente para cruzar los padres
			// que salgan dos hijos iguales es poco probable, pero pueden salir
			Individual<Integer> childUno = reproduce(x, y); 
			Individual<Integer> childDos = reproduce(x, y); 
		
			// if (small random probability) then child <- MUTATE(child)
			if (random.nextDouble() <= mutationProbability) {
				childUno = mutate(childUno);
			}
			if (random.nextDouble() <= mutationProbability) {
				childDos = mutate(childDos);
			}
			// add child to new_population 
			newPopulation.add(childUno);
			newPopulation.add(childDos);
		}
		
		return newPopulation;
	}

}
