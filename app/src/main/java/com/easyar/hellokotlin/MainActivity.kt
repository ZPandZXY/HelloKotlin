package com.easyar.hellokotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.easyar.hellokotlin.base.BaseFragmentPagerAdapter
import com.easyar.hellokotlin.fragment.SplashFragment
import com.easyar.hellokotlin.util.L
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt

class MainActivity /*constructor(user: String, token: String) */ : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    val textView = null
    var str: String = "hello kotlin"
    var str1: String? = null
    val x = 8


    /*constructor(uid: String, user: String, token: String) : this(user, token) {

    }*/

    init {
        "init all your stuff here"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val textView = findViewById<TextView>(R.id.textkotlin)
        textView.setOnClickListener {
            Log.d(TAG, "textviewclick")
        }*/
        var intent: Intent? = null
        textkotlin.text = "text kotlin text"
        textkotlin.setOnClickListener {
            showLongToast("clcik text kotlin")
            intent = Intent(this, NetActivity::class.java)
            startActivity(intent)
        }

        when (x) {
            in 1..10 -> print("x is in range")
            in 7..7 -> print("x is valid")
            !in 10..20 -> print("x is outside the range")
            else -> print("none of the above")
        }
        printProduct("12", "13")
        L.e("这是print")
        loop()

        for (x in 1..10 step 2)
            L.e("x=$x" + x)


        var fragment = SplashFragment()

        L.e("" + fragment)
        /*supportFragmentManager.inTransaction {
            add(R.id.splash_layout, fragment)
        }*/
        addFragment(fragment, R.id.splash_layout)
        window.decorView.postDelayed(Runnable {
            run {
                removeFragment(fragment)
            }
        }, 3000)

        var pageAdapter: BaseFragmentPagerAdapter? = BaseFragmentPagerAdapter(supportFragmentManager)
        var frags: ArrayList<Fragment>? = ArrayList()
        for (i in 1..5) {
            var sp: SplashFragment? = SplashFragment()
            sp!!.init(i)
            frags!!.add(sp!!)
        }
        pageAdapter!!.addAll(frags!!)
        home_viewpager.adapter = pageAdapter
        home_viewpager.offscreenPageLimit=5
    }


    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commitAllowingStateLoss()
    }

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    fun AppCompatActivity.removeFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { remove(fragment) }
    }


    val name: String = "string"
    var age = "23"

    fun getN(): String {
        return "string"
    }

//    val intent1 = Intent(this, MainActivity::class.java)


    fun getAddress(id: Int, name: String): String {
        return "got it"
    }

    fun getAddress1(id: Int, name: String) = {
        "got it"
    }

    fun getAddress2(id: Int, name: String) = "got it"

    fun getAddress3(id: Int, name: String): String? = "got it"

    fun getAddress4(id: Int, name: String): Unit {}

    fun getAddress5(id: Int, name: String) {}

    fun Activity.showLongToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        if (x != null && y != null)
            print(x * y)
        else
            print("either '$arg1' or '$arg2' is not a bumber")
    }

    fun loop() {
        val items = listOf("jfkla", "jfla", "jfalkj")
        var index = 0
        while (index < items.size) {
            L.d("item at $index is${items[index]}")
            index++
        }
    }


}
