# Plagiarism detector
### Author: Abhay Mittal

There are four classes: PlagChecker, SourceFile, TestFile, Vocabulary

The main function is present in PlagChecker. 
Some sample inputs are provided in the input directory.

-------------------------------------------------------------------------------
### Usage:

java PlagChecker <synonymFile> <sourceFile> <testFile> [<tupleSize>]

<synonymFile> : File containing the list of synonyms for each word
<sourceFile>  : File which is to be taken as the original copy
<testFile>    : File which has to be tested for plagiarism against <sourceFile>
<tupleSize>   : (Optional) The size of the tuples for comparison. Default size is 3 words.

--------------------------------------------------------------------------------
### Algorithm:
1. Read the synonyms file line by line and build a HashMap with words as keys and the line numbers as values, thus all the words which are on the same line  (synonyms) get the same value.
2. Implement an encoding scheme which takes a tuple as an input and returns a string encoding for the tuple. Basically, replace each word in the tuple by its value from the HashMap in step 1 and then separate all the numbers by spaces and use it as encoding.
3. Implement a HashSet for the source file. Read all the tuples in the source file, encode each tuple and then put it into a Hash set. Thus the HashSet contains all the unique tuples present in the source file.
4. For each tuple present in the test file, first encode it and then check if that encoding is present in the source file. If it is, then increment the count of the number of matches.
5. Calculate the percentage by dividing the number of matches by the total number of tuples present in the test file.

__Note__: Punctuations are ignored, that is, they are not taken into account while computing plagiarism

--------------------------------------------------------------------------------
### Assumptions
1. The number of words in first line of the test file must be atleast equal to the tuple size. It was necessary to do this for the hasNext() method in the TestFile class.

--------------------------------------------------------------------------------
### Limitations
1. The program can not be directly scaled to multiple threads as I have used classes like HashMap and ArrayDeque which require external synchronization methods.