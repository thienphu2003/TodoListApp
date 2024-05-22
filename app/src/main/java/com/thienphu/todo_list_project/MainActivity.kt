package com.thienphu.todo_list_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun MainPage() {

}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TODO_List_ProjectTheme {
//        Greeting("Android")
//    }
//}