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
	
	private static int objectiveResult;

	
	public static FitnessFunction<Integer> getFitnessFunction(){
		return new AritGeneticaFitnessAlgo();
	}
	
	public static GoalTest getGoalTest() {
		return new AritGeneticaGoalTest();		
	}
	
	
	public static class AritGeneticaFitnessAlgo implements FitnessFunction<Integer> {

		@Override
		public double apply(Individual<Integer> arg0) {
			return 1 /(1 + Math.abs((objectiveResult -  calculateExpression(arg0.getRepresentation()))));
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
	
	public static int calculateExpression(List<Integer> indivRepr){
		int sum = indivRepr.get(0);
		for(int i = 1; i < indivRepr.size(); i += 2) {
			
			int op = indivRepr.get(i);
			int num = indivRepr.get(i+1);
			
			if(op==MAS)
				sum += num;
			else if(op == MENOS)
				sum -= num;
			else if(op == POR)
				sum += num;
			else if(op == ENTRE)
				sum /= num;
		}
		return sum;
	}

	public static Collection<Integer> getFiniteAlphabetForRange(int rango) {
		Collection<Integer> alf = new ArrayList<Integer>();

		for (int i = 1; i < rango; i++) 
			alf.add(i);
		
		alf.add(MAS);
		alf.add(MENOS);
		alf.add(POR);
		alf.add(ENTRE);
		
		return alf;
	}
	
	
	public static Individual<Integer> generateRandomIndividual(int rango){
		List<Integer> repr = new ArrayList<Integer>();
		for(int i = 0; i < rango; i++) 
			repr.add(i % 2 == 0 ? 
					new Random().nextInt(rango - 1) + 1 : 
						(new Random().nextInt(3) + 1) * -1);
			
		Individual<Integer> individual = new Individual<Integer>(repr);
		return individual;
	}
}
