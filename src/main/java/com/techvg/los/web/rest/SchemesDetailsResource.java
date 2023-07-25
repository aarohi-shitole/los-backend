package com.techvg.los.web.rest;

import com.techvg.los.repository.SchemesDetailsRepository;
import com.techvg.los.service.SchemesDetailsQueryService;
import com.techvg.los.service.SchemesDetailsService;
import com.techvg.los.service.criteria.SchemesDetailsCriteria;
import com.techvg.los.service.dto.SchemesDetailsDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.SchemesDetails}.
 */
@RestController
@RequestMapping("/api")
public class SchemesDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SchemesDetailsResource.class);

    private static final String ENTITY_NAME = "schemesDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchemesDetailsService schemesDetailsService;

    private final SchemesDetailsRepository schemesDetailsRepository;

    private final SchemesDetailsQueryService schemesDetailsQueryService;

    public SchemesDetailsResource(
        SchemesDetailsService schemesDetailsService,
        SchemesDetailsRepository schemesDetailsRepository,
        SchemesDetailsQueryService schemesDetailsQueryService
    ) {
        this.schemesDetailsService = schemesDetailsService;
        this.schemesDetailsRepository = schemesDetailsRepository;
        this.schemesDetailsQueryService = schemesDetailsQueryService;
    }

    /**
     * {@code POST  /schemes-details} : Create a new schemesDetails.
     *
     * @param schemesDetailsDTO the schemesDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schemesDetailsDTO, or with status {@code 400 (Bad Request)} if the schemesDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schemes-details")
    public ResponseEntity<SchemesDetailsDTO> createSchemesDetails(@RequestBody SchemesDetailsDTO schemesDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save SchemesDetails : {}", schemesDetailsDTO);
        if (schemesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new schemesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemesDetailsDTO result = schemesDetailsService.save(schemesDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/schemes-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schemes-details/:id} : Updates an existing schemesDetails.
     *
     * @param id the id of the schemesDetailsDTO to save.
     * @param schemesDetailsDTO the schemesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schemesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the schemesDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schemesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schemes-details/{id}")
    public ResponseEntity<SchemesDetailsDTO> updateSchemesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchemesDetailsDTO schemesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SchemesDetails : {}, {}", id, schemesDetailsDTO);
        if (schemesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schemesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schemesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchemesDetailsDTO result = schemesDetailsService.update(schemesDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schemesDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /schemes-details/:id} : Partial updates given fields of an existing schemesDetails, field will ignore if it is null
     *
     * @param id the id of the schemesDetailsDTO to save.
     * @param schemesDetailsDTO the schemesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schemesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the schemesDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the schemesDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the schemesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/schemes-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchemesDetailsDTO> partialUpdateSchemesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SchemesDetailsDTO schemesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchemesDetails partially : {}, {}", id, schemesDetailsDTO);
        if (schemesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schemesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schemesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchemesDetailsDTO> result = schemesDetailsService.partialUpdate(schemesDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schemesDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /schemes-details} : get all the schemesDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schemesDetails in body.
     */
    @GetMapping("/schemes-details")
    public ResponseEntity<List<SchemesDetailsDTO>> getAllSchemesDetails(
        SchemesDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SchemesDetails by criteria: {}", criteria);
        Page<SchemesDetailsDTO> page = schemesDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schemes-details/count} : count all the schemesDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schemes-details/count")
    public ResponseEntity<Long> countSchemesDetails(SchemesDetailsCriteria criteria) {
        log.debug("REST request to count SchemesDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(schemesDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schemes-details/:id} : get the "id" schemesDetails.
     *
     * @param id the id of the schemesDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schemesDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schemes-details/{id}")
    public ResponseEntity<SchemesDetailsDTO> getSchemesDetails(@PathVariable Long id) {
        log.debug("REST request to get SchemesDetails : {}", id);
        Optional<SchemesDetailsDTO> schemesDetailsDTO = schemesDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schemesDetailsDTO);
    }

    /**
     * {@code DELETE  /schemes-details/:id} : delete the "id" schemesDetails.
     *
     * @param id the id of the schemesDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schemes-details/{id}")
    public ResponseEntity<Void> deleteSchemesDetails(@PathVariable Long id) {
        log.debug("REST request to delete SchemesDetails : {}", id);
        schemesDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
