package com.techvg.los.web.rest;

import com.techvg.los.repository.LedgerAccountsRepository;
import com.techvg.los.service.LedgerAccountsQueryService;
import com.techvg.los.service.LedgerAccountsService;
import com.techvg.los.service.criteria.LedgerAccountsCriteria;
import com.techvg.los.service.dto.LedgerAccountsDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.LedgerAccounts}.
 */
@RestController
@RequestMapping("/api")
public class LedgerAccountsResource {

    private final Logger log = LoggerFactory.getLogger(LedgerAccountsResource.class);

    private static final String ENTITY_NAME = "ledgerAccounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LedgerAccountsService ledgerAccountsService;

    private final LedgerAccountsRepository ledgerAccountsRepository;

    private final LedgerAccountsQueryService ledgerAccountsQueryService;

    public LedgerAccountsResource(
        LedgerAccountsService ledgerAccountsService,
        LedgerAccountsRepository ledgerAccountsRepository,
        LedgerAccountsQueryService ledgerAccountsQueryService
    ) {
        this.ledgerAccountsService = ledgerAccountsService;
        this.ledgerAccountsRepository = ledgerAccountsRepository;
        this.ledgerAccountsQueryService = ledgerAccountsQueryService;
    }

    /**
     * {@code POST  /ledger-accounts} : Create a new ledgerAccounts.
     *
     * @param ledgerAccountsDTO the ledgerAccountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ledgerAccountsDTO, or with status {@code 400 (Bad Request)} if the ledgerAccounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ledger-accounts")
    public ResponseEntity<LedgerAccountsDTO> createLedgerAccounts(@RequestBody LedgerAccountsDTO ledgerAccountsDTO)
        throws URISyntaxException {
        log.debug("REST request to save LedgerAccounts : {}", ledgerAccountsDTO);
        if (ledgerAccountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ledgerAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LedgerAccountsDTO result = ledgerAccountsService.save(ledgerAccountsDTO);
        return ResponseEntity
            .created(new URI("/api/ledger-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ledger-accounts/:id} : Updates an existing ledgerAccounts.
     *
     * @param id the id of the ledgerAccountsDTO to save.
     * @param ledgerAccountsDTO the ledgerAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ledgerAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the ledgerAccountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ledgerAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ledger-accounts/{id}")
    public ResponseEntity<LedgerAccountsDTO> updateLedgerAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LedgerAccountsDTO ledgerAccountsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LedgerAccounts : {}, {}", id, ledgerAccountsDTO);
        if (ledgerAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ledgerAccountsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ledgerAccountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LedgerAccountsDTO result = ledgerAccountsService.update(ledgerAccountsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ledgerAccountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ledger-accounts/:id} : Partial updates given fields of an existing ledgerAccounts, field will ignore if it is null
     *
     * @param id the id of the ledgerAccountsDTO to save.
     * @param ledgerAccountsDTO the ledgerAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ledgerAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the ledgerAccountsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ledgerAccountsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ledgerAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ledger-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LedgerAccountsDTO> partialUpdateLedgerAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LedgerAccountsDTO ledgerAccountsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LedgerAccounts partially : {}, {}", id, ledgerAccountsDTO);
        if (ledgerAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ledgerAccountsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ledgerAccountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LedgerAccountsDTO> result = ledgerAccountsService.partialUpdate(ledgerAccountsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ledgerAccountsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ledger-accounts} : get all the ledgerAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ledgerAccounts in body.
     */
    @GetMapping("/ledger-accounts")
    public ResponseEntity<List<LedgerAccountsDTO>> getAllLedgerAccounts(
        LedgerAccountsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LedgerAccounts by criteria: {}", criteria);
        Page<LedgerAccountsDTO> page = ledgerAccountsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ledger-accounts/count} : count all the ledgerAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ledger-accounts/count")
    public ResponseEntity<Long> countLedgerAccounts(LedgerAccountsCriteria criteria) {
        log.debug("REST request to count LedgerAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(ledgerAccountsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ledger-accounts/:id} : get the "id" ledgerAccounts.
     *
     * @param id the id of the ledgerAccountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ledgerAccountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ledger-accounts/{id}")
    public ResponseEntity<LedgerAccountsDTO> getLedgerAccounts(@PathVariable Long id) {
        log.debug("REST request to get LedgerAccounts : {}", id);
        Optional<LedgerAccountsDTO> ledgerAccountsDTO = ledgerAccountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ledgerAccountsDTO);
    }

    /**
     * {@code DELETE  /ledger-accounts/:id} : delete the "id" ledgerAccounts.
     *
     * @param id the id of the ledgerAccountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ledger-accounts/{id}")
    public ResponseEntity<Void> deleteLedgerAccounts(@PathVariable Long id) {
        log.debug("REST request to delete LedgerAccounts : {}", id);
        ledgerAccountsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
