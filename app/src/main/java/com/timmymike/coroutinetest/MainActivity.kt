package com.timmymike.coroutinetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var job1: Job? = null
    private var job2: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun fun1(): Job {
        return GlobalScope.launch {
            launch {
                repeat(100) { i ->
                    println("job1: I'm sleeping $i ...")
                    delay(50L)
                }
            }

            if (fun2()) {
                println("fun2 is return true")
            } else {
                println("fun2 is return false")
            }
            println("OK,done.")
        }
    }

    private fun fun2(): Boolean {
        job2 = GlobalScope.launch {
            repeat(100) { i ->
                println("job2: I'm sleeping $i ...")
                delay(50L)
            }
        }

        while (job2?.isCompleted == false) {
            Thread.sleep(10)
        }
        return (0..1).random() == 1

    }

    override fun onResume() {
        super.onResume()
        job1 = fun1()
    }

    override fun onPause() {
        super.onPause()
        GlobalScope.launch {
            println("main: I'm tired of waiting for job1")
            job1?.cancel() // cancels the job
            println("main: I'm tired of waiting for job2")
            job2?.cancel() // cancels the job
            println("main: Now I can quit.")
        }
    }
}