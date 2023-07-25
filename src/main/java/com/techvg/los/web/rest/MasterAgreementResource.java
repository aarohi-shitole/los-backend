package com.techvg.los.web.rest;

import com.techvg.los.repository.MasterAgreementRepository;
import com.techvg.los.service.MasterAgreementQueryService;
import com.techvg.los.service.MasterAgreementService;
import com.techvg.los.service.criteria.MasterAgreementCriteria;
import com.techvg.los.service.dto.MasterAgreementDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.MasterAgreement}.
 */
@RestController
@RequestMapping("/api")
public class MasterAgreementResource {

    private final Logger log = LoggerFactory.getLogger(MasterAgreementResource.class);

    private static final String ENTITY_NAME = "masterAgreement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MasterAgreementService masterAgreementService;

    private final MasterAgreementRepository masterAgreementRepository;

    private final MasterAgreementQueryService masterAgreementQueryService;

    public MasterAgreementResource(
        MasterAgreementService masterAgreementService,
        MasterAgreementRepository masterAgreementRepository,
        MasterAgreementQueryService masterAgreementQueryService
    ) {
        this.masterAgreementService = masterAgreementService;
        this.masterAgreementRepository = masterAgreementRepository;
        this.masterAgreementQueryService = masterAgreementQueryService;
    }

    /**
     * {@code POST  /master-agreements} : Create a new masterAgreement.
     *
     * @param masterAgreementDTO the masterAgreementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterAgreementDTO, or with status {@code 400 (Bad Request)} if the masterAgreement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/master-agreements")
    public ResponseEntity<MasterAgreementDTO> createMasterAgreement(@RequestBody MasterAgreementDTO masterAgreementDTO)
        throws URISyntaxException {
        log.debug("REST request to save MasterAgreement : {}", masterAgreementDTO);
        if (masterAgreementDTO.getId() != null) {
            throw new BadRequestAlertException("A new masterAgreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MasterAgreementDTO result = masterAgreementService.save(masterAgreementDTO);
        return ResponseEntity
            .created(new URI("/api/master-agreements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /master-agreements/:id} : Updates an existing masterAgreement.
     *
     * @param id the id of the masterAgreementDTO to save.
     * @param masterAgreementDTO the masterAgreementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterAgreementDTO,
     * or with status {@code 400 (Bad Request)} if the masterAgreementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the masterAgreementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/master-agreements/{id}")
    public ResponseEntity<MasterAgreementDTO> updateMasterAgreement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterAgreementDTO masterAgreementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MasterAgreement : {}, {}", id, masterAgreementDTO);
        if (masterAgreementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterAgreementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterAgreementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MasterAgreementDTO result = masterAgreementService.update(masterAgreementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterAgreementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /master-agreements/:id} : Partial updates given fields of an existing masterAgreement, field will ignore if it is null
     *
     * @param id the id of the masterAgreementDTO to save.
     * @param masterAgreementDTO the masterAgreementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterAgreementDTO,
     * or with status {@code 400 (Bad Request)} if the masterAgreementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the masterAgreementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the masterAgreementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/master-agreements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MasterAgreementDTO> partialUpdateMasterAgreement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterAgreementDTO masterAgreementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MasterAgreement partially : {}, {}", id, masterAgreementDTO);
        if (masterAgreementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterAgreementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterAgreementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MasterAgreementDTO> result = masterAgreementService.partialUpdate(masterAgreementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterAgreementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /master-agreements} : get all the masterAgreements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masterAgreements in body.
     */
    @GetMapping("/master-agreements")
    public ResponseEntity<List<MasterAgreementDTO>> getAllMasterAgreements(
        MasterAgreementCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MasterAgreements by criteria: {}", criteria);
        Page<MasterAgreementDTO> page = masterAgreementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /master-agreements/count} : count all the masterAgreements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/master-agreements/count")
    public ResponseEntity<Long> countMasterAgreements(MasterAgreementCriteria criteria) {
        log.debug("REST request to count MasterAgreements by criteria: {}", criteria);
        return ResponseEntity.ok().body(masterAgreementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /master-agreements/:id} : get the "id" masterAgreement.
     *
     * @param id the id of the masterAgreementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterAgreementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/master-agreements/{id}")
    public ResponseEntity<MasterAgreementDTO> getMasterAgreement(@PathVariable Long id) {
        log.debug("REST request to get MasterAgreement : {}", id);
        Optional<MasterAgreementDTO> masterAgreementDTO = masterAgreementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(masterAgreementDTO);
    }

    /**
     * {@code DELETE  /master-agreements/:id} : delete the "id" masterAgreement.
     *
     * @param id the id of the masterAgreementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/master-agreements/{id}")
    public ResponseEntity<Void> deleteMasterAgreement(@PathVariable Long id) {
        log.debug("REST request to delete MasterAgreement : {}", id);
        masterAgreementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
