package cz.vutbr.fit.knot.enticing.index.server.utils

import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

/**
 * convenience method, converts dto to json and adds proper content type
 */
fun MockHttpServletRequestBuilder.contentJson(dto: Any) = content(dto.toJson())
        .contentType(MediaType.APPLICATION_JSON)