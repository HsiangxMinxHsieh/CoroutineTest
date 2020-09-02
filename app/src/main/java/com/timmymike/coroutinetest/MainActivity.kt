package com.timmymike.coroutinetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = fun1()

    }
    private fun fun1():Job{
        return GlobalScope.launch {
            launch {
                repeat(1000) { i ->
                    println("job1: I'm sleeping $i ...")
                    delay(500L)
                }
            }
            fun2()

        }
    }

    private fun fun2() {
        repeat(1000) { i ->
            println("job2: I'm sleeping $i ...")
            Thread.sleep(500L)
        }

    }

    override fun onPause() {
        super.onPause()
        GlobalScope.launch {
            println("main: I'm tired of waiting!")
            job?.cancel() // cancels the job
            job?.join() // waits for job's completion
            println("main: Now I can quit.")
        }
    }
}