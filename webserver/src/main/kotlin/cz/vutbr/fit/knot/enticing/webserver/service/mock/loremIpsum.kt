package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata


val loremOneSentence = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit."

val documentText = """
    Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed vel lectus. Donec odio tempus molestie, porttitor ut, iaculis quis, sem. Integer imperdiet lectus quis justo. In enim a arcu imperdiet malesuada. Fusce suscipit libero eget elit. Etiam bibendum elit eget erat. Maecenas libero. Aenean fermentum risus id tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam faucibus mi quis velit. Praesent dapibus. Maecenas aliquet accumsan leo. Vivamus ac leo pretium faucibus.

Donec ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Nullam lectus justo, vulputate eget mollis sed, tempor sed magna. Nunc dapibus tortor vel mi dapibus sollicitudin. Maecenas sollicitudin. In convallis. Nullam at arcu a est sollicitudin euismod. Integer malesuada. Nunc dapibus tortor vel mi dapibus sollicitudin. Vestibulum erat nulla, ullamcorper nec, rutrum non, nonummy ac, erat. Sed vel lectus. Donec odio tempus molestie, porttitor ut, iaculis quis, sem. Sed convallis magna eu sem. Aliquam id dolor. In sem justo, commodo ut, suscipit at, pharetra vitae, orci. Fusce aliquam vestibulum ipsum. Mauris dictum facilisis augue. Nam quis nulla. Aliquam ante. Curabitur vitae diam non enim vestibulum interdum. Et harum quidem rerum facilis est et expedita distinctio.

Curabitur sagittis hendrerit ante. Etiam posuere lacus quis dolor. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Fusce aliquam vestibulum ipsum. Pellentesque arcu. Maecenas fermentum, sem in pharetra pellentesque, velit turpis volutpat ante, in pharetra metus odio a lectus. Integer rutrum, orci vestibulum ullamcorper ultricies, lacus quam ultricies odio, vitae placerat pede sem sit amet enim. Etiam posuere lacus quis dolor. Integer tempor. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Vivamus luctus egestas leo. Integer lacinia. Aliquam id dolor. Nullam feugiat, turpis at pulvinar vulputate, erat libero tristique tellus, nec bibendum odio risus sit amet ante. Nullam faucibus mi quis velit. Sed elit dui, pellentesque a, faucibus vel, interdum nec, diam. In rutrum.

Nullam sapien sem, ornare ac, nonummy non, lobortis a enim. Mauris dolor felis, sagittis at, luctus sed, aliquam non, tellus. Integer pellentesque quam vel velit. Praesent dapibus. Curabitur vitae diam non enim vestibulum interdum. Fusce consectetuer risus a nunc. Curabitur ligula sapien, pulvinar a vestibulum quis, facilisis vel sapien. Duis risus. Sed ac dolor sit amet purus malesuada congue. Etiam posuere lacus quis dolor. Fusce suscipit libero eget elit. Fusce tellus.

Curabitur bibendum justo non orci. Donec ipsum massa, ullamcorper in, auctor et, scelerisque sed, est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean fermentum risus id tortor. Aliquam erat volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec iaculis gravida nulla. Cras pede libero, dapibus nec, pretium sit amet, tempor quis. Etiam dictum tincidunt diam. In enim a arcu imperdiet malesuada. Suspendisse nisl. Nullam faucibus mi quis velit. Nulla non lectus sed nisl molestie malesuada. Nulla turpis magna, cursus sit amet, suscipit a, interdum id, felis. Mauris metus. Etiam egestas wisi a erat.
""".trimIndent()


val dummyDocument = WebServer.FullDocument(
        "google.com",
        "col1",
        1,
        "Document One",
        "https://www.google.com",
        ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(
                documentText,
                emptyMap(),
                emptyList(),
                emptyList()
        ))
)