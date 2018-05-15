package com.arkhipov.ayur.mytasksfirebase.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arkhipov.ayur.mytasksfirebase.App
import com.arkhipov.ayur.mytasksfirebase.R
import com.arkhipov.ayur.mytasksfirebase.Task
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    fun initViews() {

        btn_create.setOnClickListener {
            if (etNotNull()) {
                App.getFirestore().addTask(Task(et_title.text.toString(), et_description.text.toString()))
            }
        }

        btn_delete.setOnClickListener {

        }

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_tasks.layoutManager = layoutManager

        rv_tasks.adapter = object : FirestoreRecyclerAdapter<Task, TaskHolder>(App.getFirestore().getTaskFirestoreRecyclerOptions()) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
                return TaskHolder(view)
            }

            override fun onBindViewHolder(holder: TaskHolder, position: Int, model: Task) {
                holder.bindView(model, object : TaskItemClick {
                    @SuppressLint("ShowToast")
                    override fun itemClick(item: Task) {
                        Toast.makeText(App.getInstance(), item.toString(), Toast.LENGTH_LONG).show()
                    }
                })
            }

        }

    }

    interface TaskItemClick {
        fun itemClick(item: Task)
    }

    class TaskHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_title)
        lateinit var tvTitle: TextView
        @BindView(R.id.tv_description)
        lateinit var tvDescription: TextView

        init {
            ButterKnife.bind(itemView!!)
        }

        fun bindView(model: Task, taskItemClick: TaskItemClick) {
            tvTitle.text = model.title
            tvDescription.text = model.description
            itemView.setOnClickListener {
                taskItemClick.itemClick(model)
            }
        }
    }


    fun etNotNull(): Boolean {
        if (!TextUtils.isEmpty(et_title.text) && !TextUtils.isEmpty(et_description.text)) {
            return true
        }
        return false
    }
}