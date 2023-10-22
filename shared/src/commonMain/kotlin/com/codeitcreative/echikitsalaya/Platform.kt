package com.codeitcreative.echikitsalaya

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform