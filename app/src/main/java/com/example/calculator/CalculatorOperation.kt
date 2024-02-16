package com.example.calculator

/**
 * A class to represent the possible operations the calculator can do. All of them have only
 * the string symbol that represents them.
 *
 * @property Add object for the add operation.
 * @property Subtract object for the subtract operation.
 * @property Multiply object for the multiply operation.
 * @property Divide object for the divide operation.
 */
sealed class CalculatorOperation(val symbol: String) {
    data object Add: CalculatorOperation("+")
    data object Subtract: CalculatorOperation("-")
    data object Multiply: CalculatorOperation("x")
    data object Divide: CalculatorOperation("/")
}