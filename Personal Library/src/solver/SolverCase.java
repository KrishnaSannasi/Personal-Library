package solver;

public interface SolverCase {
	SolverState checkValidity();
	
	SolverCase[] createNextCases();
	
	default void preliminarySolve() {
	
	}
}
