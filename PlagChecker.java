import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Iterator;

/**
* The following class implements the plagiarism Checker. My algorithm for this program 
* can be found in README.md. 
* Exit codes: 
* -1 - invalid number of arguments
* 2 - file not found
* 3 - Exception in building tupleset in SourceFile class
* 4 - Exception in hasNextTuple() of TestFile class
* 5 - Exception in buildNewVocabulary() method of Vocabulary class
* @author Abhay Mittal
*/
public class PlagChecker {
    public static void main(String[] args) {
	if(args.length<3 || args.length>4) { // If the number of arguments is less than 3 or more than 4, print usage instructions
	    System.out.println("=============================================================================");
	    System.out.println("Usage: java PlagChecker <synonymFile> <sourceFile> <testFile> [<tupleSize>]");
	    System.out.println("<synonymFile> : File containing the list of synonyms for each word");
	    System.out.println("<sourceFile>  : File which is to be taken as the original copy");
	    System.out.println("<testFile>    : File which has to be tested for plagiarism against <sourceFile>");
	    System.out.println("<tupleSize>   : (Optional) The size of the tuples for comparison. Default size is 3 words.");
	    System.out.println("=============================================================================");
	    System.exit(-1);
	}

	String synonymPath=args[0];
	String sourcePath=args[1];
	String testPath=args[2];
	int tupleSize=3; //default Tuple size
	if(args.length==4)
	    tupleSize=Integer.parseInt(args[3]);
	int matchingTuples=0; 
	int totalTuples=0;
	Vocabulary vocab=new Vocabulary(synonymPath); //Create the vocabulary object
	SourceFile source=new SourceFile(sourcePath,tupleSize); //Object for source file
	source.buildTupleSet(vocab); //Build the hash set for the source file containing all the encodings present in it
	TestFile test=new TestFile(testPath,tupleSize); //Object for test file
	while(test.hasNextTuple()) { //Run till test file has tuples in it
	    totalTuples++;
	    ArrayDeque<String> tuple=test.getNextTuple(); 
	    String enTuple=vocab.encode(tuple); //Encode the tuple
	    if(source.isPresent(enTuple)){
		matchingTuples++; //If the tuple is present in source tuple, increment the matched count
	    }
	    // System.out.println(source.isPresent(enTuple));
	}
	float percentage=((float)matchingTuples)/totalTuples;
	percentage=percentage*100;
	System.out.println(Float.toString(percentage)+"%");
    }
}
