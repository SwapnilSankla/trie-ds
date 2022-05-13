
fun main() {
    val words = Node::class.java.getResource("/words_alpha.txt")
        .readText()
        .split("\r\n")

    var trie = Node('/')
    words.forEach {
        trie.insert(it)
    }

    do {
        println("Enter input: ")
        val userInput = readln()
        println("Possible outputs ${trie.matchingWords(userInput)}")
        println("Do you want to continue? : ")
        val toBeContinued = readln()
    } while (toBeContinued == "y")
}

class Node(private val key: Char, var children: HashMap<Char, Node> = HashMap(), var completeWord: Boolean = false) {
    fun insert(word: String) {
        var currentNode = this
        for (i in word.indices) {
            if (!currentNode.children.contains(word[i])) {
                currentNode.children[word[i]] = Node(word[i])
            }
            currentNode = currentNode.children[word[i]]!!
        }
        currentNode.completeWord = true
    }

    fun matchingWords(inputString: String): List<String> {
        var currentNode = this
        if (children.none { child -> child.key == inputString[0] })
            return listOf()
        var i = 0
        while (i < inputString.length && currentNode.children.contains(inputString[i])) {
            currentNode = currentNode.children[inputString[i]]!!
            i++
        }

        return currentNode.matchingWordsFor(inputString.substring(0, i - 1))
    }

    private fun matchingWordsFor(
        prefix: String,
        matchingWords: MutableList<String> = mutableListOf()
    ): MutableList<String> {
        if (children.isEmpty()) {
            matchingWords.add(prefix + key)
            return matchingWords
        }
        if (completeWord)
            matchingWords.add(prefix + key)

        children.forEach { it.value.matchingWordsFor(prefix + key, matchingWords) }
        return matchingWords
    }
}