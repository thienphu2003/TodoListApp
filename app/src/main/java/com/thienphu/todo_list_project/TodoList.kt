package com.thienphu.todo_list_project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList


@Composable
fun getTodoList() : SnapshotStateList<TodoModel>{
    val todoList = remember {
        mutableStateListOf(
            TodoModel(1,"Go to school"),
            TodoModel(2,"Go home")
        )
    }

    return todoList
}

