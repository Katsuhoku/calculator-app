package com.example.calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Simple button component that displays a string symbol and has an action associated to be
 * passed when the [onClick] function is launched.
 *
 * @param symbol the string symbol to display. It accepts a string, but if its too long it might
 * not fit depending on the button size.
 * @param action the [CalculatorAction] associated to this button.
 * @param modifier the [Modifier] to be applied to the button for extra customization.
 * @param onClick the function this button will trigger when clicked. It has to receive a
 * [CalculatorAction], that will be the action passed to this composable.
 */
@Composable
fun CalculatorButton(
    symbol: String,
    action: CalculatorAction,
    modifier: Modifier,
    onClick: (CalculatorAction) -> Unit,
) {
    Box(
        // Instead of the modifier object, the default Modifier companion object is used
        // to define the shape. This way, all the custom properties will be applied AFTER
        // the Box button is set with the round shape and the onClick() function
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp, 25.dp, 25.dp, 25.dp))
            .clickable { onClick(action) }
            .then(modifier)
    ) {
        Text(
            text = symbol,
            fontSize = 36.sp,
            color = Color.White,
        )
    }
}