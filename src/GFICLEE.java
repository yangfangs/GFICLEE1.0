import predict.Predict;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static utils.Utils.checkInputFile;
import static utils.Utils.checkOutputFile;

public class GFICLEE {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        boolean printUsage = true;
        if (args.length > 1) {
            if (run(args))
                printUsage = false;
        }

        if (printUsage) {
            System.err.println("Usage: ");
            System.err.println("optional arguments:");
            System.err.println("-i: The input gene set.");
            System.err.println("-o: Result file Path");
            System.err.println("-t: The Species tree with nwick format.");
            System.err.println("-p: Phylogenetic profile.");
            System.err.println("-c: Predicted with multi threads. The default is 1.");
            System.err.println("-m: Choose predict model<'eq','h','l','lh'>. the default model is 'eq'");
        }
    }


    private static boolean run(String[] args) throws ExecutionException, InterruptedException {
        boolean errArgs = false;
        boolean nullArgs = false;

        int argIdx = 0;
        String inputGeneSetPath = null;
        String outputResultPath = null;
        String speciesTreePath = null;
        String profilePath = null;
        String model ="eq";
        int threadNum = 1;

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
            else if (arg.equals("-m"))
                model = args[argIdx++];
            else if (arg.equals("-c"))
                threadNum = Integer.parseInt(args[argIdx++]);
            else {
                System.err.println("Unknown option: " + arg);
                errArgs = true;
            }

        }

        if (inputGeneSetPath == null)
            nullArgs = true;
        if (outputResultPath == null)
            nullArgs = true;
        if (speciesTreePath == null)
            nullArgs = true;
        if (profilePath == null)
            nullArgs = true;
        if (errArgs || nullArgs)
            return false;

        inputGeneSetPath = checkInputFile(inputGeneSetPath);

        speciesTreePath = checkInputFile(speciesTreePath);

        profilePath = checkInputFile(profilePath);

        outputResultPath = checkOutputFile(outputResultPath);


        System.err.print("GFICLEE: Started with arguments:");
        for (String arg : args)
            System.err.print(" " + arg);
        System.err.println();

        long startTime = System.currentTimeMillis();


        Predict gficlee = new Predict(profilePath,
                inputGeneSetPath,
                speciesTreePath,
                outputResultPath,
                model);
        gficlee.getAllSCL();
        gficlee.runPredictMulti(threadNum);

        long endTime = System.currentTimeMillis();
        System.err.println("GFICLEE: Completed successfully");

        System.err.println("Time used: " + (endTime - startTime) / (float)1000 + " Seconds");
        return true;


    }


}
