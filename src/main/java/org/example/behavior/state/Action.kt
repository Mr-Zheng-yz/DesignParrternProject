package org.example.behavior.state

fun moneyToPlatform() {
    println("动作：付款给平台...")
}

fun moneyToSeller() {
    println("动作：金额打给商家...")
}

fun moneyToBuyer() {
    println("动作：金额退还给买家...")
}