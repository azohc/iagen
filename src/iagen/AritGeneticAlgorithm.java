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
public class AritGeneticAlgorithm<Integer> extends GeneticAlgorithm<Integer>{
	
	private static int objectiveResult;


	public AritGeneticAlgorithm(int individualLength, Collection<Integer> finiteAlphabet, double mutationProbability) {
		super(individualLength, finiteAlphabet, mutationProbability);
	}
	
	public AritGeneticAlgorithm(int individualLength, Collection<Integer> finiteAlphabet, double mutationProbability,
			Random random, int objective) {
		super(individualLength, finiteAlphabet, mutationProbability, random);
		objectiveResult = objective;
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
			//TODO DEBUG CHECK RANGES
			int alphaOffset = (mutateOffset % 2 == 0) ? new Random().nextInt(finiteAlphabet.size() - 4)  : new Random().nextInt(4) + finiteAlphabet.size() - 4;
			
			List<Integer> mutatedRepresentation = new ArrayList<Integer>(child.getRepresentation());

			mutatedRepresentation.set(mutateOffset, finiteAlphabet.get(alphaOffset));

			Individual<Integer> mutatedChild = new Individual<Integer>(mutatedRepresentation);

			
			
			return mutatedChild;
		}
}
