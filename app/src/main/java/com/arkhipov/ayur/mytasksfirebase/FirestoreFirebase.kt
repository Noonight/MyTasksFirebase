package com.arkhipov.ayur.mytasksfirebase

import com.arkhipov.ayur.mytasksfirebase.log.Log
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class FirestoreFirebase(val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    fun addTask(task: Task) {
        val docRef = db.collection("tasks").document(task.title) //
        val tasks = HashMap<String, Any>()
        tasks.put("title", task.title)
        tasks.put("description", task.description)

        db.collection("tasks")
                .add(tasks)
                .addOnSuccessListener {
                    Log.d(it.id)
                }
                .addOnFailureListener {
                    Log.w("Error", it)
                }
    }

    fun getTasks(): List<Task>? {

        var tasks: List<Task>? = null

        val query = FirebaseFirestore.getInstance()
                .collection("tasks")
                .limit(50)
        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (e != null) {

                    return
                }

                tasks = snapshot?.toObjects(Task::class.java)


            }
        })

        return tasks
    }

    fun getTaskFirestoreRecyclerOptions(): FirestoreRecyclerOptions<Task> {
        return FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(FirebaseFirestore.getInstance()
                        .collection("tasks")
                        .limit(50), Task::class.java)
                .build()
    }

}