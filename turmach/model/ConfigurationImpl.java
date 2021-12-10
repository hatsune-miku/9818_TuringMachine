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
import util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigurationImpl implements Configuration {
    protected final TuringMachine   tmDelegate;

    protected final Set<ConfigurationObserver>
                                    observers = new HashSet<>();

    protected final ITape<String, Character>
                                    tape;

    public ConfigurationImpl(TuringMachine machine) {
        Assert.check(machine != null);

        this.tmDelegate = machine;
        this.tape = new TuringMachineTape<>(
            machine.getBlankSymbol(),
            machine.getInitialState()
        );
    }

    ////////////////////////////////////////////

    @Override
    public List<Character> getSymbolList() {
        return tmDelegate.getSymbolList();
    }

    @Override
    public char getBlankSymbol() {
        return tmDelegate.getBlankSymbol();
    }

    @Override
    public String getInitialState() {
        return tmDelegate.getInitialState();
    }

    @Override
    public int getRuleCount() {
        return tmDelegate.getRuleCount();
    }

    @Override
    public String getRuleState(int ruleNumber) {
        return tmDelegate.getRuleState(ruleNumber);
    }

    @Override
    public char getRuleSymbol(int ruleNumber) {
        return tmDelegate.getRuleSymbol(ruleNumber);
    }

    @Override
    public char getRuleNewSymbol(int ruleNumber) {
        return tmDelegate.getRuleNewSymbol(ruleNumber);
    }

    @Override
    public Direction getRuleDirection(int ruleNumber) {
        return tmDelegate.getRuleDirection(ruleNumber);
    }

    @Override
    public String getRuleNewState(int ruleNumber) {
        return tmDelegate.getRuleNewState(ruleNumber);
    }

    @Override
    public void changeRule(int ruleNumber, String sourceState, char triggerSymbol, char replacementSymbol, Direction direction, String newState) {
        tmDelegate.changeRule(ruleNumber, sourceState, triggerSymbol, replacementSymbol, direction, newState);
    }

    @Override
    public void addRule(int ruleNumber, String sourceState, char triggerSymbol, char replacementSymbol, Direction direction, String newState) {
        tmDelegate.addRule(ruleNumber, sourceState, triggerSymbol, replacementSymbol, direction, newState);
    }

    @Override
    public void removeRule(int ruleNumber) {
        tmDelegate.removeRule(ruleNumber);
    }

    @Override
    public void addSymbol(char symbol) {
        tmDelegate.addSymbol(symbol);
    }

    @Override
    public void removeSymbol(char symbol) {
        tmDelegate.removeSymbol(symbol);
    }

    @Override
    public void addObserver(TMObserver o) {
        tmDelegate.addObserver(o);
    }

    @Override
    public void deleteObserver(TMObserver o) {
        tmDelegate.deleteObserver(o);
    }

    ////////////////////////////////////////////

    @Override
    public int getLeftEnd() {
        return tape.getLeftEnd();
    }

    @Override
    public int getRightEnd() {
        return tape.getRightEnd();
    }

    @Override
    public int getCurrentPosition() {
        return tape.getCurrentPosition();
    }

    @Override
    public String getCurrentState() {
        return tape.getCurrentState();
    }

    @Override
    public char getTapeCellSymbol(int position) {
        return tape.getTapeCellSymbol(position);
    }

    @Override
    public void setTapeCell(int position, char newValue) {
        tape.setTapeCell(position, newValue);
        onChanged();
    }

    @Override
    public void setPosition(int position) {
        tape.setPosition(position);
        onChanged();
    }

    @Override
    public void setCurrentState(String state) {
        tape.setCurrentState(state);
        onChanged();
    }

    ////////////////////////////////////////////

    // fail: -1
    protected int findApplicableRuleIndex() {
        final int ruleCount = getRuleCount();
        for (int i = 0; i < ruleCount; ++i) {
            // r's init state == config current state?
            // r's init symbol == c's tape at c's position?
            if (getRuleState(i).equals(getCurrentState())
                && getRuleSymbol(i) == getTapeCellSymbol(getCurrentPosition())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean canAdvance() {
        return findApplicableRuleIndex() != -1;
    }

    @Override
    public void advance() {
        final int ruleIndex = findApplicableRuleIndex();
        Assert.check(ruleIndex != -1, "Can not advance but tried to do so.");

        // Apply. At that position c's tape's value = r's replacement symbol.
        setTapeCell(getCurrentPosition(), getRuleNewSymbol(ruleIndex));

        // LEFT: -1, RIGHT: +1.
        if (getRuleDirection(ruleIndex) == Direction.LEFT) {
            setPosition(getCurrentPosition() - 1);
        }
        else {
            setPosition(getCurrentPosition() + 1);
        }

        // c's current state = r's new state.
        setCurrentState(getRuleNewState(ruleIndex));

        onChanged();
    }

    @Override
    public void addConfigurationObserver(ConfigurationObserver o) {
        observers.add(o);
    }

    @Override
    public void deleteConfigurationObserver(ConfigurationObserver o) {
        observers.remove(o);
    }

    protected void onChanged() {
        for (ConfigurationObserver observer : observers) {
            observer.notifyChangeToConfiguration(this);
        }
    }
}
