package org.example.create.prototype

import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


/**
 * 原型模式-几种深拷贝方式
 */
fun main(args: Array<String>) {
    println("--浅拷贝 copy() 和 clone()")
    val user = User("白泽", 18, Address("浦东新区花园石桥路28弄", "上海"))
    val userCopy1 = user.copy()
    val userCopy2 = user.clone() as User

    println("user1:${user}")
    println("user2:${userCopy1}")
    println("user address:${user.address == userCopy1.address}")     //判断值，相当于equal
    println("user address:${user.address === userCopy1.address}")    //判断内存地址
    println("---")
    println("user1:${user}")
    println("user2:${userCopy2}")
    println("user address:${user.address == userCopy2.address}")     //判断值，相当于equal
    println("user address:${user.address === userCopy2.address}")    //判断内存地址
}

//深拷贝-序列化Serializable
fun deepCopy1(obj: Any?): Any {
    val bo = ByteArrayOutputStream()
    val oo = ObjectOutputStream(bo)
    oo.writeObject(obj)

    val bi = ByteArrayInputStream(bo.toByteArray())
    val oi = ObjectInputStream(bi)

    return oi.readObject()
}

//深拷贝-Gson
inline fun <reified T> deepCopy2(data: T): T {
    val gson = Gson()
    val json = gson.toJson(data)
    return gson.fromJson<T>(json, T::class.java)
}