import java.math.BigDecimal

private lateinit var gElement: BigDecimal

fun main() {
    print("Введите Z: ")
    val group = BigDecimal(readlnOrNull())

    print("Введите g: ")
    gElement = BigDecimal(readlnOrNull())

    val addPrimeFactors: Array<MutableList<Long>> = primeFactors(group)
    val multiPrimeFactors: Array<MutableList<Long>> = primeFactors(gcdCount(group).toBigDecimal())

    val resultAdd = addOrder(addPrimeFactors, group.toLong())
    val resultMulti = multiOrder(multiPrimeFactors, group.toLong())

    result(resultAdd, resultMulti, group)
}

fun result(resultAdd: Pair<Int, Int>, resultMulti: Pair<Int, Int>? = null, group: BigDecimal) {
    println("Z($group, +)\n n = ${resultAdd.second}\n ord$gElement = ${resultAdd.first}\n")
    println("Z($group, *)\n n = ${resultMulti?.second}\n ord$gElement = ${resultMulti?.first}\n")
}

fun addOrder(primeFactors: Array<MutableList<Long>>, group: Long): Pair<Int, Int> {
    var i = 0
    var d = group.toDouble()
    val n = d
    var b: Double

    while (i < primeFactors[0].size) {

        d = (d.toLong() / binaryPower(
            primeFactors[0][i],
            primeFactors[1][i]
        )) % group
        b = (gElement.toLong() * d) % group
        while (b.toInt() != 1 && b.toInt() != 0 && d.toInt() != 0) {
            b = (b * primeFactors[0][i]) % group
            d = (d * primeFactors[0][i]) % group
        }
        i++
    }
    return d.toInt() to n.toInt()

}

fun multiOrder(primeFactors: Array<MutableList<Long>>, group: Long): Pair<Int, Int> {
    var i = 0
    var d = gcdCount(group.toBigDecimal())
    val n = d
    var b: Double
    if (gcdBySteinsAlgorithm(gElement.toInt(), group.toInt()) != 1) {
        return 0 to n.toInt()
    }
    while (i < primeFactors[0].size) {

        d = (d.toLong() / binaryPower(
            primeFactors[0][i],
            primeFactors[1][i]
        )) % group
        b = binaryPower(gElement.toLong(), d.toLong()) % group
        while (b.toInt() != 1 && b.toInt() != 0 && d.toInt() != 0) {
            b = binaryPower(b.toLong(), primeFactors[0][i]) % group
            d = (d * primeFactors[0][i]) % group
        }
        i++
    }
    return d.toInt() to n.toInt()
}

fun gcdBySteinsAlgorithm(n1: Int, n2: Int): Int {
    var n1 = n1
    var n2 = n2
    if (n1 == 0) {
        return n2
    }
    if (n2 == 0) {
        return n1
    }
    var n = 0
    while (n1 or n2 and 1 == 0) {
        n1 = n1 shr 1
        n2 = n2 shr 1
        n++
    }
    while (n1 and 1 == 0) {
        n1 = n1 shr 1
    }
    do {
        while (n2 and 1 == 0) {
            n2 = n2 shr 1
        }
        if (n1 > n2) {
            val temp = n1
            n1 = n2
            n2 = temp
        }
        n2 -= n1
    } while (n2 != 0)
    return n1 shl n
}

fun gcdCount(group: BigDecimal, g: Int? = null): Double {
    var count = 0.0
    for (i in 1 until group.toInt()) {
        if (gcdBySteinsAlgorithm(i, group.toInt()) == 1) {
            count++
        }
    }
    return count
}

fun primeFactors(number: BigDecimal): Array<MutableList<Long>> {
    val primeFactors: ArrayList<Long> = ArrayList()
    val power: MutableList<Long> = ArrayList()
    var copyOfInput = number.toLong()
    var i: Long = 2
    while (i <= copyOfInput) {
        if (copyOfInput % i == 0L) {
            if (primeFactors.isNotEmpty() && i == primeFactors.last()) {
                power[power.lastIndex]++
            } else {
                primeFactors.add(i)
                power.add(1)
            }
            copyOfInput /= i
            i--
        }
        i++
    }
    return arrayOf(primeFactors, power)
}


fun binaryPower(number: Long, power: Long): Double {
    var num = number
    var pow = power
    var result = 1.0
    while (pow > 0) {
        if (pow and 1L != 0L) result *= num
        num *= num
        pow = pow shr 1
    }
    return result
}
