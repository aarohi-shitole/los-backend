package com.techvg.los.web.rest;

import com.techvg.los.repository.LoanDisbursementRepository;
import com.techvg.los.service.LoanDisbursementQueryService;
import com.techvg.los.service.LoanDisbursementService;
import com.techvg.los.service.criteria.LoanDisbursementCriteria;
import com.techvg.los.service.dto.LoanDisbursementDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.LoanDisbursement}.
 */
@RestController
@RequestMapping("/api")
public class LoanDisbursementResource {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementResource.class);

    private static final String ENTITY_NAME = "loanDisbursement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanDisbursementService loanDisbursementService;

    private final LoanDisbursementRepository loanDisbursementRepository;

    private final LoanDisbursementQueryService loanDisbursementQueryService;

    public LoanDisbursementResource(
        LoanDisbursementService loanDisbursementService,
        LoanDisbursementRepository loanDisbursementRepository,
        LoanDisbursementQueryService loanDisbursementQueryService
    ) {
        this.loanDisbursementService = loanDisbursementService;
        this.loanDisbursementRepository = loanDisbursementRepository;
        this.loanDisbursementQueryService = loanDisbursementQueryService;
    }

    /**
     * {@code POST  /loan-disbursements} : Create a new loanDisbursement.
     *
     * @param loanDisbursementDTO the loanDisbursementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDisbursementDTO, or with status {@code 400 (Bad Request)} if the loanDisbursement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-disbursements")
    public ResponseEntity<LoanDisbursementDTO> createLoanDisbursement(@RequestBody LoanDisbursementDTO loanDisbursementDTO)
        throws URISyntaxException {
        log.debug("REST request to save LoanDisbursement : {}", loanDisbursementDTO);
        if (loanDisbursementDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanDisbursement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDisbursementDTO result = loanDisbursementService.save(loanDisbursementDTO);
        return ResponseEntity
            .created(new URI("/api/loan-disbursements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-disbursements/:id} : Updates an existing loanDisbursement.
     *
     * @param id the id of the loanDisbursementDTO to save.
     * @param loanDisbursementDTO the loanDisbursementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDisbursementDTO,
     * or with status {@code 400 (Bad Request)} if the loanDisbursementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDisbursementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-disbursements/{id}")
    public ResponseEntity<LoanDisbursementDTO> updateLoanDisbursement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDisbursementDTO loanDisbursementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanDisbursement : {}, {}", id, loanDisbursementDTO);
        if (loanDisbursementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDisbursementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDisbursementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDisbursementDTO result = loanDisbursementService.update(loanDisbursementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDisbursementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-disbursements/:id} : Partial updates given fields of an existing loanDisbursement, field will ignore if it is null
     *
     * @param id the id of the loanDisbursementDTO to save.
     * @param loanDisbursementDTO the loanDisbursementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDisbursementDTO,
     * or with status {@code 400 (Bad Request)} if the loanDisbursementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanDisbursementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanDisbursementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-disbursements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanDisbursementDTO> partialUpdateLoanDisbursement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDisbursementDTO loanDisbursementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanDisbursement partially : {}, {}", id, loanDisbursementDTO);
        if (loanDisbursementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDisbursementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDisbursementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanDisbursementDTO> result = loanDisbursementService.partialUpdate(loanDisbursementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDisbursementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-disbursements} : get all the loanDisbursements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanDisbursements in body.
     */
    @GetMapping("/loan-disbursements")
    public ResponseEntity<List<LoanDisbursementDTO>> getAllLoanDisbursements(
        LoanDisbursementCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanDisbursements by criteria: {}", criteria);
        Page<LoanDisbursementDTO> page = loanDisbursementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-disbursements/count} : count all the loanDisbursements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-disbursements/count")
    public ResponseEntity<Long> countLoanDisbursements(LoanDisbursementCriteria criteria) {
        log.debug("REST request to count LoanDisbursements by criteria: {}", criteria);
        return ResponseEntity.ok().body(loanDisbursementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-disbursements/:id} : get the "id" loanDisbursement.
     *
     * @param id the id of the loanDisbursementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDisbursementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-disbursements/{id}")
    public ResponseEntity<LoanDisbursementDTO> getLoanDisbursement(@PathVariable Long id) {
        log.debug("REST request to get LoanDisbursement : {}", id);
        Optional<LoanDisbursementDTO> loanDisbursementDTO = loanDisbursementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDisbursementDTO);
    }

    /**
     * {@code DELETE  /loan-disbursements/:id} : delete the "id" loanDisbursement.
     *
     * @param id the id of the loanDisbursementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-disbursements/{id}")
    public ResponseEntity<Void> deleteLoanDisbursement(@PathVariable Long id) {
        log.debug("REST request to delete LoanDisbursement : {}", id);
        loanDisbursementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
