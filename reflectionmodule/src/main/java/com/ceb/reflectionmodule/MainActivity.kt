package com.ceb.reflectionmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        print(isOdd(2))

        val p: (Int) -> Boolean = ::isOdd

    }


    /**
     * Class References
     */
    fun main(args: Array<String>) {
        val c = MyClass::class
        println(String::class)      //kotlin.String
        println(String::class.java) //java.lang.String
    }

    /**
     * Function References
     */
    fun funciontRef() {
        var numbers = listOf<Int>(1, 2, 3)
        print(numbers.filter(::isOdd))  // 输出 [1, 3]
    }

    fun isOdd(x: Int) = x % 2 != 0


    /**
     * Property References
     */
    var x = 1

    fun pro(args: Array<String>) {
        println(::x.get())
        ::x.set(3)
        println(x)
        println(::x.name)
    }

    fun getKClass(o: Any): KClass<Any> = o.javaClass.kotlin
}
