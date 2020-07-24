import cz.vutbr.fit.knot.enticing.dto.config.dsl.LogType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.enticingConfiguration

val ENTICING_HOME = System.getenv("ENTICING_HOME")
        ?: throw IllegalStateException("ENTICING_HOME environment variable not set")

enticingConfiguration {
    localHome = ENTICING_HOME
    webserver {
        address = "athena10.fit.vutbr.cz"
    }

    management {
        address = "athena11.fit.vutbr.cz"
        heartbeat {
            period = 2_000
        }
    }

    logging {
        rootDirectory = "$ENTICING_HOME/logs"
        stdoutLogs(LogType.INFO, LogType.PERF, LogType.WARN, LogType.ERROR)
        fileLogs(LogType.INFO, LogType.PERF, LogType.WARN, LogType.ERROR)
        managementLogs {
            logTypes(LogType.PERF, LogType.WARN, LogType.ERROR)
        }
    }

    authentication {
        username = "xkozak15"
    }

    deployment {
        server = "athena10.fit.vutbr.cz"
        repository = "/mnt/minerva1/nlp/projects/corpproc_search/corpproc_search"
        configurationScript = "$repository/deploy/3.0_inheritance_small/config.kts"
    }

    corpusConfig {
        corpus("wiki-2018") {
            collectionsDir = "/mnt/data/indexes/xkozak15/wiki-2020-inheritance-small"
            serverFile("$ENTICING_HOME/deploy/3.0_inheritance_small/servers.txt")

            corpusSource {
                server = "minerva3.fit.vutbr.cz"
                directory = "/var/xdolez52/Zpracovani_Wikipedie/html_from_wikipedia_en_all_nopic_2019-10.zim/6-mg4j/old-2020-01-03"
                fileLimit = 50
            }

            metadata {
                indexes {
                    "position" whichIs "Position of the word in the document"
                    "token" whichIs "Original word in the document"
                    "tag" whichIs "tag"
                    "lemma" whichIs "Lemma of the word"
                    "parpos" whichIs "parpos"
                    "function" whichIs "function"
                    "parwrod" whichIs "parword"
                    "parlemma" whichIs "parlemma"
                    "paroffset" whichIs "paroffset"
                    "link" whichIs "link"
                    "length" whichIs "length"
                    "docuri" whichIs "docuri"
                    "lower" whichIs "lower"
                    "nerid" whichIs "nerid"
                    "nertag" whichIs "nertag"
                    attributeIndexes(10)
                    "nertype" whichIs "nertype"
                    "nerlength" whichIs "nerlength"
                    "_glue" whichIs "glue"
                }
                entities {

                    "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

                    entity("artist") {
                        parentEntityName = "person"
                    }

                    "location" with attributes("url", "image", "name", "country")

                    "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

                    "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

                    "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

                    "family" with attributes("url", "image", "name", "role", "nationality", "members")

                    "group" with attributes("url", "image", "name", "role", "nationality")

                    "nationality" with attributes("url", "image", "name", "country")

                    "date" with attributes("url", "image", "year", "month", "day")

                    "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

                    "film" with attributes("url", "image", "name")

                    "astronomicalObject" with attributes("url", "image", "name")

                    "structure" with attributes("url", "image", "name")

                    "activity" with attributes("url", "image", "name")

                    "taxon" with attributes("url", "image", "name")

                    "form" with attributes("url", "image", "name")

                    "medium" with attributes("url", "image", "name")

                    "mythology" with attributes("url", "image", "name")

                    "movement" with attributes("url", "image", "name")

                    "genre" with attributes("url", "image", "name")

                    extraAttributes("nertype", "nerlength")
                }
            }
        }
    }
}