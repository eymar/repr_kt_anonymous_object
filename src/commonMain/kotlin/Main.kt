fun interface FunInterfaceAbc {
    fun abc(i: Int)
}

fun takeInAbc(funInterfaceAbc: FunInterfaceAbc) {
    funInterfaceAbc.abc(300)
}

fun takeInAbcLambda(abcLambda: (Int) -> Unit) {
    abcLambda(200)
}

interface InterfaceAbc {
    fun abc(i: Int)
}

fun takeInIAbc(abc: InterfaceAbc) {
    abc.abc(100)
}

// Common

inline fun <T> myremember(calculation: () -> T): T {
    return calculation()
}

fun Foo() {
    val callable = myremember {
        fun localAnonymousFun() {
            // fails on k/js and k/native
            takeInIAbc(object : InterfaceAbc {
                override fun abc(i: Int) {
                    println("Iabc = $i")
                }
            })

            // works on all targets
            // takeInAbc { println("ABC = $it") }
            // takeInAbcLambda { println("AbcLambda = $it") }

        }
        ::localAnonymousFun
    }

    callable()
}

fun main() {
   Foo()
}
