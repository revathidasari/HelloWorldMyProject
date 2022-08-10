package com.example.koin2

import org.koin.dsl.module.module

class Student(private val course: Course, private val friend: Friend) {

    fun doWork(): String =
        course.study() + "\n" + friend.play()
    // """|course.study()|friend.play()""".trimMargin()
}

    class Friend {
        fun play(): String = "I am playing with my friend"
    }

    class Course {
        fun study(): String = "I am studying..."
    }

    // Method which holds instances works for koin
/*    by inject() - lazy evaluated instance from Koin container
    get() - eager fetch instance from Koin container
    getProperty()/setProperty() - get/set property*/

    val appcheck = module {
/*        single { Course() }
        factory { Friend() }*/
        factory { Student(get(), get()) }
    }

/*......Another class.......*/

class Student_1(private val interest: Interest) {

    fun passion(): String =
        interest.goal()
}

    class Interest {
        fun goal(): String = "I want to achieve a goal"
    }
    val access = module {
        factory { Interest() }
        factory { Student_1(get()) }
    }
