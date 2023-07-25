package com.techvg.los.web.rest;

import com.techvg.los.repository.VouchersRepository;
import com.techvg.los.service.VouchersQueryService;
import com.techvg.los.service.VouchersService;
import com.techvg.los.service.criteria.VouchersCriteria;
import com.techvg.los.service.dto.VouchersDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.Vouchers}.
 */
@RestController
@RequestMapping("/api")
public class VouchersResource {

    private final Logger log = LoggerFactory.getLogger(VouchersResource.class);

    private static final String ENTITY_NAME = "vouchers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VouchersService vouchersService;

    private final VouchersRepository vouchersRepository;

    private final VouchersQueryService vouchersQueryService;

    public VouchersResource(
        VouchersService vouchersService,
        VouchersRepository vouchersRepository,
        VouchersQueryService vouchersQueryService
    ) {
        this.vouchersService = vouchersService;
        this.vouchersRepository = vouchersRepository;
        this.vouchersQueryService = vouchersQueryService;
    }

    /**
     * {@code POST  /vouchers} : Create a new vouchers.
     *
     * @param vouchersDTO the vouchersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vouchersDTO, or with status {@code 400 (Bad Request)} if the vouchers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vouchers")
    public ResponseEntity<VouchersDTO> createVouchers(@RequestBody VouchersDTO vouchersDTO) throws URISyntaxException {
        log.debug("REST request to save Vouchers : {}", vouchersDTO);
        if (vouchersDTO.getId() != null) {
            throw new BadRequestAlertException("A new vouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VouchersDTO result = vouchersService.save(vouchersDTO);
        return ResponseEntity
            .created(new URI("/api/vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vouchers/:id} : Updates an existing vouchers.
     *
     * @param id the id of the vouchersDTO to save.
     * @param vouchersDTO the vouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vouchersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vouchers/{id}")
    public ResponseEntity<VouchersDTO> updateVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VouchersDTO vouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Vouchers : {}, {}", id, vouchersDTO);
        if (vouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VouchersDTO result = vouchersService.update(vouchersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vouchersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vouchers/:id} : Partial updates given fields of an existing vouchers, field will ignore if it is null
     *
     * @param id the id of the vouchersDTO to save.
     * @param vouchersDTO the vouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vouchersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vouchersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vouchers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VouchersDTO> partialUpdateVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VouchersDTO vouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vouchers partially : {}, {}", id, vouchersDTO);
        if (vouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VouchersDTO> result = vouchersService.partialUpdate(vouchersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vouchersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vouchers} : get all the vouchers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vouchers in body.
     */
    @GetMapping("/vouchers")
    public ResponseEntity<List<VouchersDTO>> getAllVouchers(
        VouchersCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Vouchers by criteria: {}", criteria);
        Page<VouchersDTO> page = vouchersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vouchers/count} : count all the vouchers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vouchers/count")
    public ResponseEntity<Long> countVouchers(VouchersCriteria criteria) {
        log.debug("REST request to count Vouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(vouchersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vouchers/:id} : get the "id" vouchers.
     *
     * @param id the id of the vouchersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vouchersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vouchers/{id}")
    public ResponseEntity<VouchersDTO> getVouchers(@PathVariable Long id) {
        log.debug("REST request to get Vouchers : {}", id);
        Optional<VouchersDTO> vouchersDTO = vouchersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vouchersDTO);
    }

    /**
     * {@code DELETE  /vouchers/:id} : delete the "id" vouchers.
     *
     * @param id the id of the vouchersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Void> deleteVouchers(@PathVariable Long id) {
        log.debug("REST request to delete Vouchers : {}", id);
        vouchersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
