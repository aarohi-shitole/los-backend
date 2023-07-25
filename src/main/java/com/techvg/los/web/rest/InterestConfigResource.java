package com.techvg.los.web.rest;

import com.techvg.los.repository.InterestConfigRepository;
import com.techvg.los.service.InterestConfigQueryService;
import com.techvg.los.service.InterestConfigService;
import com.techvg.los.service.criteria.InterestConfigCriteria;
import com.techvg.los.service.dto.InterestConfigDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.InterestConfig}.
 */
@RestController
@RequestMapping("/api")
public class InterestConfigResource {

    private final Logger log = LoggerFactory.getLogger(InterestConfigResource.class);

    private static final String ENTITY_NAME = "interestConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterestConfigService interestConfigService;

    private final InterestConfigRepository interestConfigRepository;

    private final InterestConfigQueryService interestConfigQueryService;

    public InterestConfigResource(
        InterestConfigService interestConfigService,
        InterestConfigRepository interestConfigRepository,
        InterestConfigQueryService interestConfigQueryService
    ) {
        this.interestConfigService = interestConfigService;
        this.interestConfigRepository = interestConfigRepository;
        this.interestConfigQueryService = interestConfigQueryService;
    }

    /**
     * {@code POST  /interest-configs} : Create a new interestConfig.
     *
     * @param interestConfigDTO the interestConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interestConfigDTO, or with status {@code 400 (Bad Request)} if the interestConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interest-configs")
    public ResponseEntity<InterestConfigDTO> createInterestConfig(@RequestBody InterestConfigDTO interestConfigDTO)
        throws URISyntaxException {
        log.debug("REST request to save InterestConfig : {}", interestConfigDTO);
        if (interestConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new interestConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterestConfigDTO result = interestConfigService.save(interestConfigDTO);
        return ResponseEntity
            .created(new URI("/api/interest-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interest-configs/:id} : Updates an existing interestConfig.
     *
     * @param id the id of the interestConfigDTO to save.
     * @param interestConfigDTO the interestConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interestConfigDTO,
     * or with status {@code 400 (Bad Request)} if the interestConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interestConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interest-configs/{id}")
    public ResponseEntity<InterestConfigDTO> updateInterestConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterestConfigDTO interestConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InterestConfig : {}, {}", id, interestConfigDTO);
        if (interestConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interestConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interestConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InterestConfigDTO result = interestConfigService.update(interestConfigDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interestConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interest-configs/:id} : Partial updates given fields of an existing interestConfig, field will ignore if it is null
     *
     * @param id the id of the interestConfigDTO to save.
     * @param interestConfigDTO the interestConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interestConfigDTO,
     * or with status {@code 400 (Bad Request)} if the interestConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the interestConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the interestConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/interest-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InterestConfigDTO> partialUpdateInterestConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterestConfigDTO interestConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterestConfig partially : {}, {}", id, interestConfigDTO);
        if (interestConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interestConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interestConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InterestConfigDTO> result = interestConfigService.partialUpdate(interestConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interestConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /interest-configs} : get all the interestConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interestConfigs in body.
     */
    @GetMapping("/interest-configs")
    public ResponseEntity<List<InterestConfigDTO>> getAllInterestConfigs(
        InterestConfigCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InterestConfigs by criteria: {}", criteria);
        Page<InterestConfigDTO> page = interestConfigQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interest-configs/count} : count all the interestConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/interest-configs/count")
    public ResponseEntity<Long> countInterestConfigs(InterestConfigCriteria criteria) {
        log.debug("REST request to count InterestConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(interestConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interest-configs/:id} : get the "id" interestConfig.
     *
     * @param id the id of the interestConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interestConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interest-configs/{id}")
    public ResponseEntity<InterestConfigDTO> getInterestConfig(@PathVariable Long id) {
        log.debug("REST request to get InterestConfig : {}", id);
        Optional<InterestConfigDTO> interestConfigDTO = interestConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interestConfigDTO);
    }

    /**
     * {@code DELETE  /interest-configs/:id} : delete the "id" interestConfig.
     *
     * @param id the id of the interestConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interest-configs/{id}")
    public ResponseEntity<Void> deleteInterestConfig(@PathVariable Long id) {
        log.debug("REST request to delete InterestConfig : {}", id);
        interestConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
