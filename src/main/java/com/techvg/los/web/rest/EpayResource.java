package com.techvg.los.web.rest;

import com.techvg.los.repository.EpayRepository;
import com.techvg.los.service.EpayQueryService;
import com.techvg.los.service.EpayService;
import com.techvg.los.service.criteria.EpayCriteria;
import com.techvg.los.service.dto.EpayDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.Epay}.
 */
@RestController
@RequestMapping("/api")
public class EpayResource {

    private final Logger log = LoggerFactory.getLogger(EpayResource.class);

    private static final String ENTITY_NAME = "epay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpayService epayService;

    private final EpayRepository epayRepository;

    private final EpayQueryService epayQueryService;

    public EpayResource(EpayService epayService, EpayRepository epayRepository, EpayQueryService epayQueryService) {
        this.epayService = epayService;
        this.epayRepository = epayRepository;
        this.epayQueryService = epayQueryService;
    }

    /**
     * {@code POST  /epays} : Create a new epay.
     *
     * @param epayDTO the epayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epayDTO, or with status {@code 400 (Bad Request)} if the epay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/epays")
    public ResponseEntity<EpayDTO> createEpay(@RequestBody EpayDTO epayDTO) throws URISyntaxException {
        log.debug("REST request to save Epay : {}", epayDTO);
        if (epayDTO.getId() != null) {
            throw new BadRequestAlertException("A new epay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpayDTO result = epayService.save(epayDTO);
        return ResponseEntity
            .created(new URI("/api/epays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /epays/:id} : Updates an existing epay.
     *
     * @param id the id of the epayDTO to save.
     * @param epayDTO the epayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epayDTO,
     * or with status {@code 400 (Bad Request)} if the epayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/epays/{id}")
    public ResponseEntity<EpayDTO> updateEpay(@PathVariable(value = "id", required = false) final Long id, @RequestBody EpayDTO epayDTO)
        throws URISyntaxException {
        log.debug("REST request to update Epay : {}, {}", id, epayDTO);
        if (epayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EpayDTO result = epayService.update(epayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /epays/:id} : Partial updates given fields of an existing epay, field will ignore if it is null
     *
     * @param id the id of the epayDTO to save.
     * @param epayDTO the epayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epayDTO,
     * or with status {@code 400 (Bad Request)} if the epayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the epayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the epayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/epays/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EpayDTO> partialUpdateEpay(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EpayDTO epayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Epay partially : {}, {}", id, epayDTO);
        if (epayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EpayDTO> result = epayService.partialUpdate(epayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /epays} : get all the epays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epays in body.
     */
    @GetMapping("/epays")
    public ResponseEntity<List<EpayDTO>> getAllEpays(
        EpayCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Epays by criteria: {}", criteria);
        Page<EpayDTO> page = epayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /epays/count} : count all the epays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/epays/count")
    public ResponseEntity<Long> countEpays(EpayCriteria criteria) {
        log.debug("REST request to count Epays by criteria: {}", criteria);
        return ResponseEntity.ok().body(epayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /epays/:id} : get the "id" epay.
     *
     * @param id the id of the epayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/epays/{id}")
    public ResponseEntity<EpayDTO> getEpay(@PathVariable Long id) {
        log.debug("REST request to get Epay : {}", id);
        Optional<EpayDTO> epayDTO = epayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epayDTO);
    }

    /**
     * {@code DELETE  /epays/:id} : delete the "id" epay.
     *
     * @param id the id of the epayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/epays/{id}")
    public ResponseEntity<Void> deleteEpay(@PathVariable Long id) {
        log.debug("REST request to delete Epay : {}", id);
        epayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
