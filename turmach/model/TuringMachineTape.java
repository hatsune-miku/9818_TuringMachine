/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.model;

import turmach.interfaces.ITape;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TuringMachineTape<StateType, SymbolType>
    implements ITape<StateType, SymbolType> {

    protected final Map<Integer, SymbolType> tape = new HashMap<>();

    protected final SymbolType blankSymbol;
    protected StateType state;

    protected int cursor = 0;


    public TuringMachineTape(SymbolType blankSymbol, StateType initState) {
        this.blankSymbol = blankSymbol;
        this.state = initState;
    }

    protected int getEnd(TuringMachine.Direction direction) {
        // Sorted indices.
        Integer[] indices = new Integer[tape.size()];
        indices = tape.keySet().toArray(indices);
        Arrays.sort(
            indices,
            (i0, i1) -> direction == TuringMachine.Direction.LEFT
                ? i0 - i1
                : i1 - i0
        );

        for (Integer index : indices) {
            if (tape.get(index) != blankSymbol) {
                return index;
            }
        }
        return -1;
    }

    // The index of the `leftest` non-blank symbol or -1.
    @Override
    public int getLeftEnd() {
        return getEnd(TuringMachine.Direction.LEFT);
    }

    // The index of the `rightest` non-blank symbol or -1.
    @Override
    public int getRightEnd() {
        return getEnd(TuringMachine.Direction.RIGHT);
    }

    @Override
    public int getCurrentPosition() {
        return cursor;
    }

    @Override
    public StateType getCurrentState() {
        return state;
    }

    @Override
    public SymbolType getTapeCellSymbol(int position) {
        return tape.getOrDefault(position, blankSymbol);
    }

    @Override
    public void setTapeCell(int position, SymbolType newValue) {
        tape.put(position, newValue);
    }

    @Override
    public void setPosition(int position) {
        cursor = position;
    }

    @Override
    public void setCurrentState(StateType state) {
        this.state = state;
    }
}
