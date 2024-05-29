package com.thienphu.todo_list_project

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.io.FileNotFoundException
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

const val FILE_NAME = "todolist.txt"

fun writeData(items : SnapshotStateList<String>,context : Context){

    val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
    val oas = ObjectOutputStream(fos)
    val itemList = ArrayList<String>()
    itemList.addAll(items)
    oas.writeObject(itemList)
    oas.close()
}

fun readData(context : Context) : SnapshotStateList<String>{
    var itemList : ArrayList<String>
    itemList = try {
        val fis = context.openFileInput(FILE_NAME)
        val ois = ObjectInputStream(fis)
        ois.readObject() as ArrayList<String>
    }catch (e : Exception){
        ArrayList()
    }
    val items = SnapshotStateList<String>()
    items.addAll(itemList)
    return items

}
