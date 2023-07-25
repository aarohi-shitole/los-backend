package com.techvg.los.web.rest;

import com.techvg.los.repository.DeclearationRepository;
import com.techvg.los.service.DeclearationQueryService;
import com.techvg.los.service.DeclearationService;
import com.techvg.los.service.criteria.DeclearationCriteria;
import com.techvg.los.service.dto.DeclearationDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.Declearation}.
 */
@RestController
@RequestMapping("/api")
public class DeclearationResource {

    private final Logger log = LoggerFactory.getLogger(DeclearationResource.class);

    private static final String ENTITY_NAME = "declearation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeclearationService declearationService;

    private final DeclearationRepository declearationRepository;

    private final DeclearationQueryService declearationQueryService;

    public DeclearationResource(
        DeclearationService declearationService,
        DeclearationRepository declearationRepository,
        DeclearationQueryService declearationQueryService
    ) {
        this.declearationService = declearationService;
        this.declearationRepository = declearationRepository;
        this.declearationQueryService = declearationQueryService;
    }

    /**
     * {@code POST  /declearations} : Create a new declearation.
     *
     * @param declearationDTO the declearationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new declearationDTO, or with status {@code 400 (Bad Request)} if the declearation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/declearations")
    public ResponseEntity<DeclearationDTO> createDeclearation(@RequestBody DeclearationDTO declearationDTO) throws URISyntaxException {
        log.debug("REST request to save Declearation : {}", declearationDTO);
        if (declearationDTO.getId() != null) {
            throw new BadRequestAlertException("A new declearation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeclearationDTO result = declearationService.save(declearationDTO);
        return ResponseEntity
            .created(new URI("/api/declearations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /declearations/:id} : Updates an existing declearation.
     *
     * @param id the id of the declearationDTO to save.
     * @param declearationDTO the declearationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declearationDTO,
     * or with status {@code 400 (Bad Request)} if the declearationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the declearationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/declearations/{id}")
    public ResponseEntity<DeclearationDTO> updateDeclearation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeclearationDTO declearationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Declearation : {}, {}", id, declearationDTO);
        if (declearationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declearationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declearationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeclearationDTO result = declearationService.update(declearationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, declearationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /declearations/:id} : Partial updates given fields of an existing declearation, field will ignore if it is null
     *
     * @param id the id of the declearationDTO to save.
     * @param declearationDTO the declearationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declearationDTO,
     * or with status {@code 400 (Bad Request)} if the declearationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the declearationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the declearationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/declearations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeclearationDTO> partialUpdateDeclearation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeclearationDTO declearationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Declearation partially : {}, {}", id, declearationDTO);
        if (declearationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declearationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declearationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeclearationDTO> result = declearationService.partialUpdate(declearationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, declearationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /declearations} : get all the declearations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of declearations in body.
     */
    @GetMapping("/declearations")
    public ResponseEntity<List<DeclearationDTO>> getAllDeclearations(
        DeclearationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Declearations by criteria: {}", criteria);
        Page<DeclearationDTO> page = declearationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /declearations/count} : count all the declearations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/declearations/count")
    public ResponseEntity<Long> countDeclearations(DeclearationCriteria criteria) {
        log.debug("REST request to count Declearations by criteria: {}", criteria);
        return ResponseEntity.ok().body(declearationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /declearations/:id} : get the "id" declearation.
     *
     * @param id the id of the declearationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the declearationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/declearations/{id}")
    public ResponseEntity<DeclearationDTO> getDeclearation(@PathVariable Long id) {
        log.debug("REST request to get Declearation : {}", id);
        Optional<DeclearationDTO> declearationDTO = declearationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(declearationDTO);
    }

    /**
     * {@code DELETE  /declearations/:id} : delete the "id" declearation.
     *
     * @param id the id of the declearationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/declearations/{id}")
    public ResponseEntity<Void> deleteDeclearation(@PathVariable Long id) {
        log.debug("REST request to delete Declearation : {}", id);
        declearationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
