package com.techvg.los.web.rest;

import com.techvg.los.repository.EmployementDetailsRepository;
import com.techvg.los.service.EmployementDetailsQueryService;
import com.techvg.los.service.EmployementDetailsService;
import com.techvg.los.service.criteria.EmployementDetailsCriteria;
import com.techvg.los.service.dto.EmployementDetailsDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing
 * {@link com.techvg.los.domain.EmployementDetails}.
 */
@RestController
@RequestMapping("/api")
public class EmployementDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EmployementDetailsResource.class);

    private static final String ENTITY_NAME = "employementDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployementDetailsService employementDetailsService;

    private final EmployementDetailsRepository employementDetailsRepository;

    private final EmployementDetailsQueryService employementDetailsQueryService;

    public EmployementDetailsResource(
        EmployementDetailsService employementDetailsService,
        EmployementDetailsRepository employementDetailsRepository,
        EmployementDetailsQueryService employementDetailsQueryService
    ) {
        this.employementDetailsService = employementDetailsService;
        this.employementDetailsRepository = employementDetailsRepository;
        this.employementDetailsQueryService = employementDetailsQueryService;
    }

    /**
     * {@code POST  /employement-details} : Create a new employementDetails.
     *
     * @param employementDetailsDTO the employementDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new employementDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the employementDetails has already an
     *         ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employement-details")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<EmployementDetailsDTO> createEmployementDetails(@Valid @RequestBody EmployementDetailsDTO employementDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployementDetails : {}", employementDetailsDTO);
        if (employementDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new employementDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployementDetailsDTO result = employementDetailsService.save(employementDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/employement-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employement-details/:id} : Updates an existing
     * employementDetails.
     *
     * @param id                    the id of the employementDetailsDTO to save.
     * @param employementDetailsDTO the employementDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated employementDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the employementDetailsDTO is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         employementDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employement-details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<EmployementDetailsDTO> updateEmployementDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployementDetailsDTO employementDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployementDetails : {}, {}", id, employementDetailsDTO);
        if (employementDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employementDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employementDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployementDetailsDTO result = employementDetailsService.update(employementDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employementDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employement-details/:id} : Partial updates given fields of an
     * existing employementDetails, field will ignore if it is null
     *
     * @param id                    the id of the employementDetailsDTO to save.
     * @param employementDetailsDTO the employementDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated employementDetailsDTO, or with status
     *         {@code 400 (Bad Request)} if the employementDetailsDTO is not valid,
     *         or with status {@code 404 (Not Found)} if the employementDetailsDTO
     *         is not found, or with status {@code 500 (Internal Server Error)} if
     *         the employementDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employement-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployementDetailsDTO> partialUpdateEmployementDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployementDetailsDTO employementDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployementDetails partially : {}, {}", id, employementDetailsDTO);
        if (employementDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employementDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employementDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployementDetailsDTO> result = employementDetailsService.partialUpdate(employementDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employementDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employement-details} : get all the employementDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of employementDetails in body.
     */
    @GetMapping("/employement-details")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<List<EmployementDetailsDTO>> getAllEmployementDetails(
        EmployementDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployementDetails by criteria: {}", criteria);
        Page<EmployementDetailsDTO> page = employementDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employement-details/count} : count all the employementDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/employement-details/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<Long> countEmployementDetails(EmployementDetailsCriteria criteria) {
        log.debug("REST request to count EmployementDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(employementDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employement-details/:id} : get the "id" employementDetails.
     *
     * @param id the id of the employementDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the employementDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employement-details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<EmployementDetailsDTO> getEmployementDetails(@PathVariable Long id) {
        log.debug("REST request to get EmployementDetails : {}", id);
        Optional<EmployementDetailsDTO> employementDetailsDTO = employementDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employementDetailsDTO);
    }

    /**
     * {@code DELETE  /employement-details/:id} : delete the "id"
     * employementDetails.
     *
     * @param id the id of the employementDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employement-details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<Void> deleteEmployementDetails(@PathVariable Long id) {
        log.debug("REST request to delete EmployementDetails : {}", id);
        employementDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
