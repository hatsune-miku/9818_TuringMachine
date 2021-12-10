/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.model;

import java.util.List;

public interface TuringMachine {

	public static enum  Direction{ LEFT, RIGHT } ;

	// Accessors
	
	/** Return a list of all symbols (characters) that this machine uses */
	List<Character> getSymbolList() ;
	
	/** Get the blank symbol. New tapes will contain only this symbol */
	char getBlankSymbol() ;
	
	String getInitialState() ;
	
	/** Return the number of rules.
	 * 
	 * @return
	 */
	int getRuleCount() ;
	
	/** Return the source state of a rule.
	 * 
	 * @param ruleNumber
	 * 
	 * @require 0 <= ruleNumber && rulNumber < getRuleCount()
	 * @return the rule's source state.
	 */
	String getRuleState( int ruleNumber ) ;

	/** Return the trigger symbol of a rule.
	 * 
	 * @param ruleNumber
	 * 
	 * @require 0 <= ruleNumber && rulNumber < getRuleCount()
	 * @return the rule's trigger symbol.
	 */
	char getRuleSymbol( int ruleNumber ) ;
	
	/** Return the replacement symbol of a rule.
	 * 
	 * @param ruleNumber
	 * 
	 * @require 0 <= ruleNumber && rulNumber < getRuleCount()
	 * @return the rule's replacement symbol.
	 */
	char getRuleNewSymbol( int ruleNumber ) ;
	
	Direction getRuleDirection( int ruleNumber ) ;
	
	/** Return the target state of a rule.
	 * 
	 * @param ruleNumber
	 * 
	 * @require 0 <= ruleNumber && rulNumber < getRuleCount()
	 * @return the rule's target state.
	 */
	String getRuleNewState( int ruleNumber ) ;
	
	// Mutators
	
	/** 
	 * @param ruleNumber
	 * @requires 0 <= ruleNumber && rulNumber < getRuleCount()
	 *   and sourceState != null and direction != null and newState!=null
	 * @ensures The rule with this number will be as specified in
	 * the parameters. All other rules are unchanged and the number
	 * of rules is unchanged.
	 */
	void changeRule( int ruleNumber,
			String sourceState,
			char triggerSymbol,
			char replacementSymbol,
			Direction direction,
			String newState ) ;
	
	/** 
	 * @param ruleNumber
	 * @requires 0 <= ruleNumber && rulNumber <= getRuleCount()
	 *   and sourceState != null and direction != null and newState!=null
	 * @ensures The rule with this number will be as specified in
	 * the parameters. All rule before it are unchanged.
	 * All rules originally at or after ruleNumber are unchanged but their
	 * rule number is increased by one. The number of rules will increase
	 * by one.
	 */
	void addRule( int ruleNumber,
			String sourceState,
			char triggerSymbol,
			char replacementSymbol,
			Direction direction,
			String newState ) ;
	
	/** 
	 * @param ruleNumber
	 * @requires 0 <= ruleNumber && rulNumber < getRuleCount()
	 * @ensures The rule with this number will be deleted.
	 * All rules before it are unchanged.
	 * All rules after this one are unchanged, but they will
	 * have their rule number decreased by one.
	 * The number of rules will decrease by one.
	 */
	void removeRule( int ruleNumber ) ;
	
	/** Add one symbol to the list of symbols.
	 * @ensures that the list of symbols includes `symbol` and
	 * also all symbols that were on the list before.  If
	 * `symbol` was already on the list, there should be no change.
	 * @param symbol
	 */
	void addSymbol( char symbol ) ;
	
	/** Remove one symbol from the list of symbols.
	 * 
	 * @param symbol
	 * @ensures that `symbol` is not on the list of symbols but all other
	 * symbols that were on the list are still on the list. If `symbol` was
	 * not originally on the list, then it is removed from the list.
	 */
	void removeSymbol( char symbol ) ;

	/** Add an observer object to the list of observers.
	 * Note that observers will be notified after any change
	 * to this object
	 * @requires o != null
	 * @ensures that the list of observes will be the same as before except
	 * that it will contain `o`.
	 */
	void addObserver( TMObserver o ) ;
	
	/** Add an observer object to the list of observers.
	 * Note that observers will be notified after any change
	 * to this object
	 * @ensures that the list of observers will be the same as before except
	 * that it will not contain `o`.
	 */
	void deleteObserver( TMObserver o ) ;
	
	static interface TMObserver {
		void notifyChangeToTM( TuringMachine tm ) ;
	}
}