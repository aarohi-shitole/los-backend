package com.techvg.los.web.rest;

import com.techvg.los.domain.LoanApplicationAggregateCount;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.repository.LoanApplicationsHistoryRepository;
import com.techvg.los.service.LoanApplicationMembersQueryService;
import com.techvg.los.service.LoanApplicationsHistoryQueryService;
import com.techvg.los.service.LoanApplicationsHistoryService;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria;
import com.techvg.los.service.criteria.LoanApplicationsHistoryCriteria;
import com.techvg.los.service.criteria.LoanApplicationsHistoryCriteria.LoanStatusFilter;
import com.techvg.los.service.dto.Count;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.LoanApplicationsHistory}.
 */
@RestController
@RequestMapping("/api")
public class LoanApplicationsHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsHistoryResource.class);

    private static final String ENTITY_NAME = "loanApplicationsHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanApplicationsHistoryService loanApplicationsHistoryService;

    private final LoanApplicationsHistoryRepository loanApplicationsHistoryRepository;

    private final LoanApplicationsHistoryQueryService loanApplicationsHistoryQueryService;

    @Autowired
    private LoanApplicationMembersQueryService loanApplicationMembersQueryService;

    public LoanApplicationsHistoryResource(
        LoanApplicationsHistoryService loanApplicationsHistoryService,
        LoanApplicationsHistoryRepository loanApplicationsHistoryRepository,
        LoanApplicationsHistoryQueryService loanApplicationsHistoryQueryService
    ) {
        this.loanApplicationsHistoryService = loanApplicationsHistoryService;
        this.loanApplicationsHistoryRepository = loanApplicationsHistoryRepository;
        this.loanApplicationsHistoryQueryService = loanApplicationsHistoryQueryService;
    }

    /**
     * {@code POST  /loan-applications} : Create a new loanApplicationsHistory.
     *
     * @param loanApplicationsHistoryDTO the loanApplicationsHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new loanApplicationsHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplicationsHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PostMapping("/loan-applications-history")
    //    public ResponseEntity<LoanApplicationsHistoryDTO> createLoanApplicationsHistory(@RequestBody LoanApplicationsHistoryDTO loanApplicationsHistoryDTO)
    //        throws URISyntaxException {
    //        log.debug("REST request to save LoanApplicationsHistory : {}", loanApplicationsHistoryDTO);
    //        if (loanApplicationsHistoryDTO.getId() != null) {
    //            throw new BadRequestAlertException("A new loanApplicationsHistory cannot already have an ID", ENTITY_NAME, "idexists");
    //        }
    //
    //
    //
    //        LoanApplicationsHistoryDTO result = loanApplicationsHistoryService.save(loanApplicationsHistoryDTO);
    //        return ResponseEntity
    //            .created(new URI("/api/loan-applications/" + result.getId()))
    //            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
    //            .body(result);
    //    }

    /**
     * {@code PUT  /loan-applications/:id} : Updates an existing loanApplicationsHistory.
     *
     * @param id                  the id of the loanApplicationsHistoryDTO to save.
     * @param loanApplicationsHistoryDTO the loanApplicationsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated loanApplicationsHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplicationsHistoryDTO is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         loanApplicationsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-applications-history/{id}")
    public ResponseEntity<LoanApplicationsHistoryDTO> updateLoanApplicationsHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanApplicationsHistoryDTO loanApplicationsHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanApplicationsHistory : {}, {}", id, loanApplicationsHistoryDTO);
        if (loanApplicationsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanApplicationsHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanApplicationsHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanApplicationsHistoryDTO result = loanApplicationsHistoryService.update(loanApplicationsHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanApplicationsHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-applications/:id} : Partial updates given fields of an
     * existing loanApplicationsHistory, field will ignore if it is null
     *
     * @param id                  the id of the loanApplicationsHistoryDTO to save.
     * @param loanApplicationsHistoryDTO the loanApplicationsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated loanApplicationsHistoryDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplicationsHistoryDTO is not valid, or
     *         with status {@code 404 (Not Found)} if the loanApplicationsHistoryDTO is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         loanApplicationsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-applications-history/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanApplicationsHistoryDTO> partialUpdateLoanApplicationsHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanApplicationsHistoryDTO loanApplicationsHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanApplicationsHistory partially : {}, {}", id, loanApplicationsHistoryDTO);
        if (loanApplicationsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanApplicationsHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanApplicationsHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanApplicationsHistoryDTO> result = loanApplicationsHistoryService.partialUpdate(loanApplicationsHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanApplicationsHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-applications} : get all the loanApplicationsHistory.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of loanApplicationsHistory in body.
     */
    @GetMapping("/loan-applications-history")
    public ResponseEntity<List<LoanApplicationsHistoryDTO>> getAllLoanApplicationsHistory(
        LoanApplicationsHistoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanApplicationsHistory by criteria: {}", criteria);

        if (criteria.getMemberId() != null) {
            LoanApplicationMembersCriteria memberCriteria = new LoanApplicationMembersCriteria();
            memberCriteria.setMemberId(criteria.getMemberId());
            LongFilter idFilter = new LongFilter();
            List<LoanApplicationMembersDTO> loanAppMemberList = loanApplicationMembersQueryService.findByCriteria(memberCriteria);
            for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
                List<Long> appIdList = new ArrayList<>();
                appIdList.add(dtoObj.getLoanApplicationId());
                idFilter.setIn(appIdList);
            }
            criteria.setMemberId(null);
            criteria.setId(idFilter);
        }
        Page<LoanApplicationsHistoryDTO> page = loanApplicationsHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-applications/count} : count all the loanApplicationsHistory.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/loan-applications-history/count")
    public ResponseEntity<Count> countLoanApplicationsHistory(LoanApplicationsHistoryCriteria criteria) {
        log.debug("REST request to count LoanApplicationsHistory by criteria: {}", criteria);

        Count count = new Count();
        if (criteria.getMemberId() != null) {
            LoanApplicationMembersCriteria memberCriteria = new LoanApplicationMembersCriteria();
            memberCriteria.setMemberId(criteria.getMemberId());
            LongFilter idFilter = new LongFilter();
            List<LoanApplicationMembersDTO> loanAppMemberList = loanApplicationMembersQueryService.findByCriteria(memberCriteria);
            for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
                List<Long> appIdList = new ArrayList<>();
                appIdList.add(dtoObj.getLoanApplicationId());
                idFilter.setIn(appIdList);
            }
            criteria.setMemberId(null);
            criteria.setId(idFilter);
        }
        count.count = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /loan-applications/aggregate/count} : count all the loanApplicationsHistory.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/loan-applications-history/aggregate/count")
    public ResponseEntity<LoanApplicationAggregateCount> aggregateCountLoanApplicationsHistory(LoanApplicationsHistoryCriteria criteria) {
        log.debug("REST request to count LoanApplicationsHistory by criteria: {}", criteria);

        LoanApplicationAggregateCount count = new LoanApplicationAggregateCount();
        count.totalLoanApplicationCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        LoanStatusFilter status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_LEG_VAL);
        criteria.setStatus(status);
        count.AwaitedForLegalNvalCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.DRAFT);
        criteria.setStatus(status);
        count.daftLoanApplicationCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.PENDING);
        criteria.setStatus(status);
        count.pendingLoanApplicationCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.SANCTIONED);
        criteria.setStatus(status);
        count.sancationedLoanApplicationCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.VERIFIED);
        criteria.setStatus(status);
        count.verified = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_LEG_VAL);
        criteria.setStatus(status);
        count.awaitedForLegalVal = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_COMM_REM);
        criteria.setStatus(status);
        count.awaitedCommRemark = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.APPLIED);
        criteria.setStatus(status);
        count.AppliedLoanApplicationCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_DIR_REM);
        criteria.setStatus(status);
        count.AwaitedForDirRemCount = loanApplicationsHistoryQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /loan-applications/:id} : get the "id" loanApplicationsHistory.
     *
     * @param id the id of the loanApplicationsHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the loanApplicationsHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-applications-history/{id}")
    public ResponseEntity<LoanApplicationsHistoryDTO> getLoanApplicationsHistory(@PathVariable Long id) {
        log.debug("REST request to get LoanApplicationsHistory : {}", id);
        Optional<LoanApplicationsHistoryDTO> loanApplicationsHistoryDTO = loanApplicationsHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanApplicationsHistoryDTO);
    }

    /**
     * {@code DELETE  /loan-applications/:id} : delete the "id" loanApplicationsHistory.
     *
     * @param id the id of the loanApplicationsHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-applications-history/{id}")
    public ResponseEntity<Void> deleteLoanApplicationsHistory(@PathVariable Long id) {
        log.debug("REST request to delete LoanApplicationsHistory : {}", id);
        loanApplicationsHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
