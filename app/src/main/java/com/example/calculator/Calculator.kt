package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.LightGray
import com.example.calculator.ui.theme.Aqua

/**
 * Screen that contains all the elements of the Calculator.
 *
 * @param state the state of the operation displayed on the calculator.
 * @param history a list of [CalculatorState] with the previous operations
 * @param modifier for customization
 * @param buttonSpacing the gap between the Calculator buttons
 * @param onAction a function that must receive a [CalculatorAction]. This function will be
 * triggered every time the user clicks a button. The button will send it's respective action.
 */
@Composable
fun Calculator(
    state: CalculatorState,
    history: List<CalculatorState>,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Calculator History
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    reverseLayout = true
                ) {
                    items(history) {state ->
                        Text(
                            text = state.toString(),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 7.dp),
                            fontWeight = FontWeight.Light,
                            fontSize = 30.sp,
                            color = Color.DarkGray,
                            maxLines = 2,
                            lineHeight = 30.sp
                        )
                    }
                }

                // Calculator operation state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .weight(1.5f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = state.toString(),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                        fontSize = 60.sp,
                        color = Color.White,
                        maxLines = 2,
                        lineHeight = 60.sp,
                    )
                }
            }

            // Calculator Buttons
            var i = 0
            while (i < Calculator.buttons.size) {
                var weightCount = 0f
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                ) {
                    while (weightCount != 4f) {
                        CalculatorButton(
                            symbol = Calculator.buttons[i].symbol,
                            action = Calculator.buttons[i].action,
                            modifier = Modifier
                                .background(Calculator.buttons[i].color)
                                .aspectRatio(Calculator.buttons[i].weight)
                                .weight(Calculator.buttons[i].weight),
                            onClick = onAction
                        )

                        weightCount += Calculator.buttons[i].weight
                        i++
                    }
                }
            }
        }
    }
}

/**
 * Object that stores the individual, repetitive information of every button on screen.
 *
 * Holds inside a class to represent the information of each button.
 * The object is used to generate the [Calculator Buttons][CalculatorButton] inside a loop.
 *
 * @property buttons the list of internal [Calculator.CalculatorButton] objects.
 */
private object Calculator {
    /**
     * Class that represents the information passed to [CalculatorButton] composable.
     *
     * @property symbol the char symbol to display on the button: a number, an operation sign, etc.
     * @property color the background color of the button. [Color.DarkGray] is set as default.
     * @property weight a float number that affects the weight and aspect ratio of the button in
     * its row.
     * @property action the [CalculatorAction] associated to that button.
     */
    class CalculatorButton(
        val symbol: String,
        val color: Color = Color.DarkGray,
        val weight: Float = 1f,
        val action: CalculatorAction
    )

    val buttons = listOf<CalculatorButton>(
        // First Row
        CalculatorButton(
            symbol = "AC",
            color = LightGray,
            weight = 2f,
            action = CalculatorAction.Clear
        ),
        CalculatorButton(
            symbol = "Del",
            color = LightGray,
            action = CalculatorAction.Delete
        ),
        CalculatorButton(
            symbol = "÷",
            color = Aqua,
            action = CalculatorAction.Operation(CalculatorOperation.Divide)
        ),
        // Second Row
        CalculatorButton(
            symbol = "7",
            action = CalculatorAction.Number(7)
        ),
        CalculatorButton(
            symbol = "8",
            action = CalculatorAction.Number(8)
        ),
        CalculatorButton(
            symbol = "9",
            action = CalculatorAction.Number(9)
        ),
        CalculatorButton(
            symbol = "x",
            color = Aqua,
            action = CalculatorAction.Operation(CalculatorOperation.Multiply)
        ),
        // Third Row
        CalculatorButton(
            symbol = "4",
            action = CalculatorAction.Number(4)
        ),
        CalculatorButton(
            symbol = "5",
            action = CalculatorAction.Number(5)
        ),
        CalculatorButton(
            symbol = "6",
            action = CalculatorAction.Number(6)
        ),
        CalculatorButton(
            symbol = "-",
            color = Aqua,
            action = CalculatorAction.Operation(CalculatorOperation.Subtract)
        ),
        // Fourth Row
        CalculatorButton(
            symbol = "1",
            action = CalculatorAction.Number(1)
        ),
        CalculatorButton(
            symbol = "2",
            action = CalculatorAction.Number(2)
        ),
        CalculatorButton(
            symbol = "3",
            action = CalculatorAction.Number(3)
        ),
        CalculatorButton(
            symbol = "+",
            color = Aqua,
            action = CalculatorAction.Operation(CalculatorOperation.Add)
        ),
        // Fifth Row
        CalculatorButton(
            symbol = "0",
            weight = 2f,
            action = CalculatorAction.Number(0)
        ),
        CalculatorButton(
            symbol = ".",
            action = CalculatorAction.Decimal
        ),
        CalculatorButton(
            symbol = "=",
            color = Aqua,
            action = CalculatorAction.Calculate
        ),
    )
}