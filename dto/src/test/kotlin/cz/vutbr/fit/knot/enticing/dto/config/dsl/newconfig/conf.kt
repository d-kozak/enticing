package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

val fullConf = enticingConfiguration {
    webserver {
        address = "localhost"
    }

    management {
        hearthBeat {
            period = 2_000
        }
    }

    corpusConfig {
        corpus("wiki-2018") {
            mg4jDir = "location of mg4j files"
            indexedDir = "location of the indexed metadata for the mg4j files"
            serverFile("./src/test/resources/servers.txt")

            metadata {
                indexes {
                    index("position")
                    index("word")
                    attributeIndexes(3)
                }
                entities {
                    entity("person") {
                        attributes {
                            attribute("name")
                        }
                    }
                    entity("place") {
                        attributes("name", "location")
                    }

                    extraAttributes("position")

                    entityIndexName = "nertag"
                    lengthIndexName = "nerlength"
                }
            }
        }
    }
}