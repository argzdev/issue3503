package com.argz.issue3503

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.argz.issue3503.model.Performance
import com.argz.issue3503.model.Weight
import com.argz.issue3503.model.Workout
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.System.currentTimeMillis
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private const val TAG = "SharedViewModel"
class SharedViewModel: ViewModel() {
    lateinit var childEventListener: ChildEventListener
    lateinit var database: DatabaseReference

    fun addChildEventListener(){
        database = Firebase.database.reference.child("workout")

        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + snapshot.key!!)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${snapshot.key}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + snapshot.key!!)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved:" + snapshot.key!!)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", error.toException())
            }
        }
        database.limitToLast(1).addChildEventListener(childEventListener)
    }

    fun add() {
        database = Firebase.database.reference.child("workout")

        GlobalScope.launch {
            cleanDb(database,"workout").await()

            (1..4000).map {
                async {
                    val start = currentTimeMillis()
                    writeWorkOutToDb(database).await()
                    val end = currentTimeMillis()
                    Log.d(TAG, "add $it: ${end - start} ms")
                }
            }
            Log.d(TAG, "add: done")
        }
    }
    fun writeWorkOutToDb(database: DatabaseReference) = GlobalScope.async {
        database.child("workout").push().setValue(createWorkOutObject())
    }
    fun createWorkOutObject(): Workout {
        val weight = Weight (
            (0..Int.MAX_VALUE).random(),
            UUID.randomUUID().toString()
        )

        val performances: ArrayList<Performance> = ArrayList()

        for (i in 1 until 10) {
            performances.add(
                Performance(
                    (0..Long.MAX_VALUE).random(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    (0..Int.MAX_VALUE).random(),
                    weight,
                )
            )
        }

        val workout = Workout(
            UUID.randomUUID().toString(),
            (0..Long.MAX_VALUE).random(),
            (0..Long.MAX_VALUE).random(),
            (0..Long.MAX_VALUE).random(),
            UUID.randomUUID().toString(),
            performances
        )

        return workout
    }
    fun cleanDb(database: DatabaseReference, path: String) = GlobalScope.async {
        database.child(path).setValue(null)
    }
}