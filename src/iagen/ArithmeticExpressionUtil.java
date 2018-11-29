package iagen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import aima.core.search.framework.problem.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class ArithmeticExpressionUtil {
	
	private static final int ADD = -1;
	private static final int SUB = -2;
	private static final int MUL = -3;
	private static final int DIV = -4;
	
	private static final int individualLength = 11;
	private static int objectiveResult;
	
	
	public static FitnessFunction<Integer> getFitnessFunction(){
		return new ArithmeticExpressionFitnessAlgo();
	}
	
	public static GoalTest getGoalTest() {
		return new ArithmeticExpressionGoalTest();		
	}
	
	public static void setObjectiveResult(int objective) {
		objectiveResult = objective;
	}
	
	
	public static class ArithmeticExpressionFitnessAlgo implements FitnessFunction<Integer> {
		@Override
		public double apply(Individual<Integer> individual) {
			return 1d /(1 + Math.abs((objectiveResult -  calculateExpression(individual.getRepresentation()))));
		}	
	}
	
	public static class ArithmeticExpressionGoalTest implements GoalTest{

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
			
			if(op==ADD)
				sum += num;
			else if(op == SUB)
				sum -= num;
			else if(op == MUL)
				sum *= num;
			else if(op == DIV)
				sum /= num;
		}
		return sum;
	}

	public static Collection<Integer> getFiniteAlphabet() {
		Collection<Integer> alf = new ArrayList<Integer>();

		for (int i = 1; i <= 10; i++) 
			alf.add(i);
		
		alf.add(25);
		alf.add(50);

		alf.add(ADD);
		alf.add(SUB);
		alf.add(MUL);
		alf.add(DIV);
		
		return alf;
	}
	
	
	public static Individual<Integer> generateRandomIndividual(){
		List<Integer> repr = new ArrayList<Integer>();
		Collection<Integer> operands = getFiniteAlphabet();
		
		operands.remove(ADD);
		operands.remove(SUB);
		operands.remove(MUL);
		operands.remove(DIV);
		
		for(int i = 0; i < individualLength ; i++) 
			repr.add(i % 2 == 0 ? 
					((List<Integer>)operands).get(new Random().nextInt(operands.size())) : 	
						(new Random().nextInt(3) + 1) * -1);
			
		Individual<Integer> individual = new Individual<Integer>(repr); 
		repr = individual.getRepresentation();
		return individual;
	}

	public static String getExpression(Individual<Integer> bestIndividual) {
		String out="";
		for(Integer i : bestIndividual.getRepresentation()) {
			if(i==ADD)
				out += " + ";
			else if(i==SUB)
				out += " - ";
			else if(i==MUL)
				out += " * ";
			else if(i==DIV)
				out += " / ";
			else
				out += i;
		}
		
		out += " = " + calculateExpression(bestIndividual.getRepresentation());
		
		return out;
	}

}
