import predict.Predict;

public class GFICLEE {
    public static void main(String[] args) {
        boolean printUsage = true;
        if (args.length > 1) {
            if (run(args))
                printUsage = false;
        }

        if (printUsage) {
            System.err.println("Usage: ");
            System.err.println("optional arguments:");
            System.err.println("-i: The input gene set.");
            System.err.println("-o: Result file Pth");
            System.err.println("-t: The Species tree with nwick format.");
            System.err.println("-p: Phylogenetic profile.");
        }
    }


    private static boolean run(String[] args) {
        boolean errArgs = false;
        boolean nullArgs = false;

        int argIdx = 0;
        String inputGeneSetPath = null;
        String outputResultPath = null;
        String speciesTreePath = null;
        String profilePath = null;

        while (argIdx < args.length && args[argIdx].startsWith("-")) {
            String arg = args[argIdx++];
            if (arg.equals("-i"))
                inputGeneSetPath = args[argIdx++];
            else if (arg.equals("-o"))
                outputResultPath = args[argIdx++];
            else if (arg.equals("-t"))
                speciesTreePath = args[argIdx++];
            else if (arg.equals("-p"))
                profilePath = args[argIdx++];
            else {
                System.err.println("Unknown option: " + arg);
                errArgs = true;
            }

        }

        if(inputGeneSetPath == null)
            nullArgs = true;
        if (outputResultPath == null)
            nullArgs = true;
        if (speciesTreePath == null)
            nullArgs = true;
        if (profilePath == null)
            nullArgs = true;
        if(errArgs || nullArgs)
            return false;


        System.err.print("GIFICLEE: Started with arguments:");
        for (String arg : args)
            System.err.print(" " + arg);
        System.err.println();
        Predict gficlee = new Predict(profilePath,
                inputGeneSetPath,
                speciesTreePath,
                outputResultPath);
        gficlee.getAllSCL();
        gficlee.runPredict();

        System.err.println("GIFCLEE: Completed successfully");
        return true;


    }


}
