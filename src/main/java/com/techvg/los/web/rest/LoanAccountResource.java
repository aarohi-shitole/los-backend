package com.techvg.los.web.rest;

import com.techvg.los.repository.LoanAccountRepository;
import com.techvg.los.service.LoanAccountQueryService;
import com.techvg.los.service.LoanAccountService;
import com.techvg.los.service.criteria.LoanAccountCriteria;
import com.techvg.los.service.dto.Count;
import com.techvg.los.service.dto.LoanAccountDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.LoanAccount}.
 */
@RestController
@RequestMapping("/api")
public class LoanAccountResource {

    private final Logger log = LoggerFactory.getLogger(LoanAccountResource.class);

    private static final String ENTITY_NAME = "loanAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanAccountService loanAccountService;

    private final LoanAccountRepository loanAccountRepository;

    private final LoanAccountQueryService loanAccountQueryService;

    public LoanAccountResource(
        LoanAccountService loanAccountService,
        LoanAccountRepository loanAccountRepository,
        LoanAccountQueryService loanAccountQueryService
    ) {
        this.loanAccountService = loanAccountService;
        this.loanAccountRepository = loanAccountRepository;
        this.loanAccountQueryService = loanAccountQueryService;
    }

    /**
     * {@code POST  /loan-accounts} : Create a new loanAccount.
     *
     * @param loanAccountDTO the loanAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanAccountDTO, or with status {@code 400 (Bad Request)} if the loanAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-accounts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_EDIT','LOAN_ACC_EDIT')")
    public ResponseEntity<LoanAccountDTO> createLoanAccount(@RequestBody LoanAccountDTO loanAccountDTO) throws URISyntaxException {
        log.debug("REST request to save LoanAccount : {}", loanAccountDTO);
        if (loanAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanAccountDTO result = loanAccountService.save(loanAccountDTO);
        return ResponseEntity
            .created(new URI("/api/loan-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-accounts/:id} : Updates an existing loanAccount.
     *
     * @param id the id of the loanAccountDTO to save.
     * @param loanAccountDTO the loanAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanAccountDTO,
     * or with status {@code 400 (Bad Request)} if the loanAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_EDIT','LOAN_ACC_EDIT')")
    public ResponseEntity<LoanAccountDTO> updateLoanAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanAccountDTO loanAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanAccount : {}, {}", id, loanAccountDTO);
        if (loanAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanAccountDTO result = loanAccountService.update(loanAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-accounts/:id} : Partial updates given fields of an existing loanAccount, field will ignore if it is null
     *
     * @param id the id of the loanAccountDTO to save.
     * @param loanAccountDTO the loanAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanAccountDTO,
     * or with status {@code 400 (Bad Request)} if the loanAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanAccountDTO> partialUpdateLoanAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanAccountDTO loanAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanAccount partially : {}, {}", id, loanAccountDTO);
        if (loanAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanAccountDTO> result = loanAccountService.partialUpdate(loanAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-accounts} : get all the loanAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanAccounts in body.
     */
    @GetMapping("/loan-accounts")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_ACC_VIEW')")
    public ResponseEntity<List<LoanAccountDTO>> getAllLoanAccounts(
        LoanAccountCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanAccounts by criteria: {}", criteria);
        Page<LoanAccountDTO> page = loanAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-accounts/count} : count all the loanAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-accounts/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_ACC_VIEW')")
    public ResponseEntity<Count> countLoanAccounts(LoanAccountCriteria criteria) {
        log.debug("REST request to count LoanAccounts by criteria: {}", criteria);
        Count loanAccountsCount = new Count();
        loanAccountsCount.count = loanAccountQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(loanAccountsCount);
    }

    /**
     * {@code GET  /loan-accounts/:id} : get the "id" loanAccount.
     *
     * @param id the id of the loanAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_ACC_EDIT','LOAN_APPL_VIEW','LOAN_ACC_VIEW')")
    public ResponseEntity<LoanAccountDTO> getLoanAccount(@PathVariable Long id) {
        log.debug("REST request to get LoanAccount : {}", id);
        Optional<LoanAccountDTO> loanAccountDTO = loanAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanAccountDTO);
    }

    /**
     * {@code DELETE  /loan-accounts/:id} : delete the "id" loanAccount.
     *
     * @param id the id of the loanAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_ACC_EDIT')")
    public ResponseEntity<Void> deleteLoanAccount(@PathVariable Long id) {
        log.debug("REST request to delete LoanAccount : {}", id);
        loanAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
