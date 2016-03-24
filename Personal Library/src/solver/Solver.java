package solver;

import java.util.LinkedList;

public final class Solver {
	private static final LinkedList<SolverCase> SOLUTIONS = new LinkedList<>();
	
	public static final SolverCase[] solve(SolverCase problem) {
		return solve(problem , false);
	}
	
	public static final SolverCase[] solve(SolverCase problem , boolean findFirstOnly) {
		problem.preliminarySolve();
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
					SolverCase[] solutions = solve(newProblem , findFirstOnly);
					if(solutions != null && solutions.length != 0 && findFirstOnly)
						return solutions;
				}
				break;
		}
		
		return SOLUTIONS.toArray(new SolverCase[SOLUTIONS.size()]);
	}
}
