package cz.vutbr.fit.knot.enticing.management.managementservice.controller

import cz.vutbr.fit.knot.enticing.management.managementservice.dto.Corpus
import cz.vutbr.fit.knot.enticing.management.managementservice.service.CorpusService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.base.path}/corpus")
class CorpusController(
        private val corpusService: CorpusService
) {

    @GetMapping
    fun getAll(pageable: Pageable) = corpusService.getAll(pageable)

    @PostMapping
    fun addNew(@RequestBody corpus: Corpus) = corpusService.addNew(corpus)

    @GetMapping("/{corpusId}")
    fun getOne(@PathVariable corpusId: Long) = corpusService.getOne(corpusId)

    @DeleteMapping("/{corpusId}")
    fun deleteOne(@PathVariable corpusId: Long) = corpusService.deleteById(corpusId)


    @PutMapping("/{corpusId}/{componentId}")
    fun addComponentToCorpus(@PathVariable corpusId: Long, @PathVariable componentId: Long) = corpusService.addComponentToCorpus(componentId, corpusId)

    @DeleteMapping("/{corpusId}/{componentId}")
    fun removeComponentToCorpus(@PathVariable corpusId: Long, @PathVariable componentId: Long) = corpusService.removeComponentFromCorpus(componentId, corpusId)
}