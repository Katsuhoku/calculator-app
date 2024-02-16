package com.example.calculator

/**
 * Group of possible actions to trigger through the buttons of the interface.
 *
 * @property Number an action that represents a number button clicked.
 * @property Clear the clear screen action.
 * @property Delete the delete last character action.
 * @property Decimal the decimal point action.
 * @property Calculate the calculate action triggered by "=" button.
 * @property Operation an action that represents an operation the calculator can do.
 */
sealed class CalculatorAction {
    data class Number(val number: Int): CalculatorAction()
    data object Clear: CalculatorAction()
    data object Delete: CalculatorAction()
    data object Decimal: CalculatorAction()
    data object Calculate: CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
}