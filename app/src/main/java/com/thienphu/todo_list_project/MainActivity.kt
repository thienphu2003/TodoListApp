package com.thienphu.todo_list_project

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thienphu.todo_list_project.ui.theme.TODO_List_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODO_List_ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    val context = LocalContext.current
    var toDoName by remember {
        mutableStateOf("")
    }
    val todoList = readData(context)
    val focusManager = LocalFocusManager.current
    var deleteDialogStatus by remember {
        mutableStateOf(false)
    }
    var clickedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var updateDialogStatus by remember {
        mutableStateOf(false)
    }
    var clickedItem by remember {
        mutableStateOf("")
    }

    var textDialogStatus by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = toDoName, onValueChange = { toDoName = it },
                label = { Text(text = "Enter TODO") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Green,
                    unfocusedLabelColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White

                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                    .weight(7F)
                    .height(60.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp),

                )
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = {
                    if (toDoName.isNotEmpty()) {
                        todoList.add(toDoName)
                        writeData(todoList, context)
                        toDoName = ""
                        focusManager.clearFocus()
                    } else {
                        Toast.makeText(context, "Please enter a TODO", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .weight(3F)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.green),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = "ADD", fontSize = 20.sp)
            }

        }
        LazyColumn {
            items(count = todoList.count(), itemContent = { index ->
                val todo = todoList[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 1.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(0.dp),
                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = todo, color = Color.White, fontSize = 18.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(300.dp)
                                .clickable {
                                    clickedItem = todo
                                    textDialogStatus = true
                                }

                        )
                        Row {
                            IconButton(onClick = {
                                updateDialogStatus = true
                                clickedItemIndex = index
                                clickedItem = todo
                            }) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "edit",
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = {
                                deleteDialogStatus = true
                                clickedItemIndex = index
                            }) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "delete",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            })
        }
        if (deleteDialogStatus) {
            AlertDialog(onDismissRequest = { deleteDialogStatus = false }, confirmButton = {
                TextButton(onClick = {
                    todoList.removeAt(clickedItemIndex)
                    writeData(todoList, context)
                    deleteDialogStatus = false
                    Toast.makeText(context, "Item is removed from the list", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    Text(text = "Yes")
                }
            },
                title = {
                    Text(text = "Delete")
                },
                text = {
                    Text(text = "Do you want to delete this item from the list ?")
                },
                dismissButton = {
                    TextButton(onClick = {
                        deleteDialogStatus = false
                    }) {
                        Text(text = "No")
                    }
                })
        }
        if (updateDialogStatus) {
            AlertDialog(onDismissRequest = { updateDialogStatus = false }, confirmButton = {
                TextButton(onClick = {
                    todoList[clickedItemIndex] = clickedItem
                    writeData(todoList, context)
                    updateDialogStatus = false
                    Toast.makeText(context, "Item is updated successfully", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    Text(text = "Yes")
                }
            },
                title = {
                    Text(text = "Update")
                },
                text = {
                    TextField(value = clickedItem, onValueChange = {clickedItem = it})
                },
                dismissButton = {
                    TextButton(onClick = {
                        updateDialogStatus = false
                    }) {
                        Text(text = "No")
                    }
                })
        }
        if (textDialogStatus) {
            AlertDialog(onDismissRequest = { textDialogStatus = false }, confirmButton = {
                TextButton(onClick = {
                    textDialogStatus = false
                }) {
                    Text(text = "OK")
                }
            },
                title = {
                    Text(text = "TODO Item")
                },
                text = {
                    Text(text = clickedItem)
                },
               )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TODO_List_ProjectTheme {
        MainPage()
    }
}