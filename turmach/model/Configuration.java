/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.model;

/** A Configuration consist of
 * 
 * * A Turing machine, t, represented by an object of type TuringMachine
 * * A tape, which is a function from int to char
 * * A current position, which is an int
 * * A current state, which is a String
 * 
 * Note that the characters on the tape do not need to be
 * in the t's symbol list and the current state
 * does not need to be a state that is mentioned in the t's
 * list of rules.
 * 
 * This object is observable and it will notify its observers of
 * any change to it or to its underlying TuringMachine object.
 * 
 * @author theo
 *
 */
public interface Configuration extends TuringMachine {

	
	// Accessors
	
	/** Get the left end of the interesting part of the tape.
	 * @ensures The result should be such that
	 *      getTapeCellSymbol(i) = getBlankSymbol(),
	 *      for all i less than result
	 *  and that getCurrentPosition() is greater or equal to the result. */
	int getLeftEnd() ;
	
	/** Get the right end of the interesting part of the tape.
	 * @ensures The result should be such that
	 *     getTapeCellSymbol(i) = getDefaultSymbol(),
	 *     for all i greater than result
	 *  and at getCurrentPosition() is less or equal to the result. */
	int getRightEnd() ;
	
	/** Get the current position of the read/write head in this configuration.
	 */
	int getCurrentPosition() ;
	
	/** Return the current state of the configuration. */
	String getCurrentState() ;
	
	/** Get value of the tape at the given position. 
	 */
	char getTapeCellSymbol( int position ) ;
	
	/** See assignment sheet. */
	boolean canAdvance() ;
	
	// Mutators
	
	/** Change to any next configuration
	 * Requires canAdvance() ;
	 * Ensures: the final state of this object is a next configuration 
	 *         of its initial state.
	 */
	void advance() ;
	
	/** Change one cell of the tape
	 * 
	 * @param position
	 * @param newValue
	 * 
	 * @ensures that the value of the tape at `position` is `newValue`
	 * and that for all `i`, 
	 *       if `i != position`
	 *       then the value of the tape at position `i` is the same
	 *       as the value of the old tape at position `i`
	 */
	void setTapeCell( int position, char newValue ) ;
	
	/* Change the position of the configuration.
	 * 
	 */
	void setPosition( int position ) ;
	
	/** Change the state of the configuration.
	 * 
	 * @requires state != null
	 * @param state
	 */
	void setCurrentState(String state) ;	
	
	/** Add an observer object to the list of observers.
	 * Note that observers will be notified after any change
	 * to this object.
	 * @requires o != null
	 * @ensures that the list of observes will be the same as before except
	 * that it will contain `o`.
	 */
	void addConfigurationObserver( ConfigurationObserver o ) ;
	
	/** Remove an observer
	 * @ensures that the list of observers will be the same as before except
	 * that it will not contain `o`.
	 */
	void deleteConfigurationObserver( ConfigurationObserver o ) ;

	
	static interface ConfigurationObserver {
		void notifyChangeToConfiguration( Configuration config ) ;
	}
}