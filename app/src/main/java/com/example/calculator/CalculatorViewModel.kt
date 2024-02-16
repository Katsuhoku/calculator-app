package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * View Model of the [Calculator]. It holds the [CalculatorState] objects that are displayed
 * on the main calculator screen and on the calculator history.
 * The view model provides the function that all the [CalculatorButton] will trigger when clicked,
 * and defines the flow to perform each operation.
 *
 * @property state the [CalculatorState] displayed in the main screen.
 * @property history the [CalculatorState] list to display in the history section.
 * @property clearClicks internal variable to define the behavior of the Clear Button.
 */
class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    var history by mutableStateOf(emptyList<CalculatorState>())
        private set

    private var clearClicks = 0

    /**
     * Function triggered by the [CalculatorButton] objects in the [Calculator]. It defines the
     * flow of action depending on the action received in the argument.
     *
     * @param action the [CalculatorAction] associated to the button that triggered the function.
     */
    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> {
                state = CalculatorState()
                clearClicks++

                if (clearClicks == 2) {
                    history = emptyList<CalculatorState>()
                    clearClicks = 0
                }
            }
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()

            is CalculatorAction.Delete -> performDeletion()
        }

        if (action !is CalculatorAction.Clear) clearClicks = 0
    }

    /**
     * Appends the number (digit) passed in the [CalculatorAction.Number] to the last number in the
     * [state].
     *
     * @param number the digit to append.
     */
    private fun enterNumber(number: Int) {
        if (state.numbers.last().length >= MAX_NUM_LENGTH) return

        state = state.copy(
            numbers = state.numbers.toMutableList().also { numbers ->
                numbers[numbers.size - 1] = numbers[numbers.size - 1] + number
            }
        )
    }

    /**
     * Adds the respective [CalculatorOperation] passed in the argument to the operations list of
     * the [state], then adds a new empty number to the number list.
     *
     * @param operation the [CalculatorOperation] to add.
     */
    private fun enterOperation(operation: CalculatorOperation) {
        if (state.numbers.last().isNotBlank()) {
            state = state.copy(
                operations = state.operations.toMutableList().also {
                    it.add(operation)
                },
                numbers = state.numbers.toMutableList().also {
                    it.add("")
                }
            )
        }
    }

    /**
     * Puts a decimal point into the last number of the [state] number list, if didn't exist
     * one already. If the last number is empty, it also appends a "0" before appending the decimal
     * point.
     */
    private fun enterDecimal() {
        if (!state.numbers.last().contains(".")) {
            state = state.copy(
                numbers = state.numbers.toMutableList().also {
                    if (it.last().isBlank()) it[it.size - 1] += "0"
                    it[it.size - 1] += "."
                }
            )
        }
    }

    /**
     * Deletes the last character visible in the operation string. It checks whether it was part
     * of the last number of the [state] or if it was an operation, and in this last case removes
     * the operation from the list and also removes the last number (which will be empty).
     */
    private fun performDeletion() {
        if (state.numbers.last().isNotBlank()) {
            state = state.copy(
                numbers = state.numbers.toMutableList().also {
                    it[state.numbers.size - 1] = it.last().dropLast(1)
                }
            )
        } else if (state.operations.size > 0) {
            state = state.copy(
                operations = state.operations.toMutableList().also {
                    it.removeLast()
                },
                numbers = state.numbers.toMutableList().also {
                    it.removeLast()
                }
            )
        }
    }

    /**
     * Performs the given operation. The operation won't be done if the last number in [state] is
     * empty, as it indicates the operation string ends with an operation symbol.
     * The process follows the convention of the order of operations, starting with multiplications
     * and divisions, and then operating adds and subtracts.
     * The result is stored in two places: a copy of the state with the entire operation is made
     * and the result is appended, then stored in the [history]; then, a new empty [state], and the
     * first number is set with the result, displaying it on the screen.
     */
    private fun performCalculation() {
        val numbers = state.numbers.toMutableList()
        val operations = state.operations.toMutableList()

        if (numbers.last().isBlank() || operations.size < 1) return

        var i = 0
        var highPreference = true
        while (operations.size > 0) {
            var skipped = false

            val rightOperand = numbers[i + 1].toDouble()
            val leftOperand = numbers[i].toDouble()

            val operation = when (operations[i]) {
                is CalculatorOperation.Add -> leftOperand + rightOperand
                is CalculatorOperation.Subtract -> leftOperand - rightOperand
                is CalculatorOperation.Multiply -> leftOperand * rightOperand
                is CalculatorOperation.Divide -> leftOperand / rightOperand
            }

            if (highPreference &&
                (operations[i] is CalculatorOperation.Add ||
                        operations[i] is CalculatorOperation.Subtract))
                skipped = true

            if (!skipped) {
                operations.removeAt(i)
                numbers.removeAt(i + 1)

                numbers[i] = operation.toString()
            } else i++

            if (i == operations.size) {
                i = 0
                highPreference = !highPreference
            }
        }

        numbers[0] =
            if (numbers[0].endsWith(".0")) numbers[0].dropLast(2).toInt().toString()
            else numbers[0].take(15).toFloat().toString()

        val auxList = history.toMutableList()
        auxList.add(0, state.copy(result = numbers[0].take(15)))
        history = auxList.toList()

        state = state.copy(
            numbers = numbers.toMutableList(),
            operations = mutableListOf(),
            result = ""
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}