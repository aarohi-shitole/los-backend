package com.techvg.los.web.rest;

import com.techvg.los.repository.RemarkHistoryRepository;
import com.techvg.los.service.RemarkHistoryQueryService;
import com.techvg.los.service.RemarkHistoryService;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.RemarkHistory}.
 */
@RestController
@RequestMapping("/api")
public class RemarkHistoryResource {

    private final Logger log = LoggerFactory.getLogger(RemarkHistoryResource.class);

    private static final String ENTITY_NAME = "remarkHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemarkHistoryService remarkHistoryService;

    private final RemarkHistoryRepository remarkHistoryRepository;

    private final RemarkHistoryQueryService remarkHistoryQueryService;

    public RemarkHistoryResource(
        RemarkHistoryService remarkHistoryService,
        RemarkHistoryRepository remarkHistoryRepository,
        RemarkHistoryQueryService remarkHistoryQueryService
    ) {
        this.remarkHistoryService = remarkHistoryService;
        this.remarkHistoryRepository = remarkHistoryRepository;
        this.remarkHistoryQueryService = remarkHistoryQueryService;
    }

    /**
     * {@code POST  /remark-histories} : Create a new remarkHistory.
     *
     * @param remarkHistoryDTO the remarkHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new remarkHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the remarkHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remark-histories")
    public ResponseEntity<RemarkHistoryDTO> createRemarkHistory(@RequestBody RemarkHistoryDTO remarkHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save RemarkHistory : {}", remarkHistoryDTO);
        if (remarkHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new remarkHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemarkHistoryDTO result = remarkHistoryService.save(remarkHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/remark-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remark-histories/:id} : Updates an existing remarkHistory.
     *
     * @param id               the id of the remarkHistoryDTO to save.
     * @param remarkHistoryDTO the remarkHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated remarkHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the remarkHistoryDTO is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         remarkHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remark-histories/{id}")
    public ResponseEntity<RemarkHistoryDTO> updateRemarkHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemarkHistoryDTO remarkHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RemarkHistory : {}, {}", id, remarkHistoryDTO);
        if (remarkHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remarkHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remarkHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RemarkHistoryDTO result = remarkHistoryService.update(remarkHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remarkHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /remark-histories/:id} : Partial updates given fields of an
     * existing remarkHistory, field will ignore if it is null
     *
     * @param id               the id of the remarkHistoryDTO to save.
     * @param remarkHistoryDTO the remarkHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated remarkHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the remarkHistoryDTO is not valid, or
     *         with status {@code 404 (Not Found)} if the remarkHistoryDTO is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         remarkHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/remark-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemarkHistoryDTO> partialUpdateRemarkHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemarkHistoryDTO remarkHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RemarkHistory partially : {}, {}", id, remarkHistoryDTO);
        if (remarkHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remarkHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remarkHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemarkHistoryDTO> result = remarkHistoryService.partialUpdate(remarkHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remarkHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remark-histories} : get all the remarkHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of remarkHistories in body.
     */
    @GetMapping("/remark-histories")
    public ResponseEntity<List<RemarkHistoryDTO>> getAllRemarkHistories(
        RemarkHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RemarkHistories by criteria: {}", criteria);
        BooleanFilter isdeleted = new BooleanFilter();
        isdeleted.setNotEquals(true);
        criteria.setIsDeleted(isdeleted);
        Page<RemarkHistoryDTO> page = remarkHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remark-histories/count} : count all the remarkHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/remark-histories/count")
    public ResponseEntity<Long> countRemarkHistories(RemarkHistoryCriteria criteria) {
        log.debug("REST request to count RemarkHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(remarkHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /remark-histories/:id} : get the "id" remarkHistory.
     *
     * @param id the id of the remarkHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the remarkHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remark-histories/{id}")
    public ResponseEntity<RemarkHistoryDTO> getRemarkHistory(@PathVariable Long id) {
        log.debug("REST request to get RemarkHistory : {}", id);
        Optional<RemarkHistoryDTO> remarkHistoryDTO = remarkHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remarkHistoryDTO);
    }

    /**
     * {@code DELETE  /remark-histories/:id} : delete the "id" remarkHistory.
     *
     * @param id the id of the remarkHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remark-histories/{id}")
    public ResponseEntity<Void> deleteRemarkHistory(@PathVariable Long id) {
        log.debug("REST request to delete RemarkHistory : {}", id);
        remarkHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/remark-histories/list")
    public ResponseEntity<List<RemarkHistoryDTO>> createRemarkHistoryList(@RequestBody ArrayList<RemarkHistoryDTO> remarkHistoryDTOList)
        throws URISyntaxException {
        List<RemarkHistoryDTO> responceList = new ArrayList<RemarkHistoryDTO>();

        log.debug("REST request to save RemarkHistory : {}", remarkHistoryDTOList.size());

        for (RemarkHistoryDTO remarkHistoryDTO : remarkHistoryDTOList) {
            RemarkHistoryDTO result = remarkHistoryService.save(remarkHistoryDTO);
            responceList.add(result);
        }

        return ResponseEntity
            .created(new URI("/api/remark-histories/" + responceList.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, responceList.get(0).toString()))
            .body(responceList);
    }
}
