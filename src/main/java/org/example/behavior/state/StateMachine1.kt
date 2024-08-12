package org.example.behavior.state

/**
 * 订单状态机：使用分支逻辑法
 */

fun main() {
    println("流程一：")
    val stateMachine1 = OrderStateMachine()
    stateMachine1.payment()
    stateMachine1.shipment()
    stateMachine1.receipt()
    println("")

    println("流程二：")
    val stateMachine2 = OrderStateMachine()
    stateMachine2.cancel()
    println("")

    println("流程三：")
    val stateMachine3 = OrderStateMachine()
    stateMachine3.payment()
    stateMachine3.cancel()
    stateMachine3.shipment()
    println("")
}

//订单状态：
enum class OrderState(val value: Int, val desc: String) {
    WAIT_PAYMENT(0, "待付款"),
    WAIT_SHIPMENT(1, "待发货"),
    WAIT_RECEIPT(2, "待收货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消")
}

//触发动作：
public object ActionGroup {
    fun moneyToPlatform() {
        println("动作：付款给平台...")
    }

    fun moneyToSeller() {
        println("动作：金额打给商家...")
    }

    fun moneyToBuyer() {
        println("动作：金额退还给买家...")
    }
}

//状态机：
class OrderStateMachine {
    private var currentState: OrderState = OrderState.WAIT_PAYMENT  //已待付款作为初始状态

    //事件：买家付款
    fun payment() {
        println("事件：买家付款")
        when (currentState) {
            OrderState.WAIT_PAYMENT -> {
                ActionGroup.moneyToPlatform()
                currentState = OrderState.WAIT_SHIPMENT
            }

            OrderState.WAIT_SHIPMENT, OrderState.WAIT_RECEIPT, OrderState.COMPLETED, OrderState.CANCELLED -> {
                println("待发货、待收货、已完成和已取消的订单不用付款...")
            }
        }
        println("订单状态: ${currentState.desc}")
    }

    //事件：商家发货
    fun shipment() {
        println("事件：商家发货")
        when (currentState) {
            OrderState.WAIT_SHIPMENT -> {
                currentState = OrderState.WAIT_RECEIPT
            }

            OrderState.WAIT_PAYMENT, OrderState.WAIT_RECEIPT, OrderState.COMPLETED, OrderState.CANCELLED -> {
                println("待付款、待收货、已完成和已取消的订单不用发货...")
            }
        }
        printState()
    }

    //事件：买家收货
    fun receipt() {
        println("事件：买家收货")
        when (currentState) {
            OrderState.WAIT_RECEIPT -> {
                ActionGroup.moneyToSeller()
                currentState = OrderState.COMPLETED
            }

            OrderState.WAIT_PAYMENT, OrderState.WAIT_SHIPMENT, OrderState.COMPLETED, OrderState.CANCELLED -> {
                println("待付款，待发货、已完成和已取消的订单不用收货...")
            }
        }
        printState()
    }

    //事件：买家/商家取消
    fun cancel() {
        println("事件：买家/商家取消")
        when (currentState) {
            OrderState.WAIT_PAYMENT -> {
                currentState = OrderState.CANCELLED
            }

            OrderState.WAIT_SHIPMENT -> {
                ActionGroup.moneyToBuyer()
                currentState = OrderState.CANCELLED
            }

            OrderState.WAIT_RECEIPT, OrderState.COMPLETED, OrderState.CANCELLED -> {
                println("待收货、已完成和已取消的订单不能取消...")
            }
        }
        printState()
    }

    fun printState() {
        println("订单状态: ${currentState.desc}")
    }

}
