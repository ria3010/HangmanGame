/*
 * Picture.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */
/**
 * This program is a word guessing game using the Scanner class
 *
 * @author Ria Lulla
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Picture {

    static int soManyCorrectGuesses = 0;
    static int numberOfTries = 9;   // number of guesses
    static int totalTries = 9;
    static int MAX_SIZE = 10;       // max size of the words file

    static String[] wordArray = new String[MAX_SIZE];

    /**
     * Scans he words from words.txt and selects a random word using random class
     *
     * @param wordScanner scanner from words.txt
     * @param argument    arg[1] for picture.txt
     * @return
     */

    private static void scanAndGenerateRandomWords( Scanner wordScanner,String argument ) throws FileNotFoundException {

        Random random = new Random();
        Vector wordVector = new Vector();
        int lengthOfwordArray = 0;
        //scan each word from words.txt and add to string array - wordArray
        while ( wordScanner.hasNextLine() ) {
            String input = wordScanner.nextLine();
            wordArray [ lengthOfwordArray ] = input;
            lengthOfwordArray ++;
        }
        //play till the number of guesses are not zero and the words.txt has unguessed words
        while ( numberOfTries != 0 ) {
            int randomInt = random.nextInt( lengthOfwordArray );
            if ( !wordVector.contains( wordArray[randomInt] ) ) {
                //vector to keep track of visited words so as to not select them again
                wordVector.add( wordArray[randomInt] );
                soManyCorrectGuesses = 0;
                StringBuilder sb = new StringBuilder();
                //string appended to string Builder
                sb.append( wordArray[randomInt] );
                playHangman( argument, sb.toString() );


            }
            //if vector consists of all the words in the words.txt, break
            else if ( wordVector.size() == lengthOfwordArray ) {
                System.out.println( "No more words left to guess I hope you enjoyed the game, bye!" );
                break;
            }
        }

    }
    /**
     * Method to scan through the picture file and scan user input
     *
     * @param arg       picture.txt as argument
     * @param inputStr  each input string randomly selected
     * @return
     */

    private static void playHangman( String arg, String inputStr ) throws FileNotFoundException {
        //scan each character using delimiter ""
        Scanner pictureScanner = new Scanner( new File( arg ) ).useDelimiter("");
        StringBuilder userEnteredString = new StringBuilder();
        StringBuilder patternStr = new StringBuilder();
        String copyOfPatternString = new String();

        while ( pictureScanner.hasNextLine() ) {
            patternStr.append( pictureScanner.nextLine() );
            patternStr.append( "\n" ); //appends new line
        }
        copyOfPatternString = patternStr.toString(); //copy of entire pattern from picture.txt
        //convert input string to char array to compare
        char[] stringToCharArray = inputStr.toCharArray();
        //print first ASCII pattern
        printASCIIPattern(copyOfPatternString, stringToCharArray.length + 1);

        for ( int k = 0; k < totalTries; k++ ) {
            if ( numberOfTries != 0 ) {
                if ( soManyCorrectGuesses != stringToCharArray.length ) {
                    System.out.println( " Guess the letter : " );
                    Scanner charScanner = new Scanner( System.in );
                    char c = charScanner.next().charAt(0);
                    int printEveryXthLetter = 0;
                    //if the letter is correctly guessed
                    if ( stringToCharArray[soManyCorrectGuesses] == c ) {
                        for ( int j = soManyCorrectGuesses; j < stringToCharArray.length; j++ ) {
                            //calculating the printEveryXthLetter value
                            printEveryXthLetter = ( (stringToCharArray.length - 1) - soManyCorrectGuesses ) + 1;
                            soManyCorrectGuesses = soManyCorrectGuesses + 1;
                            printASCIIPattern( copyOfPatternString, printEveryXthLetter );
                            System.out.println( "Correct guess, tries left : " + numberOfTries );
                            userEnteredString.append( stringToCharArray[soManyCorrectGuesses - 1] );
                            System.out.println( "Letters you have guessed correctly so far : "+ userEnteredString );
                            System.out.println();
                            break;
                        }
                    } else {

                        numberOfTries--;
                        printEveryXthLetter = ( ( stringToCharArray.length ) - soManyCorrectGuesses ) + 1;
                        printASCIIPattern( copyOfPatternString, printEveryXthLetter );
                        System.out.println( "Wrong guess, tries left : " + numberOfTries );
                        System.out.println( "Letters you have guessed correctly so far : " + userEnteredString );
                        System.out.println( );
                    }
                }
            }

        }
        /*if number of tries is not zero and input Array is equal to correct guesses
         then restart new game with new word*/
        if ( soManyCorrectGuesses == stringToCharArray.length && numberOfTries != 0 ) {
            System.out.println( "You have guessed the word correctly. Play again .. " );
            System.out.println( );
            System.out.println( "----NEW ROUND----" );
            System.out.println( "Tries left : " + numberOfTries );
        } else {
            System.out.println( "You have exhausted the number of tries. See you later!!" );
        }

    }

    /**
     * Prints the ASCII picture according to the printEveryXthLetter
     *
     * @param patternStr          pattern string scanned from Picture.txt
     * @param printEveryXthLetter Xth letter to be printed
     * @return
     */
    private static void printASCIIPattern( String patternStr, int printEveryXthLetter ) {
        char[] arr = patternStr.toCharArray();
        int counter = 0;
        for ( int i = 0; i < arr.length; i++ ) {
            counter++;
            //add '*' to every non Xth letter to hide the pattern and ignore new line character
            if ( counter % printEveryXthLetter != 0 && arr[i] != '\n' ) {
                arr[i] = '.';
            }
        }
        //print array
        for ( int k = 0; k < arr.length; k++ ) {
            System.out.print( arr[k] );
        }

    }
    /**
     * Main program call : scans and generates random words
     *
     * @param args command line args[0] - words.txt and args[1] - picture.txt
     */

    public static void main( String[] args ) throws FileNotFoundException {
        System.out.println( "Reading from words file : " + args[0] );
        Scanner wordScanner = new Scanner( new File( args[0] ) );
        scanAndGenerateRandomWords( wordScanner, args[1] );
    }

}