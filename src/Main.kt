/**
 * Created by MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 9/8/17.
 */

val possibleTreasures = mutableMapOf<Int, MutableSet<Set<String>>>()
val max = 101

val treasures = hashMapOf(
        12 to setOf("got-arrested"),
        11 to setOf("made-out-in-public"),
        10 to setOf("sex"),
        9 to setOf("kiss-same-gender", "done-drugs", "suspended-in-college"),
        7 to setOf("cried", "heartbroken"),
        6 to setOf("smoked", "kiss-opposite-gender", "fist-fight"),
        3 to setOf("stole-something"),
        2 to setOf("got-drunk", "been-in-love"),
        1 to setOf("peed–in-pool")
)

fun searchValue(treasure: String) : Int {
    var value = -1
    treasures.forEach {
        if (it.value.contains(treasure)) {
            value = it.key
        }
    }
    return value
}

fun findPossibleTreasures() {

    (0 until max).forEach { score ->
        possibleTreasures[score] = mutableSetOf()
        treasures[score]?.forEach { treasure ->
            possibleTreasures[score]!!.add(setOf(treasure))
        }
    }

    (1 until max).forEach { score ->
        (score-1 downTo 0).forEach { value ->
            treasures[value]?.forEach { newTreasure ->
                possibleTreasures[score - value]?.filter {
                    !it.contains(newTreasure)
                }?.forEach { previousTreasures ->
                    val newTreasureBox = previousTreasures.plus(newTreasure)
                    possibleTreasures[score]!!.add(newTreasureBox)
                }
            }
        }
    }

}

fun main(args: Array<String>) {

    print("Score? ")
    findPossibleTreasures()
    val score = readLine()!!.toInt()

    val possiblesBoxes =
            if (possibleTreasures[score] == null) mutableSetOf()
            else possibleTreasures[score]!!

    possiblesBoxes.distinct()
            .sortedWith(compareBy { it.size })
            .forEachIndexed { index, treasureBox ->
                print("${index+1}: ")
                treasureBox.sortedWith(
                        compareBy {
                            searchValue(it)
                        }
                ).reversed().forEach {
                    print("$it ")
                }
                println()
            }

}