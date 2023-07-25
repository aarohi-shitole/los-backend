package com.techvg.los.web.rest;

import com.techvg.los.repository.VoucherDetailsRepository;
import com.techvg.los.service.VoucherDetailsQueryService;
import com.techvg.los.service.VoucherDetailsService;
import com.techvg.los.service.criteria.VoucherDetailsCriteria;
import com.techvg.los.service.dto.VoucherDetailsDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.VoucherDetails}.
 */
@RestController
@RequestMapping("/api")
public class VoucherDetailsResource {

    private final Logger log = LoggerFactory.getLogger(VoucherDetailsResource.class);

    private static final String ENTITY_NAME = "voucherDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherDetailsService voucherDetailsService;

    private final VoucherDetailsRepository voucherDetailsRepository;

    private final VoucherDetailsQueryService voucherDetailsQueryService;

    public VoucherDetailsResource(
        VoucherDetailsService voucherDetailsService,
        VoucherDetailsRepository voucherDetailsRepository,
        VoucherDetailsQueryService voucherDetailsQueryService
    ) {
        this.voucherDetailsService = voucherDetailsService;
        this.voucherDetailsRepository = voucherDetailsRepository;
        this.voucherDetailsQueryService = voucherDetailsQueryService;
    }

    /**
     * {@code POST  /voucher-details} : Create a new voucherDetails.
     *
     * @param voucherDetailsDTO the voucherDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherDetailsDTO, or with status {@code 400 (Bad Request)} if the voucherDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/voucher-details")
    public ResponseEntity<VoucherDetailsDTO> createVoucherDetails(@RequestBody VoucherDetailsDTO voucherDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save VoucherDetails : {}", voucherDetailsDTO);
        if (voucherDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherDetailsDTO result = voucherDetailsService.save(voucherDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/voucher-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /voucher-details/:id} : Updates an existing voucherDetails.
     *
     * @param id the id of the voucherDetailsDTO to save.
     * @param voucherDetailsDTO the voucherDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the voucherDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/voucher-details/{id}")
    public ResponseEntity<VoucherDetailsDTO> updateVoucherDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherDetailsDTO voucherDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VoucherDetails : {}, {}", id, voucherDetailsDTO);
        if (voucherDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VoucherDetailsDTO result = voucherDetailsService.update(voucherDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voucherDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /voucher-details/:id} : Partial updates given fields of an existing voucherDetails, field will ignore if it is null
     *
     * @param id the id of the voucherDetailsDTO to save.
     * @param voucherDetailsDTO the voucherDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the voucherDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voucherDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/voucher-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoucherDetailsDTO> partialUpdateVoucherDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherDetailsDTO voucherDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VoucherDetails partially : {}, {}", id, voucherDetailsDTO);
        if (voucherDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherDetailsDTO> result = voucherDetailsService.partialUpdate(voucherDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voucherDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-details} : get all the voucherDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherDetails in body.
     */
    @GetMapping("/voucher-details")
    public ResponseEntity<List<VoucherDetailsDTO>> getAllVoucherDetails(
        VoucherDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VoucherDetails by criteria: {}", criteria);
        Page<VoucherDetailsDTO> page = voucherDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voucher-details/count} : count all the voucherDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/voucher-details/count")
    public ResponseEntity<Long> countVoucherDetails(VoucherDetailsCriteria criteria) {
        log.debug("REST request to count VoucherDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /voucher-details/:id} : get the "id" voucherDetails.
     *
     * @param id the id of the voucherDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/voucher-details/{id}")
    public ResponseEntity<VoucherDetailsDTO> getVoucherDetails(@PathVariable Long id) {
        log.debug("REST request to get VoucherDetails : {}", id);
        Optional<VoucherDetailsDTO> voucherDetailsDTO = voucherDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherDetailsDTO);
    }

    /**
     * {@code DELETE  /voucher-details/:id} : delete the "id" voucherDetails.
     *
     * @param id the id of the voucherDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/voucher-details/{id}")
    public ResponseEntity<Void> deleteVoucherDetails(@PathVariable Long id) {
        log.debug("REST request to delete VoucherDetails : {}", id);
        voucherDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
