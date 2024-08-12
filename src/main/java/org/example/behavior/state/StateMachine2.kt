package org.example.behavior.state

/**
 * 订单状态机：使用图表法
 */

fun main() {
    println("流程一：")
    val stateMachine1 = OrderStateMachine2()
    stateMachine1.payment()
    stateMachine1.shipment()
    stateMachine1.receipt()
    println("")

    println("流程二：")
    val stateMachine2 = OrderStateMachine2()
    stateMachine2.cancel()
    println("")

    println("流程三：")
    val stateMachine3 = OrderStateMachine2()
    stateMachine3.payment()
    stateMachine3.cancel()
    stateMachine3.shipment()
    println("")
}

//订单事件：
enum class OrderEvent(val value: Int, val desc: String) {
    PAYMENT(0, "事件：买家付款"),
    SHIPMENT(1, "事件：商家发货"),
    RECEIPT(2, "事件：买家收货"),
    CANCEL(3, "事件：买家/商家取消")
}

//状态机：
class OrderStateMachine2 {
    private val STATE_EVENT_TABLE = arrayOf(
        arrayOf<OrderState?>(OrderState.WAIT_SHIPMENT, null, null, OrderState.CANCELLED),
        arrayOf<OrderState?>(null, OrderState.WAIT_RECEIPT, null, OrderState.CANCELLED),
        arrayOf<OrderState?>(null, null, OrderState.COMPLETED, null),
        arrayOf<OrderState?>(null, null, null, null),
        arrayOf<OrderState?>(null, null, null, null)
    )
    private val STATE_ACTION_TABLE = arrayOf(
        arrayOf<Function0<Unit>?>(::moneyToPlatform, null, null, null),
        arrayOf<Function0<Unit>?>(null, null, null, ::moneyToBuyer),
        arrayOf<Function0<Unit>?>(null, null, ::moneyToSeller, null),
        arrayOf<Function0<Unit>?>(null, null, null, null),
        arrayOf<Function0<Unit>?>(null, null, null, null)
    )

    private var currentState: OrderState = OrderState.WAIT_PAYMENT  //已待付款作为初始状态

    //事件：买家付款
    fun payment() {
        println("事件：买家付款")
        executeEvent(OrderEvent.PAYMENT)
        println("订单状态: ${currentState.desc}")
    }

    //事件：商家发货
    fun shipment() {
        println("事件：商家发货")
        executeEvent(OrderEvent.SHIPMENT)
        printState()
    }

    //事件：买家收货
    fun receipt() {
        println("事件：买家收货")
        executeEvent(OrderEvent.RECEIPT)
        printState()
    }

    //事件：买家/商家取消
    fun cancel() {
        println("事件：买家/商家取消")
        executeEvent(OrderEvent.CANCEL)
        printState()
    }

    private fun executeEvent(event: OrderEvent) {
        //执行动作
        STATE_ACTION_TABLE.getOrNull(currentState.value)?.getOrNull(event.value)?.invoke()

        val nextState = STATE_EVENT_TABLE.getOrNull(currentState.value)?.getOrNull(event.value)
        if (nextState != null) {
            currentState = nextState
        } else {
            println("${currentState.desc}不响应${event.desc}")
        }
    }

    fun printState() {
        println("订单状态: ${currentState.desc}")
    }
}
