package iagen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class AritGeneticaUtil {
	
	private static final int MAS = -1;
	private static final int MENOS = -2;
	private static final int POR = -3;
	private static final int ENTRE = -4;
	private static final int individualLength = 11;
	
	private static int objectiveResult = 101;
	

	
	public static FitnessFunction<Integer> getFitnessFunction(){
		return new AritGeneticaFitnessAlgo();
	}
	
	public static GoalTest getGoalTest() {
		return new AritGeneticaGoalTest();		
	}
	
	public static void setObjectiveResult(int objective) {
		objectiveResult = objective;
	}
	
	public static class AritGeneticaFitnessAlgo implements FitnessFunction<Integer> {

		@Override
		public double apply(Individual<Integer> individual) {
			return 1 /(1 + Math.abs((objectiveResult -  calculateExpression(individual.getRepresentation()))));
		}	
	}	
	
	public static class AritGeneticaGoalTest implements GoalTest{

		@Override
		public boolean isGoalState(Object o) {
			@SuppressWarnings("unchecked")
			Individual<Integer> state = (Individual<Integer>) o;
			int sum = calculateExpression(state.getRepresentation());

			return sum == objectiveResult;
		}
	}
	
	public static int calculateExpression(List<Integer> individual){
		int sum = individual.get(0);
		
		for(int i = 1; i < individualLength; i += 2) {
			int op = individual.get(i);
			int num = individual.get(i+1);
			
			if(op==MAS)
				sum += num;
			else if(op == MENOS)
				sum -= num;
			else if(op == POR)
				sum *= num;
			else if(op == ENTRE)
				sum /= num;
		}
		return sum;
	}

	public static Collection<Integer> getFiniteAlphabetForRange(int range) {
		Collection<Integer> alf = new ArrayList<Integer>();

		for (int i = 1; i <= range; i++) 
			alf.add(i);
		
		alf.add(MAS);
		alf.add(MENOS);
		alf.add(POR);
		alf.add(ENTRE);
		
		return alf;
	}
	
	
	public static Individual<Integer> generateRandomIndividual(int range){
		List<Integer> repr = new ArrayList<Integer>();
		for(int i = 0; i < individualLength ; i++) 
			repr.add(i % 2 == 0 ? 
					new Random().nextInt(range - 1) + 1 : 
						(new Random().nextInt(3) + 1) * -1);
			
		Individual<Integer> individual = new Individual<Integer>(repr); //TODO FIX?	: esta linea deja un 0 al final de invididual (watchearlo)
		repr = individual.getRepresentation();
		return individual;
	}

	public static String getExpression(Individual<Integer> bestIndividual) {
		String out="";
		for(Integer i : bestIndividual.getRepresentation()) {
			if(i==MAS)
				out += " + ";
			else if(i==MENOS)
				out += " - ";
			else if(i==POR)
				out += " * ";
			else if(i==ENTRE)
				out += " / ";
			else
				out += i;
		}
		
		return out;
	}
}
