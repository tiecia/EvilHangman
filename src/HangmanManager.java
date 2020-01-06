/**
 * CSE 143 Evil Hangman Manager
 *
 * @author Tyler Ciapala-Hazlerig
 * @version 1.0
 */

import java.util.*;

public class HangmanManager {
    /**
     * The words that the game can choose from at the current state of the game.
     */
    private Set<String> currentDictionary;

    /**
     * A list of all the previously guessed letters.
     */
    private Set<Character> guesses;

    /**
     * The number of wrong guesses the user can make at the current state of the game.
     */
    private int wrongGuessesLeft;

    /**
     * The word pattern currently displayed to the user
     */
    private String pattern;


    /**
     * Creates a new instance of {@link HangmanManager}
     *
     * @param dictionary The list of all possible words to be used in in this game of hangman
     * @param length The length of word the player is trying to guess
     * @param max The maximum number of wrong guesses the user is allowed to make.
     *
     * @throws IllegalArgumentException if length is less than 1
     * @throws IllegalArgumentException if max is less than 0
     *
     * @return a new instance of {@link HangmanManager}
     * @since 1.0
     */
    public HangmanManager(Collection<String> dictionary, int length, int max){
        if(length < 1){
            throw new IllegalArgumentException("The given word length cannot be less than 1");
        }
        if(max < 0) {
            throw new IllegalArgumentException("Max cannot be less than 0.");
        }
        this.wrongGuessesLeft = max;
        this.currentDictionary = new TreeSet<String>();
        for(String s: dictionary){
            if(s.length() == length){
                currentDictionary.add(s);
            }
        }
        guesses = new TreeSet<Character>();
        pattern = "-";
        for(int i = 1; i<length; i++){
            pattern += "-";
        }
    }

    /**
     * Gets the current dictionary with all possible words at the current game state
     *
     * @return a {@link Set} of the current dictionary
     * @since 1.0
     */
    public Set<String> words(){
        return this.currentDictionary;
    }

    /**
     * Gets the wrong guesses left at the current game state
     *
     * @return the number of wrong guesses the player has left.
     * @since 1.0
     */
    public int guessesLeft(){
        return this.wrongGuessesLeft;
    }

    /**
     * @return a {@link Set} of already guessed letters
     * @since 1.0
     */
    public Set<Character> guesses(){
        return this.guesses;
    }

    /**
     * Gets the current pattern to be displayed to the user at this point in the game.
     *
     * @throws IllegalStateException if the dictionary is empty
     *
     * @return a {@link String} with the pattern to be displayed to the user.
     * @since 1.0
     */
    public String pattern(){
        if(this.currentDictionary.isEmpty()){
            throw new IllegalStateException("Empty Dictionary");
        }
        return this.pattern;
    }

    /**
     * Registers a users guess and updates the game state based on that guess.
     *
     * @param guess the letter the user guessed
     *
     * @return the number of occurrences the guessed letter occurs in the correct word.
     * @since 1.0
     */
    public int record(char guess){
        checkExceptions(guess);
        guesses.add(guess);
        Map<String, Set<String>> map = new TreeMap<>();
        String largestSetPattern = "";
        for(String word : currentDictionary){
            String pattern = "";
            for(int i = 0; i<word.length(); i++){   //Iterates through the word and creates the pattern for that word.
                if(word.charAt(i) == guess){
                    pattern += guess;
                } else if (word.charAt(i) == this.pattern.charAt(i)) {
                    pattern += this.pattern.charAt(i);
                } else {
                    pattern += "-";
                }
            }
            if(!map.containsKey(pattern)){
                map.put(pattern, new TreeSet<String>());
            }
            map.get(pattern).add(word);
            if(largestSetPattern.equals("") || map.get(pattern).size() > map.get(largestSetPattern).size()){
                largestSetPattern = pattern;
            }
        }
        this.pattern = largestSetPattern;
        this.currentDictionary = map.get(largestSetPattern);
        return count(largestSetPattern, guess);
    }

    /**
     * Helper method that checks the exceptions for the {@code record} operation.
     *
     * @param guess the guess guessed by the user.
     * @since 1.0
     */
    private void checkExceptions(char guess) {
        if (guessesLeft() < 1)
            throw new IllegalStateException("Out of guesses");
        if (currentDictionary.size() < 1)
            throw new IllegalStateException("Dictionary is empty");
        if (guesses.contains(guess))
            throw new IllegalArgumentException("This letter had been guessed already");
    }

    /**
     * Helper method that gets the number of occurrences the guessed letter occurs in the correct word.
     *
     * @param largestSetPattern the pattern to be used in the new dictionary, also the largest set of words.
     * @param guess the letter guessed by the user
     *
     * @return the number of occurrences the guessed letter occurs in the correct word.
     * @since 1.0
     */
    private int count(String largestSetPattern, char guess){
        int count = 0;
        for(char c : largestSetPattern.toCharArray()){
            if(c == guess){
                count++;
            }
        }
        if(count == 0){
            wrongGuessesLeft--;
        }
        return count;
    }
}