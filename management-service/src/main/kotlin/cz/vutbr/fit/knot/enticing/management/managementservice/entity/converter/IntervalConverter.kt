package cz.vutbr.fit.knot.enticing.management.managementservice.entity.converter

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class IntervalConverter : AttributeConverter<Interval, String> {

    override fun convertToDatabaseColumn(attribute: Interval): String = attribute.toJson()

    override fun convertToEntityAttribute(dbData: String): Interval = dbData.toDto()
}