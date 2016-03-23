package solver;

import java.util.LinkedList;

public final class Solver {
	private static final LinkedList<SolverCase> SOLUTIONS = new LinkedList<>();
	
	public static final SolverCase[] solve(SolverCase problem) {
		SolverState s = problem.checkValidity();
		
		switch(s) {
			case PASS:
				SOLUTIONS.add(problem);
				break;
			case FAIL:
				return null;
			case CONTINUE:
				for(SolverCase newProblem: problem.createNextCases()) {
					s = newProblem.checkValidity();
					solve(newProblem);
				}
				break;
		}
		
		return SOLUTIONS.toArray(new SolverCase[SOLUTIONS.size()]);
	}
}
