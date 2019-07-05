package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.index.config.dsl.FieldType
import it.unimi.di.big.mg4j.document.DocumentFactory

val FieldType.mg4jType: DocumentFactory.FieldType
    get() = when (this) {
        FieldType.Text -> DocumentFactory.FieldType.TEXT
        FieldType.Int -> DocumentFactory.FieldType.INT
        FieldType.Date -> DocumentFactory.FieldType.DATE
    }