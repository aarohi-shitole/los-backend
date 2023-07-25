package com.techvg.los.web.rest;

import com.techvg.los.repository.AccountHeadCodeRepository;
import com.techvg.los.service.AccountHeadCodeQueryService;
import com.techvg.los.service.AccountHeadCodeService;
import com.techvg.los.service.criteria.AccountHeadCodeCriteria;
import com.techvg.los.service.dto.AccountHeadCodeDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.AccountHeadCode}.
 */
@RestController
@RequestMapping("/api")
public class AccountHeadCodeResource {

    private final Logger log = LoggerFactory.getLogger(AccountHeadCodeResource.class);

    private static final String ENTITY_NAME = "accountHeadCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountHeadCodeService accountHeadCodeService;

    private final AccountHeadCodeRepository accountHeadCodeRepository;

    private final AccountHeadCodeQueryService accountHeadCodeQueryService;

    public AccountHeadCodeResource(
        AccountHeadCodeService accountHeadCodeService,
        AccountHeadCodeRepository accountHeadCodeRepository,
        AccountHeadCodeQueryService accountHeadCodeQueryService
    ) {
        this.accountHeadCodeService = accountHeadCodeService;
        this.accountHeadCodeRepository = accountHeadCodeRepository;
        this.accountHeadCodeQueryService = accountHeadCodeQueryService;
    }

    /**
     * {@code POST  /account-head-codes} : Create a new accountHeadCode.
     *
     * @param accountHeadCodeDTO the accountHeadCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountHeadCodeDTO, or with status {@code 400 (Bad Request)} if the accountHeadCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-head-codes")
    public ResponseEntity<AccountHeadCodeDTO> createAccountHeadCode(@RequestBody AccountHeadCodeDTO accountHeadCodeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccountHeadCode : {}", accountHeadCodeDTO);
        if (accountHeadCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountHeadCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountHeadCodeDTO result = accountHeadCodeService.save(accountHeadCodeDTO);
        return ResponseEntity
            .created(new URI("/api/account-head-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-head-codes/:id} : Updates an existing accountHeadCode.
     *
     * @param id the id of the accountHeadCodeDTO to save.
     * @param accountHeadCodeDTO the accountHeadCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountHeadCodeDTO,
     * or with status {@code 400 (Bad Request)} if the accountHeadCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountHeadCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-head-codes/{id}")
    public ResponseEntity<AccountHeadCodeDTO> updateAccountHeadCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountHeadCodeDTO accountHeadCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccountHeadCode : {}, {}", id, accountHeadCodeDTO);
        if (accountHeadCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountHeadCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountHeadCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountHeadCodeDTO result = accountHeadCodeService.update(accountHeadCodeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountHeadCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-head-codes/:id} : Partial updates given fields of an existing accountHeadCode, field will ignore if it is null
     *
     * @param id the id of the accountHeadCodeDTO to save.
     * @param accountHeadCodeDTO the accountHeadCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountHeadCodeDTO,
     * or with status {@code 400 (Bad Request)} if the accountHeadCodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accountHeadCodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountHeadCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-head-codes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountHeadCodeDTO> partialUpdateAccountHeadCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountHeadCodeDTO accountHeadCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountHeadCode partially : {}, {}", id, accountHeadCodeDTO);
        if (accountHeadCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountHeadCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountHeadCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountHeadCodeDTO> result = accountHeadCodeService.partialUpdate(accountHeadCodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountHeadCodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /account-head-codes} : get all the accountHeadCodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountHeadCodes in body.
     */
    @GetMapping("/account-head-codes")
    public ResponseEntity<List<AccountHeadCodeDTO>> getAllAccountHeadCodes(
        AccountHeadCodeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AccountHeadCodes by criteria: {}", criteria);
        Page<AccountHeadCodeDTO> page = accountHeadCodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-head-codes/count} : count all the accountHeadCodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/account-head-codes/count")
    public ResponseEntity<Long> countAccountHeadCodes(AccountHeadCodeCriteria criteria) {
        log.debug("REST request to count AccountHeadCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(accountHeadCodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /account-head-codes/:id} : get the "id" accountHeadCode.
     *
     * @param id the id of the accountHeadCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountHeadCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-head-codes/{id}")
    public ResponseEntity<AccountHeadCodeDTO> getAccountHeadCode(@PathVariable Long id) {
        log.debug("REST request to get AccountHeadCode : {}", id);
        Optional<AccountHeadCodeDTO> accountHeadCodeDTO = accountHeadCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountHeadCodeDTO);
    }

    /**
     * {@code DELETE  /account-head-codes/:id} : delete the "id" accountHeadCode.
     *
     * @param id the id of the accountHeadCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-head-codes/{id}")
    public ResponseEntity<Void> deleteAccountHeadCode(@PathVariable Long id) {
        log.debug("REST request to delete AccountHeadCode : {}", id);
        accountHeadCodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
