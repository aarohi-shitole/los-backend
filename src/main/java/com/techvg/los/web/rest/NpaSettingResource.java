package com.techvg.los.web.rest;

import com.techvg.los.repository.NpaSettingRepository;
import com.techvg.los.service.NpaSettingQueryService;
import com.techvg.los.service.NpaSettingService;
import com.techvg.los.service.criteria.NpaSettingCriteria;
import com.techvg.los.service.dto.NpaSettingDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.NpaSetting}.
 */
@RestController
@RequestMapping("/api")
public class NpaSettingResource {

    private final Logger log = LoggerFactory.getLogger(NpaSettingResource.class);

    private static final String ENTITY_NAME = "npaSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NpaSettingService npaSettingService;

    private final NpaSettingRepository npaSettingRepository;

    private final NpaSettingQueryService npaSettingQueryService;

    public NpaSettingResource(
        NpaSettingService npaSettingService,
        NpaSettingRepository npaSettingRepository,
        NpaSettingQueryService npaSettingQueryService
    ) {
        this.npaSettingService = npaSettingService;
        this.npaSettingRepository = npaSettingRepository;
        this.npaSettingQueryService = npaSettingQueryService;
    }

    /**
     * {@code POST  /npa-settings} : Create a new npaSetting.
     *
     * @param npaSettingDTO the npaSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new npaSettingDTO, or with status {@code 400 (Bad Request)} if the npaSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/npa-settings")
    public ResponseEntity<NpaSettingDTO> createNpaSetting(@RequestBody NpaSettingDTO npaSettingDTO) throws URISyntaxException {
        log.debug("REST request to save NpaSetting : {}", npaSettingDTO);
        if (npaSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new npaSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NpaSettingDTO result = npaSettingService.save(npaSettingDTO);
        return ResponseEntity
            .created(new URI("/api/npa-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /npa-settings/:id} : Updates an existing npaSetting.
     *
     * @param id the id of the npaSettingDTO to save.
     * @param npaSettingDTO the npaSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated npaSettingDTO,
     * or with status {@code 400 (Bad Request)} if the npaSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the npaSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/npa-settings/{id}")
    public ResponseEntity<NpaSettingDTO> updateNpaSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NpaSettingDTO npaSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NpaSetting : {}, {}", id, npaSettingDTO);
        if (npaSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, npaSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!npaSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NpaSettingDTO result = npaSettingService.update(npaSettingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, npaSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /npa-settings/:id} : Partial updates given fields of an existing npaSetting, field will ignore if it is null
     *
     * @param id the id of the npaSettingDTO to save.
     * @param npaSettingDTO the npaSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated npaSettingDTO,
     * or with status {@code 400 (Bad Request)} if the npaSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the npaSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the npaSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/npa-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NpaSettingDTO> partialUpdateNpaSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NpaSettingDTO npaSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NpaSetting partially : {}, {}", id, npaSettingDTO);
        if (npaSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, npaSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!npaSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NpaSettingDTO> result = npaSettingService.partialUpdate(npaSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, npaSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /npa-settings} : get all the npaSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of npaSettings in body.
     */
    @GetMapping("/npa-settings")
    public ResponseEntity<List<NpaSettingDTO>> getAllNpaSettings(
        NpaSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NpaSettings by criteria: {}", criteria);
        Page<NpaSettingDTO> page = npaSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /npa-settings/count} : count all the npaSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/npa-settings/count")
    public ResponseEntity<Long> countNpaSettings(NpaSettingCriteria criteria) {
        log.debug("REST request to count NpaSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(npaSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /npa-settings/:id} : get the "id" npaSetting.
     *
     * @param id the id of the npaSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the npaSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/npa-settings/{id}")
    public ResponseEntity<NpaSettingDTO> getNpaSetting(@PathVariable Long id) {
        log.debug("REST request to get NpaSetting : {}", id);
        Optional<NpaSettingDTO> npaSettingDTO = npaSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(npaSettingDTO);
    }

    /**
     * {@code DELETE  /npa-settings/:id} : delete the "id" npaSetting.
     *
     * @param id the id of the npaSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/npa-settings/{id}")
    public ResponseEntity<Void> deleteNpaSetting(@PathVariable Long id) {
        log.debug("REST request to delete NpaSetting : {}", id);
        npaSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
