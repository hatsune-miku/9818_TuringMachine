/*
 * @author Zhen Guan
 * @id 202191382
 * @email zguan@mun.ca
 *
 * This file was prepared by Zhen Guan
 * It was finished by me alone
 */

package turmach.model;

public record Rule(
    String initialState,
    String newState,

    Character initialSymbol,
    Character replacementSymbol,

    TuringMachine.Direction direction
) { }
