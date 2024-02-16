package com.example.calculator

/**
 * The state of the operation the user has entered and that it's displayed on the Calculator Screen
 * or in the operation history.
 * The state can hold as many as numbers and operations is needed, but it always will have at least
 * one number stored. The numbers are stored as String to make easier its manipulation to convert
 * them into Integers or Doubles.
 * The [numbers] and [operations] lists are sequential and their positions are associated. The
 * operation <i> is between the numbers <i> and <i + 1>.
 * Only states in the history stores a result. For the state in the screen is always empty.
 * Therefore, the calculation is delegated to the view model. The result here is only for screen
 * display.
 *
 * @property numbers a [MutableList] of Strings that represents the operands of the operation.
 * @property operations a [MutableList] of [CalculatorOperation] with the sequence of operations.
 * @property result the result of the operation.
 */
data class CalculatorState(
    val numbers: MutableList<String> = mutableListOf(""),
    val operations: MutableList<CalculatorOperation> = mutableListOf(),
    val result: String = ""
) {
    override fun toString(): String {
        var displayString = ""
        numbers.forEachIndexed { index, number ->
            displayString += number + if (index < operations.size) operations[index].symbol else ""
        }

        if (result.isNotBlank()) displayString += " = $result"

        return displayString
    }
}
