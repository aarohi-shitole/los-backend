package com.techvg.los.web.rest;

import com.techvg.los.repository.AmortizationDetailsRepository;
import com.techvg.los.service.AmortizationDetailsQueryService;
import com.techvg.los.service.AmortizationDetailsService;
import com.techvg.los.service.criteria.AmortizationDetailsCriteria;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.AmortizationDetails}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationDetailsResource.class);

    private static final String ENTITY_NAME = "amortizationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmortizationDetailsService amortizationDetailsService;

    private final AmortizationDetailsRepository amortizationDetailsRepository;

    private final AmortizationDetailsQueryService amortizationDetailsQueryService;

    public AmortizationDetailsResource(
        AmortizationDetailsService amortizationDetailsService,
        AmortizationDetailsRepository amortizationDetailsRepository,
        AmortizationDetailsQueryService amortizationDetailsQueryService
    ) {
        this.amortizationDetailsService = amortizationDetailsService;
        this.amortizationDetailsRepository = amortizationDetailsRepository;
        this.amortizationDetailsQueryService = amortizationDetailsQueryService;
    }

    /**
     * {@code POST  /amortization-details} : Create a new amortizationDetails.
     *
     * @param amortizationDetailsDTO the amortizationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationDetailsDTO, or with status {@code 400 (Bad Request)} if the amortizationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-details")
    public ResponseEntity<AmortizationDetailsDTO> createAmortizationDetails(@RequestBody AmortizationDetailsDTO amortizationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AmortizationDetails : {}", amortizationDetailsDTO);
        if (amortizationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationDetailsDTO result = amortizationDetailsService.save(amortizationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/amortization-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amortization-details/:id} : Updates an existing amortizationDetails.
     *
     * @param id the id of the amortizationDetailsDTO to save.
     * @param amortizationDetailsDTO the amortizationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-details/{id}")
    public ResponseEntity<AmortizationDetailsDTO> updateAmortizationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmortizationDetailsDTO amortizationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AmortizationDetails : {}, {}", id, amortizationDetailsDTO);
        if (amortizationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amortizationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amortizationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AmortizationDetailsDTO result = amortizationDetailsService.update(amortizationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amortizationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /amortization-details/:id} : Partial updates given fields of an existing amortizationDetails, field will ignore if it is null
     *
     * @param id the id of the amortizationDetailsDTO to save.
     * @param amortizationDetailsDTO the amortizationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the amortizationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the amortizationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/amortization-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AmortizationDetailsDTO> partialUpdateAmortizationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AmortizationDetailsDTO amortizationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AmortizationDetails partially : {}, {}", id, amortizationDetailsDTO);
        if (amortizationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amortizationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amortizationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AmortizationDetailsDTO> result = amortizationDetailsService.partialUpdate(amortizationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amortizationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /amortization-details} : get all the amortizationDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationDetails in body.
     */
    @GetMapping("/amortization-details")
    public ResponseEntity<List<AmortizationDetailsDTO>> getAllAmortizationDetails(
        AmortizationDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AmortizationDetails by criteria: {}", criteria);
        Page<AmortizationDetailsDTO> page = amortizationDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /amortization-details/count} : count all the amortizationDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/amortization-details/count")
    public ResponseEntity<Long> countAmortizationDetails(AmortizationDetailsCriteria criteria) {
        log.debug("REST request to count AmortizationDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(amortizationDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /amortization-details/:id} : get the "id" amortizationDetails.
     *
     * @param id the id of the amortizationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-details/{id}")
    public ResponseEntity<AmortizationDetailsDTO> getAmortizationDetails(@PathVariable Long id) {
        log.debug("REST request to get AmortizationDetails : {}", id);
        Optional<AmortizationDetailsDTO> amortizationDetailsDTO = amortizationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationDetailsDTO);
    }

    /**
     * {@code DELETE  /amortization-details/:id} : delete the "id" amortizationDetails.
     *
     * @param id the id of the amortizationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-details/{id}")
    public ResponseEntity<Void> deleteAmortizationDetails(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationDetails : {}", id);
        amortizationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
