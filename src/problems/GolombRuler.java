package problems;

import org.kohsuke.args4j.Option;


public class GolombRuler extends AbstractProblem {

    @Option(name = "-m", usage = "Golomb ruler order.", required = false)
    private int m = 10;

    

    @Override
    public void buildModel() {
    	
    	// Build Model here. 
        model = new Model();
        
        //Variables definition
    	IntVar[] X = model.intVarArray("a", m, 0, m*m);
    	//All distances
    	IntVar[] Y = model.intVarArray("b", (m*(m-1))/2, 0, m*m);
    	
    	
    	//Constraints definition
                //for all i, Xi < Xi+1
    	for(int i=0; i<=m-1; i++) {
    		model.arithm(X[i], "<", X[i+1]).post();
    	}
    	        //force the first mark to take the value 0
    	model.arithm(X[0], "=", 0).post();
    	for (int i=0, j=0, k=0, l=0 ; i<=m-1 & j<=m-1 & k<=m-1 & l<=m-1 & i<j & k<j & ((j != l) || (i != k)) ; i++ , j++, k++, j++)
    	{
    		    //2 distinct marks are never at the same distance
    		model.scalar(new IntVar[]{X[i],X[j],X[l],X[k]} , new int[]{1,-1,-1,1}, "!=", 0).post();
    		model.arithm(Y[k], "=", X[i], "-", X[j]).post();
    	}
    	        // All distances have to be different
    	model.allDifferent(Y).post();
    	        //To break the symmetry
    	model.scalar(new IntVar[]{X[1],X[0],X[m-1],X[m-2]} , new int[]{1,-1,-1,1}, "<", 0).post();
    }

    @Override
    public void configureSearch() {
    	
    	// Set search here
    }

    @Override
    public void solve() {
  
    	// Set objective if needed;
        model.getSolver().setObjective(Model.MINIMIZE, X[m-1]);
    	// Solve the instance
    	// Print the solution
        
    }

    public static void main(String[] args) {
        new GolombRuler().execute(args);
    }
}
