package com.techvg.los.web.rest;

import com.techvg.los.repository.OrgPrerequisiteDocRepository;
import com.techvg.los.service.OrgPrerequisiteDocQueryService;
import com.techvg.los.service.OrgPrerequisiteDocService;
import com.techvg.los.service.criteria.OrgPrerequisiteDocCriteria;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.OrgPrerequisiteDoc}.
 */
@RestController
@RequestMapping("/api")
public class OrgPrerequisiteDocResource {

    private final Logger log = LoggerFactory.getLogger(OrgPrerequisiteDocResource.class);

    private static final String ENTITY_NAME = "orgPrerequisiteDoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgPrerequisiteDocService orgPrerequisiteDocService;

    private final OrgPrerequisiteDocRepository orgPrerequisiteDocRepository;

    private final OrgPrerequisiteDocQueryService orgPrerequisiteDocQueryService;

    public OrgPrerequisiteDocResource(
        OrgPrerequisiteDocService orgPrerequisiteDocService,
        OrgPrerequisiteDocRepository orgPrerequisiteDocRepository,
        OrgPrerequisiteDocQueryService orgPrerequisiteDocQueryService
    ) {
        this.orgPrerequisiteDocService = orgPrerequisiteDocService;
        this.orgPrerequisiteDocRepository = orgPrerequisiteDocRepository;
        this.orgPrerequisiteDocQueryService = orgPrerequisiteDocQueryService;
    }

    /**
     * {@code POST  /org-prerequisite-docs} : Create a new orgPrerequisiteDoc.
     *
     * @param orgPrerequisiteDocDTO the orgPrerequisiteDocDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgPrerequisiteDocDTO, or with status {@code 400 (Bad Request)} if the orgPrerequisiteDoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/org-prerequisite-docs")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_EDIT')")
    public ResponseEntity<OrgPrerequisiteDocDTO> createOrgPrerequisiteDoc(@RequestBody OrgPrerequisiteDocDTO orgPrerequisiteDocDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrgPrerequisiteDoc : {}", orgPrerequisiteDocDTO);
        if (orgPrerequisiteDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgPrerequisiteDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgPrerequisiteDocDTO result = orgPrerequisiteDocService.save(orgPrerequisiteDocDTO);
        return ResponseEntity
            .created(new URI("/api/org-prerequisite-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /org-prerequisite-docs/:id} : Updates an existing orgPrerequisiteDoc.
     *
     * @param id the id of the orgPrerequisiteDocDTO to save.
     * @param orgPrerequisiteDocDTO the orgPrerequisiteDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgPrerequisiteDocDTO,
     * or with status {@code 400 (Bad Request)} if the orgPrerequisiteDocDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgPrerequisiteDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/org-prerequisite-docs/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_EDIT')")
    public ResponseEntity<OrgPrerequisiteDocDTO> updateOrgPrerequisiteDoc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgPrerequisiteDocDTO orgPrerequisiteDocDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrgPrerequisiteDoc : {}, {}", id, orgPrerequisiteDocDTO);
        if (orgPrerequisiteDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgPrerequisiteDocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgPrerequisiteDocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrgPrerequisiteDocDTO result = orgPrerequisiteDocService.update(orgPrerequisiteDocDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgPrerequisiteDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /org-prerequisite-docs/:id} : Partial updates given fields of an existing orgPrerequisiteDoc, field will ignore if it is null
     *
     * @param id the id of the orgPrerequisiteDocDTO to save.
     * @param orgPrerequisiteDocDTO the orgPrerequisiteDocDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgPrerequisiteDocDTO,
     * or with status {@code 400 (Bad Request)} if the orgPrerequisiteDocDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orgPrerequisiteDocDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgPrerequisiteDocDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/org-prerequisite-docs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrgPrerequisiteDocDTO> partialUpdateOrgPrerequisiteDoc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgPrerequisiteDocDTO orgPrerequisiteDocDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrgPrerequisiteDoc partially : {}, {}", id, orgPrerequisiteDocDTO);
        if (orgPrerequisiteDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgPrerequisiteDocDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgPrerequisiteDocRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgPrerequisiteDocDTO> result = orgPrerequisiteDocService.partialUpdate(orgPrerequisiteDocDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgPrerequisiteDocDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /org-prerequisite-docs} : get all the orgPrerequisiteDocs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgPrerequisiteDocs in body.
     */
    @GetMapping("/org-prerequisite-docs")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_VIEW')")
    public ResponseEntity<List<OrgPrerequisiteDocDTO>> getAllOrgPrerequisiteDocs(
        OrgPrerequisiteDocCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OrgPrerequisiteDocs by criteria: {}", criteria);
        Page<OrgPrerequisiteDocDTO> page = orgPrerequisiteDocQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /org-prerequisite-docs/count} : count all the orgPrerequisiteDocs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/org-prerequisite-docs/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_VIEW')")
    public ResponseEntity<Long> countOrgPrerequisiteDocs(OrgPrerequisiteDocCriteria criteria) {
        log.debug("REST request to count OrgPrerequisiteDocs by criteria: {}", criteria);
        return ResponseEntity.ok().body(orgPrerequisiteDocQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /org-prerequisite-docs/:id} : get the "id" orgPrerequisiteDoc.
     *
     * @param id the id of the orgPrerequisiteDocDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgPrerequisiteDocDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/org-prerequisite-docs/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_VIEW','SOC_PRERE_DOC_EDIT',)")
    public ResponseEntity<OrgPrerequisiteDocDTO> getOrgPrerequisiteDoc(@PathVariable Long id) {
        log.debug("REST request to get OrgPrerequisiteDoc : {}", id);
        Optional<OrgPrerequisiteDocDTO> orgPrerequisiteDocDTO = orgPrerequisiteDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgPrerequisiteDocDTO);
    }

    /**
     * {@code DELETE  /org-prerequisite-docs/:id} : delete the "id" orgPrerequisiteDoc.
     *
     * @param id the id of the orgPrerequisiteDocDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/org-prerequisite-docs/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SOC_PRERE_DOC_EDIT')")
    public ResponseEntity<Void> deleteOrgPrerequisiteDoc(@PathVariable Long id) {
        log.debug("REST request to delete OrgPrerequisiteDoc : {}", id);
        orgPrerequisiteDocService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
