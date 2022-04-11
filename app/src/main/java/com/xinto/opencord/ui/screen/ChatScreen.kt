package com.xinto.opencord.ui.screen

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.People
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xinto.bdc.BottomSheetDialog
import com.xinto.opencord.R
import com.xinto.opencord.domain.model.DomainMessage
import com.xinto.opencord.ui.viewmodel.ChatViewModel
import com.xinto.opencord.ui.widget.WidgetChatInput
import com.xinto.opencord.ui.widget.WidgetChatMessage
import com.xinto.opencord.util.SimpleAstParser
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun ChatScreen(
    onChannelsButtonClick: () -> Unit,
    onMembersButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    parser: SimpleAstParser = get(),
    viewModel: ChatViewModel = getViewModel(),
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SmallTopAppBar(
                title = { Text(viewModel.channelName) },
                navigationIcon = {
                    IconButton(onChannelsButtonClick) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onMembersButtonClick) {
                        Icon(
                            imageVector = Icons.Rounded.People,
                            contentDescription = null
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            tonalElevation = 2.dp
        ) {
            when (viewModel.state) {
                is ChatViewModel.State.Loading -> {
                    ChatScreenLoading(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                is ChatViewModel.State.Loaded -> {
                    ChatScreenLoaded(
                        modifier = Modifier.fillMaxSize(),
                        parser = parser,
                        messages = viewModel.messages,
                        channelName = viewModel.channelName,
                        userMessage = viewModel.userMessage,
                        onUserMessageUpdate = viewModel::updateMessage,
                        onUserMessageSend = viewModel::sendMessage,
                    )
                }
                is ChatViewModel.State.Error -> {

                }
            }
        }
    }
}

@Composable
fun ChatScreenLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ChatScreenLoaded(
    messages: List<DomainMessage>,
    channelName: String,
    userMessage: String,
    onUserMessageUpdate: (String) -> Unit,
    onUserMessageSend: () -> Unit,
    parser: SimpleAstParser,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true,
        ) {
            items(messages) { message ->
                var showBottomDialog by rememberSaveable { mutableStateOf(false) }

                WidgetChatMessage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .combinedClickable(
                            onLongClick = {
                                showBottomDialog = true
                            },
                            onClick = {}
                        )
                        .padding(8.dp),
                    message = message,
                    parser = parser,
                )

                if (showBottomDialog) {
                    MessageActionMenu(
                        onDismissRequest = {
                            showBottomDialog = false
                        }
                    )
                }
            }
        }
        WidgetChatInput(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 4.dp
            ),
            value = userMessage,
            onValueChange = onUserMessageUpdate,
            onSendClick = onUserMessageSend,
            hint = { Text(stringResource(R.string.chat_input_hint, channelName)) }
        )
    }
}

@Composable
fun MessageActionMenu(
    onDismissRequest: () -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface {
            Column {
//                FullWidthButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    icon = {},
//                    content = {
//                        Text("Reply")
//                    }
//                ) {
//
//                }
//                FullWidthButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    icon = {},
//                    content = {
//                        Text("Create Thread")
//                    }
//                ) {
//
//                }
//                FullWidthButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    icon = {},
//                    content = {
//                        Text("Copy Text")
//                    }
//                ) {
//
//                }
//                FullWidthButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    icon = {},
//                    content = {
//                        Text("Delete")
//                    }
//                ) {
//
//                }
            }
        }
    }
}