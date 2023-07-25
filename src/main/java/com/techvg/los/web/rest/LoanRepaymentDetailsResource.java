package com.techvg.los.web.rest;

import com.techvg.los.repository.LoanRepaymentDetailsRepository;
import com.techvg.los.service.LoanRepaymentDetailsQueryService;
import com.techvg.los.service.LoanRepaymentDetailsService;
import com.techvg.los.service.criteria.LoanRepaymentDetailsCriteria;
import com.techvg.los.service.dto.LoanRepaymentDetailsDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.LoanRepaymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class LoanRepaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LoanRepaymentDetailsResource.class);

    private static final String ENTITY_NAME = "loanRepaymentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanRepaymentDetailsService loanRepaymentDetailsService;

    private final LoanRepaymentDetailsRepository loanRepaymentDetailsRepository;

    private final LoanRepaymentDetailsQueryService loanRepaymentDetailsQueryService;

    public LoanRepaymentDetailsResource(
        LoanRepaymentDetailsService loanRepaymentDetailsService,
        LoanRepaymentDetailsRepository loanRepaymentDetailsRepository,
        LoanRepaymentDetailsQueryService loanRepaymentDetailsQueryService
    ) {
        this.loanRepaymentDetailsService = loanRepaymentDetailsService;
        this.loanRepaymentDetailsRepository = loanRepaymentDetailsRepository;
        this.loanRepaymentDetailsQueryService = loanRepaymentDetailsQueryService;
    }

    /**
     * {@code POST  /loan-repayment-details} : Create a new loanRepaymentDetails.
     *
     * @param loanRepaymentDetailsDTO the loanRepaymentDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanRepaymentDetailsDTO, or with status {@code 400 (Bad Request)} if the loanRepaymentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-repayment-details")
    public ResponseEntity<LoanRepaymentDetailsDTO> createLoanRepaymentDetails(@RequestBody LoanRepaymentDetailsDTO loanRepaymentDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save LoanRepaymentDetails : {}", loanRepaymentDetailsDTO);
        if (loanRepaymentDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanRepaymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanRepaymentDetailsDTO result = loanRepaymentDetailsService.save(loanRepaymentDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/loan-repayment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-repayment-details/:id} : Updates an existing loanRepaymentDetails.
     *
     * @param id the id of the loanRepaymentDetailsDTO to save.
     * @param loanRepaymentDetailsDTO the loanRepaymentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanRepaymentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanRepaymentDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanRepaymentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-repayment-details/{id}")
    public ResponseEntity<LoanRepaymentDetailsDTO> updateLoanRepaymentDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanRepaymentDetailsDTO loanRepaymentDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanRepaymentDetails : {}, {}", id, loanRepaymentDetailsDTO);
        if (loanRepaymentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanRepaymentDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanRepaymentDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanRepaymentDetailsDTO result = loanRepaymentDetailsService.update(loanRepaymentDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanRepaymentDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-repayment-details/:id} : Partial updates given fields of an existing loanRepaymentDetails, field will ignore if it is null
     *
     * @param id the id of the loanRepaymentDetailsDTO to save.
     * @param loanRepaymentDetailsDTO the loanRepaymentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanRepaymentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanRepaymentDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanRepaymentDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanRepaymentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-repayment-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanRepaymentDetailsDTO> partialUpdateLoanRepaymentDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanRepaymentDetailsDTO loanRepaymentDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanRepaymentDetails partially : {}, {}", id, loanRepaymentDetailsDTO);
        if (loanRepaymentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanRepaymentDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanRepaymentDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanRepaymentDetailsDTO> result = loanRepaymentDetailsService.partialUpdate(loanRepaymentDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanRepaymentDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-repayment-details} : get all the loanRepaymentDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanRepaymentDetails in body.
     */
    @GetMapping("/loan-repayment-details")
    public ResponseEntity<List<LoanRepaymentDetailsDTO>> getAllLoanRepaymentDetails(
        LoanRepaymentDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanRepaymentDetails by criteria: {}", criteria);
        Page<LoanRepaymentDetailsDTO> page = loanRepaymentDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-repayment-details/count} : count all the loanRepaymentDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-repayment-details/count")
    public ResponseEntity<Long> countLoanRepaymentDetails(LoanRepaymentDetailsCriteria criteria) {
        log.debug("REST request to count LoanRepaymentDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(loanRepaymentDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-repayment-details/:id} : get the "id" loanRepaymentDetails.
     *
     * @param id the id of the loanRepaymentDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanRepaymentDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-repayment-details/{id}")
    public ResponseEntity<LoanRepaymentDetailsDTO> getLoanRepaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get LoanRepaymentDetails : {}", id);
        Optional<LoanRepaymentDetailsDTO> loanRepaymentDetailsDTO = loanRepaymentDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanRepaymentDetailsDTO);
    }

    /**
     * {@code DELETE  /loan-repayment-details/:id} : delete the "id" loanRepaymentDetails.
     *
     * @param id the id of the loanRepaymentDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-repayment-details/{id}")
    public ResponseEntity<Void> deleteLoanRepaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete LoanRepaymentDetails : {}", id);
        loanRepaymentDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
