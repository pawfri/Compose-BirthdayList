package com.example.birthdaylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.birthdaylist.NavRoutes

@Composable
fun FriendContent(
    innerPadding: PaddingValues,
    title: String,
    subtitle: String,
    initialName: String,
    initialBirthday: Long?,
    onCancel: () -> Unit,
    onSave: (String, Long?) -> Unit
) {
    var name by rememberSaveable(initialName) { mutableStateOf(initialName) }
    var birthday by rememberSaveable(initialBirthday) {  mutableStateOf<Long?>(initialBirthday) }
    var showDatePicker by remember { mutableStateOf(false) }

    val isValid = name.isNotBlank()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(subtitle, style = MaterialTheme.typography.bodyLarge)

        FriendNameInput(
            value = name,
            onValueChange = { name = it }
        )

        OutlinedButton(onClick = { showDatePicker = true }) {
            Text(
                text = birthday?.let { millis ->
                    java.text.SimpleDateFormat(
                        "dd.MM.yyyy",
                        java.util.Locale.getDefault())
                        .format(java.util.Date(millis))
                } ?: "Select Birthday"
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                initialSelectedMillis = birthday,
                onDateSelected = {
                    birthday = it
                    showDatePicker = false
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)) {
                Text("Cancel",
                    style = MaterialTheme.typography.titleLarge)
            }
            OutlinedButton(
                onClick = { onSave(name.trim(), birthday) },
                enabled = isValid,
                modifier = Modifier.weight(1f)) {
                Text("Save",
                    style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun FriendNameInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    initialSelectedMillis: Long? = null,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedMillis)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = true
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            onDateSelected(datePickerState.selectedDateMillis)
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendContentPreview() {
    FriendContent(
        innerPadding = PaddingValues(0.dp),
        title = "Add New Friend",
        subtitle = "Enter friend information and click save",
        initialName = "Patrick",
        initialBirthday = 1735689600000L,
        onCancel = {},
        onSave = { _, _ -> }
    )
}
