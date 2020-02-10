package cz.vutbr.fit.knot.enticing.index.client


//class HandleArgumentsTest {
// todo fix
//    companion object {
//        @BeforeAll
//        @JvmStatic
//        internal fun initConfig() {
//            // @cleanup it it necessary to have the indexed data in place for the console client to work, but there is no gradle
//            // dependency that would require index-builder tests to execute before console-client, there should be a better way, but currently
//            // it is solved by executing the indexing from here
//            startIndexing(executeScript<IndexBuilderConfig>("../index-builder/src/test/resources/indexer.config.kts").also { it.validate() })
//
//            val wholeConfig = executeScript<ConsoleClientConfig>("src/test/resources/client.config.local.kts").also { it.validate() }
//        }
//    }
//
//    @Test
//    fun `just config file`() {
//        val config = handleArguments(arrayOf("src/test/resources/client.config.local.kts"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//    }
//
//    @Test
//    fun `config file and query`() {
//        val query = "hello darkness, my old friend"
//        val config = handleArguments(arrayOf("src/test/resources/client.config.local.kts", query))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.query).isEqualTo(query)
//    }
//
//    @Test
//    fun `text format html anhd config file`() {
//        val config = handleArguments(arrayOf("-t", "HTML", "src/test/resources/client.config.local.kts"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.HTML)
//    }
//
//    @Test
//    fun `text format plain and config file`() {
//        val config = handleArguments(arrayOf("-t", "PLAIN_TEXT", "src/test/resources/client.config.local.kts"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
//    }
//
//    @Test
//    fun `result format IDENTIFIER_LIST and config file`() {
//        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "src/test/resources/client.config.local.kts"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
//    }
//
//    @Test
//    fun `result format text format and output file`() {
//        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
//        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
//        assertThat(config.searchConfig.outputFile).isEqualTo(File("out"))
//    }
//
//    @Test
//    fun `result format text format and output file and query`() {
//        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts", "foo"))
//        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
//        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
//        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
//        assertThat(config.searchConfig.outputFile).isEqualTo(File("out"))
//        assertThat(config.searchConfig.query).isEqualTo("foo")
//    }
//
//    @Test
//    fun `missing result type`() {
//        assertThrows<IllegalArgumentException> { handleArguments(arrayOf("-r", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts", "foo")) }
//    }
//
//    @Test
//    fun `missing text type`() {
//        assertThrows<IllegalArgumentException> { handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "-f", "out", "src/test/resources/client.config.local.kts", "foo")) }
//    }
//
//    @Test
//    fun `missing config file`() {
//        assertThrows<FileNotFoundException> { handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "foo")) }
//    }
//
//}