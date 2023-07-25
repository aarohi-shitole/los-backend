package com.techvg.los.web.rest;

import com.techvg.los.repository.LoanCatagoryRepository;
import com.techvg.los.service.LoanCatagoryQueryService;
import com.techvg.los.service.LoanCatagoryService;
import com.techvg.los.service.criteria.LoanCatagoryCriteria;
import com.techvg.los.service.dto.LoanCatagoryDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.LoanCatagory}.
 */
@RestController
@RequestMapping("/api")
public class LoanCatagoryResource {

    private final Logger log = LoggerFactory.getLogger(LoanCatagoryResource.class);

    private static final String ENTITY_NAME = "loanCatagory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanCatagoryService loanCatagoryService;

    private final LoanCatagoryRepository loanCatagoryRepository;

    private final LoanCatagoryQueryService loanCatagoryQueryService;

    public LoanCatagoryResource(
        LoanCatagoryService loanCatagoryService,
        LoanCatagoryRepository loanCatagoryRepository,
        LoanCatagoryQueryService loanCatagoryQueryService
    ) {
        this.loanCatagoryService = loanCatagoryService;
        this.loanCatagoryRepository = loanCatagoryRepository;
        this.loanCatagoryQueryService = loanCatagoryQueryService;
    }

    /**
     * {@code POST  /loan-catagories} : Create a new loanCatagory.
     *
     * @param loanCatagoryDTO the loanCatagoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanCatagoryDTO, or with status {@code 400 (Bad Request)} if the loanCatagory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-catagories")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_CATAG_EDIT')")
    public ResponseEntity<LoanCatagoryDTO> createLoanCatagory(@RequestBody LoanCatagoryDTO loanCatagoryDTO) throws URISyntaxException {
        log.debug("REST request to save LoanCatagory : {}", loanCatagoryDTO);
        if (loanCatagoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanCatagory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanCatagoryDTO result = loanCatagoryService.save(loanCatagoryDTO);
        return ResponseEntity
            .created(new URI("/api/loan-catagories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-catagories/:id} : Updates an existing loanCatagory.
     *
     * @param id the id of the loanCatagoryDTO to save.
     * @param loanCatagoryDTO the loanCatagoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanCatagoryDTO,
     * or with status {@code 400 (Bad Request)} if the loanCatagoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanCatagoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-catagories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_CATAG_EDIT')")
    public ResponseEntity<LoanCatagoryDTO> updateLoanCatagory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanCatagoryDTO loanCatagoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanCatagory : {}, {}", id, loanCatagoryDTO);
        if (loanCatagoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanCatagoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanCatagoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanCatagoryDTO result = loanCatagoryService.update(loanCatagoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanCatagoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-catagories/:id} : Partial updates given fields of an existing loanCatagory, field will ignore if it is null
     *
     * @param id the id of the loanCatagoryDTO to save.
     * @param loanCatagoryDTO the loanCatagoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanCatagoryDTO,
     * or with status {@code 400 (Bad Request)} if the loanCatagoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanCatagoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanCatagoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-catagories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanCatagoryDTO> partialUpdateLoanCatagory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanCatagoryDTO loanCatagoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanCatagory partially : {}, {}", id, loanCatagoryDTO);
        if (loanCatagoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanCatagoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanCatagoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanCatagoryDTO> result = loanCatagoryService.partialUpdate(loanCatagoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanCatagoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-catagories} : get all the loanCatagories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanCatagories in body.
     */
    @GetMapping("/loan-catagories")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_CATAG_VIEW','LOAN_PRODUCT_EDIT','LOAN_PRODUCT_VIEW','LOAN_APPL_EDIT')")
    public ResponseEntity<List<LoanCatagoryDTO>> getAllLoanCatagories(
        LoanCatagoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanCatagories by criteria: {}", criteria);
        Page<LoanCatagoryDTO> page = loanCatagoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-catagories/count} : count all the loanCatagories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-catagories/count")
    public ResponseEntity<Long> countLoanCatagories(LoanCatagoryCriteria criteria) {
        log.debug("REST request to count LoanCatagories by criteria: {}", criteria);
        return ResponseEntity.ok().body(loanCatagoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-catagories/:id} : get the "id" loanCatagory.
     *
     * @param id the id of the loanCatagoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanCatagoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-catagories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_CATAG_EDIT','LOAN_PRODUCT_EDIT','LOAN_PRODUCT_VIEW','LOAN_APPL_EDIT')")
    public ResponseEntity<LoanCatagoryDTO> getLoanCatagory(@PathVariable Long id) {
        log.debug("REST request to get LoanCatagory : {}", id);
        Optional<LoanCatagoryDTO> loanCatagoryDTO = loanCatagoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanCatagoryDTO);
    }

    /**
     * {@code DELETE  /loan-catagories/:id} : delete the "id" loanCatagory.
     *
     * @param id the id of the loanCatagoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-catagories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_CATAG_EDIT')")
    public ResponseEntity<Void> deleteLoanCatagory(@PathVariable Long id) {
        log.debug("REST request to delete LoanCatagory : {}", id);
        loanCatagoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
