package com.techvg.los.web.rest;

import com.techvg.los.repository.VouchersHistoryRepository;
import com.techvg.los.service.VouchersHistoryQueryService;
import com.techvg.los.service.VouchersHistoryService;
import com.techvg.los.service.criteria.VouchersHistoryCriteria;
import com.techvg.los.service.dto.VouchersHistoryDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.VouchersHistory}.
 */
@RestController
@RequestMapping("/api")
public class VouchersHistoryResource {

    private final Logger log = LoggerFactory.getLogger(VouchersHistoryResource.class);

    private static final String ENTITY_NAME = "vouchersHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VouchersHistoryService vouchersHistoryService;

    private final VouchersHistoryRepository vouchersHistoryRepository;

    private final VouchersHistoryQueryService vouchersHistoryQueryService;

    public VouchersHistoryResource(
        VouchersHistoryService vouchersHistoryService,
        VouchersHistoryRepository vouchersHistoryRepository,
        VouchersHistoryQueryService vouchersHistoryQueryService
    ) {
        this.vouchersHistoryService = vouchersHistoryService;
        this.vouchersHistoryRepository = vouchersHistoryRepository;
        this.vouchersHistoryQueryService = vouchersHistoryQueryService;
    }

    /**
     * {@code POST  /vouchers-histories} : Create a new vouchersHistory.
     *
     * @param vouchersHistoryDTO the vouchersHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vouchersHistoryDTO, or with status {@code 400 (Bad Request)} if the vouchersHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vouchers-histories")
    public ResponseEntity<VouchersHistoryDTO> createVouchersHistory(@RequestBody VouchersHistoryDTO vouchersHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save VouchersHistory : {}", vouchersHistoryDTO);
        if (vouchersHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new vouchersHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VouchersHistoryDTO result = vouchersHistoryService.save(vouchersHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/vouchers-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vouchers-histories/:id} : Updates an existing vouchersHistory.
     *
     * @param id the id of the vouchersHistoryDTO to save.
     * @param vouchersHistoryDTO the vouchersHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vouchersHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the vouchersHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vouchersHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vouchers-histories/{id}")
    public ResponseEntity<VouchersHistoryDTO> updateVouchersHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VouchersHistoryDTO vouchersHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VouchersHistory : {}, {}", id, vouchersHistoryDTO);
        if (vouchersHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vouchersHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vouchersHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VouchersHistoryDTO result = vouchersHistoryService.update(vouchersHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vouchersHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vouchers-histories/:id} : Partial updates given fields of an existing vouchersHistory, field will ignore if it is null
     *
     * @param id the id of the vouchersHistoryDTO to save.
     * @param vouchersHistoryDTO the vouchersHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vouchersHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the vouchersHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vouchersHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vouchersHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vouchers-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VouchersHistoryDTO> partialUpdateVouchersHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VouchersHistoryDTO vouchersHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VouchersHistory partially : {}, {}", id, vouchersHistoryDTO);
        if (vouchersHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vouchersHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vouchersHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VouchersHistoryDTO> result = vouchersHistoryService.partialUpdate(vouchersHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vouchersHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vouchers-histories} : get all the vouchersHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vouchersHistories in body.
     */
    @GetMapping("/vouchers-histories")
    public ResponseEntity<List<VouchersHistoryDTO>> getAllVouchersHistories(
        VouchersHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VouchersHistories by criteria: {}", criteria);
        Page<VouchersHistoryDTO> page = vouchersHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vouchers-histories/count} : count all the vouchersHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vouchers-histories/count")
    public ResponseEntity<Long> countVouchersHistories(VouchersHistoryCriteria criteria) {
        log.debug("REST request to count VouchersHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(vouchersHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vouchers-histories/:id} : get the "id" vouchersHistory.
     *
     * @param id the id of the vouchersHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vouchersHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vouchers-histories/{id}")
    public ResponseEntity<VouchersHistoryDTO> getVouchersHistory(@PathVariable Long id) {
        log.debug("REST request to get VouchersHistory : {}", id);
        Optional<VouchersHistoryDTO> vouchersHistoryDTO = vouchersHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vouchersHistoryDTO);
    }

    /**
     * {@code DELETE  /vouchers-histories/:id} : delete the "id" vouchersHistory.
     *
     * @param id the id of the vouchersHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vouchers-histories/{id}")
    public ResponseEntity<Void> deleteVouchersHistory(@PathVariable Long id) {
        log.debug("REST request to delete VouchersHistory : {}", id);
        vouchersHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
