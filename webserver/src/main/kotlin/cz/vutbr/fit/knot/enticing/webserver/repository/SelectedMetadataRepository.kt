package cz.vutbr.fit.knot.enticing.webserver.repository

import cz.vutbr.fit.knot.enticing.webserver.entity.SelectedEntityMetadata
import cz.vutbr.fit.knot.enticing.webserver.entity.SelectedMetadata
import org.springframework.data.jpa.repository.JpaRepository

interface SelectedMetadataRepository : JpaRepository<SelectedMetadata, Long>

interface SelectedEntityMetadataRepository : JpaRepository<SelectedEntityMetadata, Long>