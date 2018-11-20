package iagen;

import java.util.ArrayList;
import java.util.Collection;

public class AritGeneticaDemo {

	private static final int MAS = -1;
	private static final int MENOS = -2;
	private static final int POR = -3;
	private static final int ENTRE = -4;	
	
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
	
	
	
	
}
