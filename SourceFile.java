import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.Iterator;
/**
 * The following class represents the source file which is used to test the others
 * @author Abhay Mittal
 */

public class SourceFile {
    String filePath; //the path of the file
    int tupleSize; // the number of words in a tuple
    HashSet<String> tupleSet; //All the encodings present in the file

    /**
     * A single argument constructor
     * This method initializes the variables by their default values except for the ones
     * that are given
     * @param filePath: The path of the source file
     */
    public SourceFile(String filePath) {
	this.tupleSet=new HashSet<String>();
	this.tupleSize=3;
	setFilePath(filePath);
    }
    /**
     * A two argument constructor
     * This method initializes the variables by their default values except for the ones
     * that are given
     * @param filePath: The path of the source file
     * @param tplSize: The tuple size
     */
    public SourceFile(String filePath,int tplSize) {
	this.tupleSet=new HashSet<String>();
	this.tupleSize=tplSize;
	setFilePath(filePath);
    }

    /**
     * This method sets the path of the source file and checks if the file exists
     * @param filePath: The path of the source file
     */
    public void setFilePath(String filePath) {
	try {
	    new FileReader(filePath); // check the file
	}
	catch(FileNotFoundException ex) { //if the file not present
	    System.out.println("The file "+filePath+" was not found on the system. Please verify if the file exists and run again.");
	    System.exit(2);
	}
	this.filePath=filePath; //set the file path
    }

    /**
     * This method builds a HashSet for the source file tuples using the vocabulary encodings.
     * @param synonyms: A vocabulary object containing the synonyms to be used
     */
    public void buildTupleSet(Vocabulary synonyms) {
	BufferedReader sourceReader=null;
	try {
	    sourceReader=new BufferedReader(new FileReader(this.filePath));//initialize the reader
	    ArrayDeque<String> tuple=new ArrayDeque<String>();
	    String line=null;
	    while((line=sourceReader.readLine())!=null){
		line=line.trim(); //trim any whitespaces
		if(line.isEmpty()) //skip if the line is empty
		    continue;
		String[] words=line.split("[\\s\\p{Punct}]+"); //split by whitespace and punctuation
		for(int nextWordId=0;nextWordId<words.length;) {
		    if(tuple.size()<this.tupleSize) { //if the tuple object contains lesser elements than the tupleSize, we don't have to remove anything
			int size=tuple.size();
			for(int i=0; (i<this.tupleSize-size) && (i<words.length);i++) {
			    tuple.addLast(words[nextWordId]);
			    nextWordId++;
			}
			if(tuple.size()==this.tupleSize) {
			    String enTuple=synonyms.encode(tuple);//encode the tuple
			    this.tupleSet.add(enTuple);
			}
		    }
		    else { //remove the first word in the tuple and add the new word in the end
			tuple.removeFirst();
			tuple.addLast(words[nextWordId]);
			nextWordId++;
			String enTuple=synonyms.encode(tuple);
			this.tupleSet.add(enTuple);
		    }
		}
	    }

			       
	}
	catch(Exception ex) { //catch any exception
	    System.out.println("An exception occured while building tuple set: "+ex.getMessage());
	    ex.printStackTrace();
	    try { //close the reader for file
		if(sourceReader!=null)
		    sourceReader.close();
	    }
	    catch (IOException exp) {
		System.out.println("An exception occured in closing buffered reader for Source file: "+exp.getMessage());
	    }
	    System.exit(3);
	}
    }


    /**
     * This method checks if a tuple is present in the source file
     * @param enTuple: encoded string of the tuple
     * @return true if tuple is present in source file, else false
     */
    public boolean isPresent(String enTuple)  {
	return this.tupleSet.contains(enTuple);
    }
    
}
