package cz.vutbr.fit.knot.enticing.dto.utils.regex

const val urlRegexStr = """((([-\w]+\.)+[\w-]+)|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?([\w-/]*)${'$'}"""

val urlRegex = urlRegexStr.toRegex()
