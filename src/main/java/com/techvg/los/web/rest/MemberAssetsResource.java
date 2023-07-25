package com.techvg.los.web.rest;

import com.techvg.los.repository.MemberAssetsRepository;
import com.techvg.los.service.MemberAssetsQueryService;
import com.techvg.los.service.MemberAssetsService;
import com.techvg.los.service.MortgagedAssetsService;
import com.techvg.los.service.criteria.MemberAssetsCriteria;
import com.techvg.los.service.criteria.MortgagedAssetsCriteria;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.dto.MortgagedAssetsDTO;
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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.MemberAssets}.
 */
@RestController
@RequestMapping("/api")
public class MemberAssetsResource {

    private final Logger log = LoggerFactory.getLogger(MemberAssetsResource.class);

    private static final String ENTITY_NAME = "memberAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberAssetsService memberAssetsService;

    private final MemberAssetsRepository memberAssetsRepository;

    private final MemberAssetsQueryService memberAssetsQueryService;

    @Autowired
    private MortgagedAssetsService mortgagedAssetsService;

    public MemberAssetsResource(
        MemberAssetsService memberAssetsService,
        MemberAssetsRepository memberAssetsRepository,
        MemberAssetsQueryService memberAssetsQueryService
    ) {
        this.memberAssetsService = memberAssetsService;
        this.memberAssetsRepository = memberAssetsRepository;
        this.memberAssetsQueryService = memberAssetsQueryService;
    }

    /**
     * {@code POST  /member-assets} : Create a new memberAssets.
     *
     * @param memberAssetsDTO the memberAssetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberAssetsDTO, or with status {@code 400 (Bad Request)} if the memberAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-assets")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<MemberAssetsDTO> createMemberAssets(@RequestBody MemberAssetsDTO memberAssetsDTO) throws URISyntaxException {
        log.debug("REST request to save MemberAssets : {}", memberAssetsDTO);
        if (memberAssetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberAssetsDTO result = memberAssetsService.save(memberAssetsDTO);
        return ResponseEntity
            .created(new URI("/api/member-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/member-assetlist")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<ArrayList<MemberAssetsDTO>> createMemberAssets(@RequestBody ArrayList<MemberAssetsDTO> memberAssetsList)
        throws URISyntaxException {
        log.debug("REST request to save MemberAssets : {}", memberAssetsList);

        ArrayList<MemberAssetsDTO> resultList = new ArrayList<>();
        for (MemberAssetsDTO MemberAsset : memberAssetsList) {
            //            if (MemberAsset.getId() != null) {
            //                throw new BadRequestAlertException("A new memberAssets cannot already have an ID", ENTITY_NAME, "idexists");
            //            }
            MemberAssetsDTO result = memberAssetsService.save(MemberAsset);
            resultList.add(result);
        }

        return ResponseEntity.ok().body(resultList);
    }

    /**
     * {@code PUT  /member-assets/:id} : Updates an existing memberAssets.
     *
     * @param id the id of the memberAssetsDTO to save.
     * @param memberAssetsDTO the memberAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the memberAssetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-assets/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<MemberAssetsDTO> updateMemberAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberAssetsDTO memberAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberAssets : {}, {}", id, memberAssetsDTO);
        if (memberAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberAssetsDTO result = memberAssetsService.update(memberAssetsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberAssetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-assets/:id} : Partial updates given fields of an existing memberAssets, field will ignore if it is null
     *
     * @param id the id of the memberAssetsDTO to save.
     * @param memberAssetsDTO the memberAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the memberAssetsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberAssetsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberAssetsDTO> partialUpdateMemberAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberAssetsDTO memberAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberAssets partially : {}, {}", id, memberAssetsDTO);
        if (memberAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberAssetsDTO> result = memberAssetsService.partialUpdate(memberAssetsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberAssetsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-assets} : get all the memberAssets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberAssets in body.
     */
    @GetMapping("/member-assets")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_EDIT','LOAN_APPL_VIEW')")
    public ResponseEntity<List<MemberAssetsDTO>> getAllMemberAssets(
        MemberAssetsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberAssets by criteria: {}", criteria);
        Page<MemberAssetsDTO> page = memberAssetsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-assets/count} : count all the memberAssets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/member-assets/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW','LOAN_APPL_EDIT')")
    public ResponseEntity<Long> countMemberAssets(MemberAssetsCriteria criteria) {
        log.debug("REST request to count MemberAssets by criteria: {}", criteria);
        return ResponseEntity.ok().body(memberAssetsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-assets/:id} : get the "id" memberAssets.
     *
     * @param id the id of the memberAssetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberAssetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-assets/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','MEMBER_EDIT','LOAN_APPL_VIEW')")
    public ResponseEntity<MemberAssetsDTO> getMemberAssets(@PathVariable Long id) {
        log.debug("REST request to get MemberAssets : {}", id);
        Optional<MemberAssetsDTO> memberAssetsDTO = memberAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberAssetsDTO);
    }

    /**
     * {@code DELETE  /member-assets/:id} : delete the "id" memberAssets.
     *
     * @param id the id of the memberAssetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-assets/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<Void> deleteMemberAssets(@PathVariable Long id) {
        log.debug("REST request to delete MemberAssets : {}", id);
        memberAssetsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/member-assets/mortgage")
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_EDIT','LOAN_APPL_VIEW')")
    public ResponseEntity<List<MortgagedAssetsDTO>> getAllMortgageAssets(
        MortgagedAssetsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberAssets by criteria: {}", criteria);
        Page<MortgagedAssetsDTO> page = mortgagedAssetsService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/member-assets/loan")
    public ResponseEntity<List<MemberAssetsDTO>> getMemberAssets(
        @RequestParam("memberId") Long memberId,
        @RequestParam("loanApplicationsId") Long loanApplicationsId
    ) {
        log.debug("REST request to get MemberAssets loans by entering member id and loan application id: {}");
        List<MemberAssetsDTO> memberAssetsList = memberAssetsService.getLoanMemberAssets(memberId, loanApplicationsId);
        return ResponseEntity.ok().body(memberAssetsList);
    }
}
