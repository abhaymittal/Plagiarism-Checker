import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * This class implements a HashMap for the synonyms. The words are taken as keys for 
 * the hash map and the synonyms are given the same integer values (line number that
 * they were present on).
 * @author Abhay Mittal
 */
public class Vocabulary {
    HashMap<String,Integer> vocabularyMap; //hash map for the vocabulary
    int nSyn; //number of synonyms stored (all the synonyms of a word are counted as 1, this variable basically stores the number of words with different meaning)

    /**
     * This is a single argument constructor. It initializes the variables and builds 
     * vocabulary
     * @param filePath: the path of the file containing the synonyms
     */
    public Vocabulary(String filePath) {
	this.nSyn=0;
	this.vocabularyMap= new HashMap<String,Integer>();
	this.buildNewVocabulary(filePath);
    }

    
    /**
     * This method builds a vocabulary HashMap using the file passed as argument.
     * @param filePath: The path of the file containing the synonyms
     */
    public void buildNewVocabulary(String filePath) {
	BufferedReader synReader=null;
	try {
	    synReader= new BufferedReader( new FileReader(filePath)); //Initialize a reader for file
	    String line;
	    int lineCount=0; //Even though the HashMap uses Integer class instead of primitive int, I used int here because I require mutability
	    while((line=synReader.readLine())!=null) {
		line=line.trim(); //trim any trailing or leading whitespaces
		if(line.isEmpty()) //If the line contains nothing, skip to the next line
		    continue;
		String[] synonyms=line.split("[\\s\\p{Punct}]+"); //split based on whitespace and punctuation
		lineCount++;
		for(int i=0;i<synonyms.length;i++) { //Assign  same value to all the synonyms in the hash map
		    this.vocabularyMap.put(synonyms[i],lineCount);
		}
		this.nSyn++; //increase the number of unique words held
	    }
	    synReader.close();
	}
	catch(FileNotFoundException ex) { //If file not found
	    System.out.println("The file "+filePath+" was not found on the system. Please verify if the file exists and run again.");
	    System.exit(1);
	}
	catch(Exception ex) { // in case any other exception occurs
	    System.out.println("An exception occured: "+ex.getMessage());
	    try { //close the reader before exiting
		if(synReader!=null)
		    synReader.close();
	    }
	    catch (IOException exp) {
		System.out.println("An exception occured in closing buffered reader for Vocabulary: "+exp.getMessage());
	    }
	    System.exit(2);
	}
    }

    /**
     * This method encodes a tuple to a string. Each word in the tuple is changed to its corresponding value in the HashMap of the vocabulary. If a word does not exist in the HashMap, it is added to it.
     * @param tuple: The words to encode
     * @return result: An encoding in string form
     */
    public String encode(ArrayDeque<String> tuple) {
	String result="";
	for(Iterator<String> itr=tuple.iterator();itr.hasNext();) {
	    String str=itr.next();
	    if(this.vocabularyMap.get(str)==null) { //If the word is not in the vocabulary, insert it
		this.vocabularyMap.put(str,this.nSyn);
		this.nSyn++;
	    }
	    result+=this.vocabularyMap.get(str)+" ";
	}
	result=result.trim(); //remove the last space from the encoding 
	return result;
    }
}
