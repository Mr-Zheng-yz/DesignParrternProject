package org.example.create.prototype

import java.io.Serializable

data class User(var name: String, var age: Int, val address: Address): Cloneable, Serializable {
    public override fun clone(): Any {
        return User(name, age, address.clone() as Address)
    }
}

data class Address(var street: String, var city: String) : Cloneable {
    public override fun clone(): Any {
        return Address(street, city)
    }
}
