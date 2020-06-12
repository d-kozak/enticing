import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode

File("second_experiment.csv")
        .readLines()
        .drop(1)
        .map { it.split(",") }
        .map { it.subList(0, 1) + it.subList(2, it.size) }
        .map {
            it.mapIndexed { i, str ->
                when (i) {
                    0 -> str.replace("&", """\&""")
                    in 1..2 -> BigDecimal.valueOf(str.toDouble()).setScale(2, RoundingMode.HALF_UP).toString()
                    else -> str
                }
            }
        }
        .map { it.joinToString(" & ", postfix = """ \\ \hline""") }
        .forEach(::println)