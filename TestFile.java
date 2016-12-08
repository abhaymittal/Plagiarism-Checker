import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Class for the test file. This file is tested against the source file. This file is not completely read in one go to save memory.
 * @author Abhay Mittal
 */
public class TestFile {
    String filePath; //path of the test file
    int tupleSize;//number of words in tuple
    BufferedReader inputReader; //reader for the input file
    String[] currentLineWords; //store the current line read
    int nextWordIndex; //the next unread word
    ArrayDeque<String> tuple; //the current tuple

    /**
     * Single argument constructor
     * This method initializes the variables by their default values except for the ones
     * that are given
     * @param filePath: The path of the test file
     */
    public TestFile(String filePath) { 
	this.tupleSize=3;
	this.tuple=new ArrayDeque<String>(this.tupleSize);
	this.inputReader=null;
	setFilePath(filePath);
	this.nextWordIndex=0;
	this.currentLineWords=null;
    }

    /**
     * A two argument constructor
     * This method initializes the variables by their default values except for the ones
     * that are given
     * @param filePath: The path of the test file
     * @param tplSize: The tuple size
     */
    public TestFile(String filePath, int tplSize) {
	this.tupleSize=tplSize;
	this.tuple=new ArrayDeque<String>(this.tupleSize);
	this.inputReader=null;
	setFilePath(filePath);
	this.nextWordIndex=0;
	this.currentLineWords=null;
    }

    /**
     * This method sets the path of the test file and checks if the file exists
     * @param filePath: The path of the test file
     */
    public void setFilePath(String filePath)  {
	this.inputReader=null;
	try {
	    this.inputReader=new BufferedReader(new FileReader(filePath));
	}
	catch(FileNotFoundException ex) {
	    System.out.println("The file "+filePath+" was not found on the system. Please verify if the file exists and run again.");
	    System.exit(2);
	}
	
	this.filePath=filePath; //set the file path if the file exists
    }

    /**
     * This method checks if an unread tuple is present in the test file 
     * @return true, if an unread tuple is present, else false
     */ 
    public boolean hasNextTuple() {
	//if there are unread words in the current file
	if(this.currentLineWords!=null && (this.nextWordIndex<this.currentLineWords.length)) {
	    return true;
	}
	else { //no unread words in the current file
	    String line=null;
	    try{
		while((line=this.inputReader.readLine())!=null) { //skip through the empty lines
		    line=line.trim();
		    if(!line.isEmpty())
			break;
		}
	    }
	    catch(Exception ex) {
		System.out.println("Exception occured: "+ex.getMessage());
		try { //close the reader for file
		    if(inputReader!=null)
			inputReader.close();
		}
		catch (IOException exp) {
		    System.out.println("An exception occured in closing buffered reader for Test file: "+exp.getMessage());
		}
		System.exit(4);	   
	    }
	    
	    if(line==null) //if we reach the end of the file
		return false;
	    else { // a non-empty line is read
		this.currentLineWords=line.split("[\\s\\p{Punct}]+");//split around punctuation and whitespaces
		this.nextWordIndex=0;
		if(this.tuple.size()==0&&this.currentLineWords.length<this.tupleSize) { //if the current tuple does not have any words (initial case) and the number of words in the first line are less than the tuple size (ASSUMPTION)
		    return false;
		}
		return true;
	    }
	}
    }


    /**
     * This method gets the next unread tuple from the test file
     * @return tuple: The next unread tuple
     */
    public ArrayDeque<String> getNextTuple() {
	//CALL THIS FUNCTION ONLY AFTER CALL TO hasNextTuple(). IT MAY GIVE AN ERROR OTHERWISE.
	if(this.tuple.size()<this.tupleSize){ //if current tuple has lesser words than the tuple size, add words
	    for(int i=0;i<this.tupleSize;i++) {
		this.tuple.addLast(this.currentLineWords[this.nextWordIndex]);
		this.nextWordIndex++;
	    }
	}
	else {//remove word from the beginning and add a new word at the end
	    this.tuple.removeFirst();
	    this.tuple.addLast(this.currentLineWords[this.nextWordIndex]);
	    this.nextWordIndex++;
	}
	return this.tuple;
    }
}
