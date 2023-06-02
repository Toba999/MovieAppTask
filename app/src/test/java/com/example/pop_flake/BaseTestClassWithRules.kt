package com.example.pop_flake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.junit.Rule
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.CountDownLatch


open class BaseTestClassWithRules :BaseTest(){


    @get: Rule
    val androidComponentRule = InstantTaskExecutorRule()


    fun invokePrivateMethod(instance:Any,methodName:String,prams:Array<Class<*>>?=null
                            , pramsValues:Array<Any?>?=null):Any? {
        val pramsAvailable = prams != null && pramsValues != null
        val method =if(pramsAvailable)
            instance::class.java.getDeclaredMethod(methodName,*(prams!!))
        else instance::class.java.getDeclaredMethod(methodName)
        method.isAccessible = true
        return if(pramsAvailable)
            method.invoke(instance,*(pramsValues!!))
        else
            method.invoke(instance)
    }

    fun setPrivateField(instance: Any,name:String,value:Any?){
        val field = try {
            instance::class.java.getDeclaredField(name)
        }catch (e:NoSuchFieldException){
            instance::class.java.superclass?.getDeclaredField(name)?:throw e
        }
        field.isAccessible =true
        when(value){
            is Boolean ->field.setBoolean(instance,value)
            is Int ->field.setInt(instance , value)
            is Byte ->field.setByte(instance , value)
            is Char ->field.setChar(instance , value)
            is Double ->field.setDouble(instance , value)
            is Float ->field.setFloat(instance , value)
            is Long ->field.setLong(instance , value)
            is Short ->field.setShort(instance , value)
            else ->field.set(instance , value)
        }
    }

    fun getPrivateField(instance: Any,name:String): Any? {
        val field = try {
            instance::class.java.getDeclaredField(name)
        }catch (e:NoSuchFieldException){
            instance::class.java.superclass?.getDeclaredField(name)?:throw e
        }
        field.isAccessible =true
        return field.get(instance)
    }

    @Throws(Exception::class)
    fun setFinalIntStatic(field: Field, value: Any) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        when(value){
            is Boolean ->field.setBoolean(null,value)
            is Int ->field.setInt(null , value)
            is Byte ->field.setByte(null , value)
            is Char ->field.setChar(null , value)
            is Double ->field.setDouble(null , value)
            is Float ->field.setFloat(null , value)
            is Long ->field.setLong(null , value)
            is Short ->field.setShort(null , value)
            else ->field.set(null , value)
        }
    }
    inline fun <reified T : Any> Flow<T>.testFlowObserver(testScope: CoroutineScope): FlowTestObserver<T> {
        return FlowTestObserver<T>().also { observer ->
            testScope.launch {
                collect { value -> observer.onChanged(value) }
            }
        }
    }


    class FlowTestObserver<T> {
        private val latch = CountDownLatch(1)
        private val values = mutableListOf<T>()

        fun onChanged(newValue: T) {
            values.add(newValue)
            latch.countDown()
        }

        suspend fun awaitValue(): T {
            if (values.isNotEmpty()) {
                return values.last()
            }

            latch.await()
            return values.last()
        }
    }
}
