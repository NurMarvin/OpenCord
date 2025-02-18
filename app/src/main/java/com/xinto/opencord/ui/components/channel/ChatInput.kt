package com.xinto.opencord.ui.components.channel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xinto.opencord.ui.components.OCBasicTextField

@Composable
fun ChatInput(
    value: String,
    onValueChange: (value: String) -> Unit,
    sendEnabled: Boolean,
    onSendClick: () -> Unit,
    hint: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OCBasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            maxLines = 4,
            decorationBox = { innerTextField ->
                CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Box(modifier = Modifier.padding(14.dp)) {
                            innerTextField()
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.medium,
                                LocalTextStyle provides MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                            ) {
                                if (value.isEmpty()) {
                                    hint()
                                }
                            }
                        }
                    }
                }
            },
        )
        AnimatedVisibility(
            visible = value.isNotEmpty(),
            enter = slideInHorizontally { it * 2 },
            exit = slideOutHorizontally { it * 2 },
        ) {
            FilledIconButton(
                onClick = onSendClick,
                enabled = sendEnabled,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = null,
                )
            }
        }
    }
}
