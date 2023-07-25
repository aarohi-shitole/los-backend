package com.techvg.los.web.rest;

import com.techvg.los.repository.SequenceGeneratorRepository;
import com.techvg.los.service.SequenceGeneratorQueryService;
import com.techvg.los.service.SequenceGeneratorService;
import com.techvg.los.service.criteria.SequenceGeneratorCriteria;
import com.techvg.los.service.dto.SequenceGeneratorDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.SequenceGenerator}.
 */
@RestController
@RequestMapping("/api")
public class SequenceGeneratorResource {

    private final Logger log = LoggerFactory.getLogger(SequenceGeneratorResource.class);

    private static final String ENTITY_NAME = "sequenceGenerator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceGeneratorService sequenceGeneratorService;

    private final SequenceGeneratorRepository sequenceGeneratorRepository;

    private final SequenceGeneratorQueryService sequenceGeneratorQueryService;

    public SequenceGeneratorResource(
        SequenceGeneratorService sequenceGeneratorService,
        SequenceGeneratorRepository sequenceGeneratorRepository,
        SequenceGeneratorQueryService sequenceGeneratorQueryService
    ) {
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.sequenceGeneratorRepository = sequenceGeneratorRepository;
        this.sequenceGeneratorQueryService = sequenceGeneratorQueryService;
    }

    /**
     * {@code POST  /sequence-generators} : Create a new sequenceGenerator.
     *
     * @param sequenceGeneratorDTO the sequenceGeneratorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceGeneratorDTO, or with status {@code 400 (Bad Request)} if the sequenceGenerator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sequence-generators")
    public ResponseEntity<SequenceGeneratorDTO> createSequenceGenerator(@RequestBody SequenceGeneratorDTO sequenceGeneratorDTO)
        throws URISyntaxException {
        log.debug("REST request to save SequenceGenerator : {}", sequenceGeneratorDTO);
        if (sequenceGeneratorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceGenerator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceGeneratorDTO result = sequenceGeneratorService.save(sequenceGeneratorDTO);
        return ResponseEntity
            .created(new URI("/api/sequence-generators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-generators/:id} : Updates an existing sequenceGenerator.
     *
     * @param id the id of the sequenceGeneratorDTO to save.
     * @param sequenceGeneratorDTO the sequenceGeneratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceGeneratorDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceGeneratorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceGeneratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sequence-generators/{id}")
    public ResponseEntity<SequenceGeneratorDTO> updateSequenceGenerator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SequenceGeneratorDTO sequenceGeneratorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SequenceGenerator : {}, {}", id, sequenceGeneratorDTO);
        if (sequenceGeneratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceGeneratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceGeneratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SequenceGeneratorDTO result = sequenceGeneratorService.update(sequenceGeneratorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceGeneratorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sequence-generators/:id} : Partial updates given fields of an existing sequenceGenerator, field will ignore if it is null
     *
     * @param id the id of the sequenceGeneratorDTO to save.
     * @param sequenceGeneratorDTO the sequenceGeneratorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceGeneratorDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceGeneratorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sequenceGeneratorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sequenceGeneratorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sequence-generators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SequenceGeneratorDTO> partialUpdateSequenceGenerator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SequenceGeneratorDTO sequenceGeneratorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SequenceGenerator partially : {}, {}", id, sequenceGeneratorDTO);
        if (sequenceGeneratorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sequenceGeneratorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sequenceGeneratorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SequenceGeneratorDTO> result = sequenceGeneratorService.partialUpdate(sequenceGeneratorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceGeneratorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sequence-generators} : get all the sequenceGenerators.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceGenerators in body.
     */
    @GetMapping("/sequence-generators")
    public ResponseEntity<List<SequenceGeneratorDTO>> getAllSequenceGenerators(
        SequenceGeneratorCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SequenceGenerators by criteria: {}", criteria);
        Page<SequenceGeneratorDTO> page = sequenceGeneratorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-generators/count} : count all the sequenceGenerators.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sequence-generators/count")
    public ResponseEntity<Long> countSequenceGenerators(SequenceGeneratorCriteria criteria) {
        log.debug("REST request to count SequenceGenerators by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceGeneratorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-generators/:id} : get the "id" sequenceGenerator.
     *
     * @param id the id of the sequenceGeneratorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceGeneratorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sequence-generators/{id}")
    public ResponseEntity<SequenceGeneratorDTO> getSequenceGenerator(@PathVariable Long id) {
        log.debug("REST request to get SequenceGenerator : {}", id);
        Optional<SequenceGeneratorDTO> sequenceGeneratorDTO = sequenceGeneratorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceGeneratorDTO);
    }

    /**
     * {@code DELETE  /sequence-generators/:id} : delete the "id" sequenceGenerator.
     *
     * @param id the id of the sequenceGeneratorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sequence-generators/{id}")
    public ResponseEntity<Void> deleteSequenceGenerator(@PathVariable Long id) {
        log.debug("REST request to delete SequenceGenerator : {}", id);
        sequenceGeneratorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
