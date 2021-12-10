/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.model;

import util.Assert;

import java.util.*;

public class TuringMachineImpl implements TuringMachine {
    protected final static Character SYMBOL_BLANK = '.';  // 40f
    protected final static String STATE_INITIAL = "init";

    protected final Set<TMObserver> observers = new HashSet<>();
    protected final Set<Character> symbols = new HashSet<>() {{
        add(SYMBOL_BLANK);
        add('0');
        add('1');
        add('(');
        add(')');
    }};
    protected final List<Rule> rules = new ArrayList<>();

    public TuringMachineImpl() {

    }

    @Override
    public List<Character> getSymbolList() {
        return new ArrayList<>(symbols);
    }

    @Override
    public char getBlankSymbol() {
        return SYMBOL_BLANK;
    }

    @Override
    public String getInitialState() {
        return STATE_INITIAL;
    }

    @Override
    public int getRuleCount() {
        return rules.size();
    }

    protected Rule getRule(int i) {
        Assert.check(i >= 0 && i < rules.size());

        return rules.get(i);
    }

    @Override
    public String getRuleState(int ruleNumber) {
        return getRule(ruleNumber).initialState();
    }

    @Override
    public String getRuleNewState(int ruleNumber) {
        return getRule(ruleNumber).newState();
    }

    @Override
    public char getRuleSymbol(int ruleNumber) {
        return getRule(ruleNumber).initialSymbol();
    }

    @Override
    public char getRuleNewSymbol(int ruleNumber) {
        return getRule(ruleNumber).replacementSymbol();
    }

    @Override
    public Direction getRuleDirection(int ruleNumber) {
        return getRule(ruleNumber).direction();
    }

    @Override
    public void changeRule(
        int ruleNumber,
        String state,
        char symbol,
        char newSymbol,
        Direction direction,
        String newState
    ) {
        Assert.check(ruleNumber >= 0 && ruleNumber < rules.size());

        rules.set(ruleNumber, new Rule(
            state, newState, symbol, newSymbol, direction
        ));
        onChanged();
    }

    @Override
    public void addRule(
        int ruleNumber,
        String state,
        char symbol,
        char newSymbol,
        Direction direction,
        String newState
    ) {
        Assert.check(ruleNumber >= 0 && ruleNumber <= rules.size());

        rules.add(ruleNumber, new Rule(
            state, newState, symbol, newSymbol, direction
        ));
        onChanged();
    }

    @Override
    public void removeRule(int ruleNumber) {
        Assert.check(ruleNumber >= 0 && ruleNumber < rules.size());

        rules.remove(ruleNumber);
        onChanged();
    }

    @Override
    public void addSymbol(char symbol) {
        symbols.add(symbol);
    }

    @Override
    public void removeSymbol(char symbol) {
        symbols.remove(symbol);
    }

    @Override
    public void addObserver(TMObserver o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(TMObserver o) {
        observers.remove(o);
    }

    protected void onChanged() {
        for (TMObserver observer : observers) {
            observer.notifyChangeToTM(this);
        }
    }
}
