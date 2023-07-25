package com.techvg.los.web.rest;

import com.techvg.los.repository.EnquiryDetailsRepository;
import com.techvg.los.service.EnquiryDetailsQueryService;
import com.techvg.los.service.EnquiryDetailsService;
import com.techvg.los.service.criteria.EnquiryDetailsCriteria;
import com.techvg.los.service.dto.EnquiryDetailsDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.EnquiryDetails}.
 */
@RestController
@RequestMapping("/api")
public class EnquiryDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsResource.class);

    private static final String ENTITY_NAME = "enquiryDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquiryDetailsService enquiryDetailsService;

    private final EnquiryDetailsRepository enquiryDetailsRepository;

    private final EnquiryDetailsQueryService enquiryDetailsQueryService;

    public EnquiryDetailsResource(
        EnquiryDetailsService enquiryDetailsService,
        EnquiryDetailsRepository enquiryDetailsRepository,
        EnquiryDetailsQueryService enquiryDetailsQueryService
    ) {
        this.enquiryDetailsService = enquiryDetailsService;
        this.enquiryDetailsRepository = enquiryDetailsRepository;
        this.enquiryDetailsQueryService = enquiryDetailsQueryService;
    }

    /**
     * {@code POST  /enquiry-details} : Create a new enquiryDetails.
     *
     * @param enquiryDetailsDTO the enquiryDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquiryDetailsDTO, or with status {@code 400 (Bad Request)} if the enquiryDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enquiry-details")
    public ResponseEntity<EnquiryDetailsDTO> createEnquiryDetails(@RequestBody EnquiryDetailsDTO enquiryDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save EnquiryDetails : {}", enquiryDetailsDTO);
        if (enquiryDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquiryDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnquiryDetailsDTO result = enquiryDetailsService.save(enquiryDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/enquiry-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enquiry-details/:id} : Updates an existing enquiryDetails.
     *
     * @param id the id of the enquiryDetailsDTO to save.
     * @param enquiryDetailsDTO the enquiryDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquiryDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enquiry-details/{id}")
    public ResponseEntity<EnquiryDetailsDTO> updateEnquiryDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnquiryDetailsDTO enquiryDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EnquiryDetails : {}, {}", id, enquiryDetailsDTO);
        if (enquiryDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquiryDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquiryDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnquiryDetailsDTO result = enquiryDetailsService.update(enquiryDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /enquiry-details/:id} : Partial updates given fields of an existing enquiryDetails, field will ignore if it is null
     *
     * @param id the id of the enquiryDetailsDTO to save.
     * @param enquiryDetailsDTO the enquiryDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enquiryDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enquiryDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/enquiry-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnquiryDetailsDTO> partialUpdateEnquiryDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnquiryDetailsDTO enquiryDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnquiryDetails partially : {}, {}", id, enquiryDetailsDTO);
        if (enquiryDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enquiryDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enquiryDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnquiryDetailsDTO> result = enquiryDetailsService.partialUpdate(enquiryDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /enquiry-details} : get all the enquiryDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquiryDetails in body.
     */
    @GetMapping("/enquiry-details")
    public ResponseEntity<List<EnquiryDetailsDTO>> getAllEnquiryDetails(
        EnquiryDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EnquiryDetails by criteria: {}", criteria);
        Page<EnquiryDetailsDTO> page = enquiryDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquiry-details/count} : count all the enquiryDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enquiry-details/count")
    public ResponseEntity<Long> countEnquiryDetails(EnquiryDetailsCriteria criteria) {
        log.debug("REST request to count EnquiryDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquiryDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquiry-details/:id} : get the "id" enquiryDetails.
     *
     * @param id the id of the enquiryDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquiryDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enquiry-details/{id}")
    public ResponseEntity<EnquiryDetailsDTO> getEnquiryDetails(@PathVariable Long id) {
        log.debug("REST request to get EnquiryDetails : {}", id);
        Optional<EnquiryDetailsDTO> enquiryDetailsDTO = enquiryDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquiryDetailsDTO);
    }

    /**
     * {@code DELETE  /enquiry-details/:id} : delete the "id" enquiryDetails.
     *
     * @param id the id of the enquiryDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enquiry-details/{id}")
    public ResponseEntity<Void> deleteEnquiryDetails(@PathVariable Long id) {
        log.debug("REST request to delete EnquiryDetails : {}", id);
        enquiryDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
