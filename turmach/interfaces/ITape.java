/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.interfaces;

public interface ITape<StateType, SymbolType> {
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
    StateType getCurrentState() ;

    /** Get value of the tape at the given position.
     */
    SymbolType getTapeCellSymbol(int position ) ;

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
    void setTapeCell( int position, SymbolType newValue ) ;

    /* Change the position of the configuration.
     *
     */
    void setPosition( int position ) ;

    /** Change the state of the configuration.
     *
     * @requires state != null
     * @param state
     */
    void setCurrentState(StateType state) ;

}
