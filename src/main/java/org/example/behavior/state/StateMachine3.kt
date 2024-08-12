package org.example.behavior.state

/**
 * 订单状态机：状态模式
 */

fun main() {
    println("流程一：")
    val stateMachine1 = OrderStateMachine3()
    stateMachine1.payment()
    stateMachine1.shipment()
    stateMachine1.receipt()
    println("")

    println("流程二：")
    val stateMachine2 = OrderStateMachine3()
    stateMachine2.cancel()
    println("")

    println("流程三：")
    val stateMachine3 = OrderStateMachine3()
    stateMachine3.payment()
    stateMachine3.cancel()
    stateMachine3.shipment()
    println("")
}

//状态流转事件接口，各状态需实现：
interface IOrder {
    fun getDesc(): String

    //Kotlin中接口支持默认实现（高版本的Java也支持了）
    fun payment(stateMachine: OrderStateMachine3): Unit {
        println("${stateMachine.getOrderState().getDesc()}不响应事件：买家付款")
    }
    fun shipment(stateMachine: OrderStateMachine3): Unit {
        println("${stateMachine.getOrderState().getDesc()}不响应事件：商家发货")
    }
    fun receipt(stateMachine: OrderStateMachine3): Unit {
        println("${stateMachine.getOrderState().getDesc()}不响应事件：买家收货")
    }
    fun cancel(stateMachine: OrderStateMachine3): Unit {
        println("${stateMachine.getOrderState().getDesc()}不响应事件：买家/商家取消")
    }
}

//状态：待付款
object OrderWaitPayment : IOrder {
    override fun getDesc(): String = "待付款"

    override fun payment(stateMachine: OrderStateMachine3) {
        stateMachine.setOrderState(OrderWaitShipment)
        println("动作：付款给平台...")
    }

    override fun cancel(stateMachine: OrderStateMachine3) {
        stateMachine.setOrderState(OrderCanceled)
    }
}

//状态：待发货
object OrderWaitShipment : IOrder {
    override fun getDesc(): String = "待发货"

    override fun shipment(stateMachine: OrderStateMachine3) {
        stateMachine.setOrderState(OrderWaitReceipt)
    }

    override fun cancel(stateMachine: OrderStateMachine3) {
        stateMachine.setOrderState(OrderCanceled)
        println("动作：金额退还给买家...")
    }

}

//状态：待收货
object OrderWaitReceipt : IOrder {
    override fun getDesc(): String = "待收货"

    override fun receipt(stateMachine: OrderStateMachine3) {
        stateMachine.setOrderState(OrderCompleted)
        println("动作：金额打给商家...")
    }
}

//状态：已完成
object OrderCompleted: IOrder {
    override fun getDesc(): String = "已完成"
}

//状态：已取消
object OrderCanceled: IOrder {
    override fun getDesc(): String = "已取消"
}

//状态机：
class OrderStateMachine3 {
    private var currentState: IOrder = OrderWaitPayment  //待付款作为初始状态

    fun setOrderState(orderState: IOrder) {
        currentState = orderState
    }

    fun getOrderState(): IOrder = currentState

    //事件：买家付款
    fun payment() {
        println("事件：买家付款")
        currentState.payment(this)
        printState()
    }

    //事件：商家发货
    fun shipment() {
        println("事件：商家发货")
        currentState.shipment(this)
        printState()
    }

    //事件：买家收货
    fun receipt() {
        println("事件：买家收货")
        currentState.receipt(this)
        printState()
    }

    //事件：买家/商家取消
    fun cancel() {
        println("事件：买家/商家取消")
        currentState.cancel(this)
        printState()
    }

    private fun printState() {
        println("订单状态: ${currentState.getDesc()}")
    }

}
