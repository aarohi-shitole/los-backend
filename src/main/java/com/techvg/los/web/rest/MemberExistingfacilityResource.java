package com.techvg.los.web.rest;

import com.techvg.los.repository.MemberExistingfacilityRepository;
import com.techvg.los.service.MemberExistingfacilityQueryService;
import com.techvg.los.service.MemberExistingfacilityService;
import com.techvg.los.service.criteria.MemberExistingfacilityCriteria;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.MemberExistingfacility}.
 */
@RestController
@RequestMapping("/api")
public class MemberExistingfacilityResource {

    private final Logger log = LoggerFactory.getLogger(MemberExistingfacilityResource.class);

    private static final String ENTITY_NAME = "memberExistingfacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberExistingfacilityService memberExistingfacilityService;

    private final MemberExistingfacilityRepository memberExistingfacilityRepository;

    private final MemberExistingfacilityQueryService memberExistingfacilityQueryService;

    public MemberExistingfacilityResource(
        MemberExistingfacilityService memberExistingfacilityService,
        MemberExistingfacilityRepository memberExistingfacilityRepository,
        MemberExistingfacilityQueryService memberExistingfacilityQueryService
    ) {
        this.memberExistingfacilityService = memberExistingfacilityService;
        this.memberExistingfacilityRepository = memberExistingfacilityRepository;
        this.memberExistingfacilityQueryService = memberExistingfacilityQueryService;
    }

    /**
     * {@code POST  /member-existingfacilities} : Create a new memberExistingfacility.
     *
     * @param memberExistingfacilityDTO the memberExistingfacilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberExistingfacilityDTO, or with status {@code 400 (Bad Request)} if the memberExistingfacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-existingfacilities")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<MemberExistingfacilityDTO> createMemberExistingfacility(
        @RequestBody MemberExistingfacilityDTO memberExistingfacilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MemberExistingfacility : {}", memberExistingfacilityDTO);
        if (memberExistingfacilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberExistingfacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberExistingfacilityDTO result = memberExistingfacilityService.save(memberExistingfacilityDTO);
        return ResponseEntity
            .created(new URI("/api/member-existingfacilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-existingfacilities/:id} : Updates an existing memberExistingfacility.
     *
     * @param id the id of the memberExistingfacilityDTO to save.
     * @param memberExistingfacilityDTO the memberExistingfacilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberExistingfacilityDTO,
     * or with status {@code 400 (Bad Request)} if the memberExistingfacilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberExistingfacilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-existingfacilities/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<MemberExistingfacilityDTO> updateMemberExistingfacility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberExistingfacilityDTO memberExistingfacilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberExistingfacility : {}, {}", id, memberExistingfacilityDTO);
        if (memberExistingfacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberExistingfacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberExistingfacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberExistingfacilityDTO result = memberExistingfacilityService.update(memberExistingfacilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberExistingfacilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-existingfacilities/:id} : Partial updates given fields of an existing memberExistingfacility, field will ignore if it is null
     *
     * @param id the id of the memberExistingfacilityDTO to save.
     * @param memberExistingfacilityDTO the memberExistingfacilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberExistingfacilityDTO,
     * or with status {@code 400 (Bad Request)} if the memberExistingfacilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberExistingfacilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberExistingfacilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-existingfacilities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberExistingfacilityDTO> partialUpdateMemberExistingfacility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberExistingfacilityDTO memberExistingfacilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberExistingfacility partially : {}, {}", id, memberExistingfacilityDTO);
        if (memberExistingfacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberExistingfacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberExistingfacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberExistingfacilityDTO> result = memberExistingfacilityService.partialUpdate(memberExistingfacilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberExistingfacilityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-existingfacilities} : get all the memberExistingfacilities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberExistingfacilities in body.
     */
    @GetMapping("/member-existingfacilities")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','MEMBER_EDIT')")
    public ResponseEntity<List<MemberExistingfacilityDTO>> getAllMemberExistingfacilities(
        MemberExistingfacilityCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberExistingfacilities by criteria: {}", criteria);
        Page<MemberExistingfacilityDTO> page = memberExistingfacilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-existingfacilities/count} : count all the memberExistingfacilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/member-existingfacilities/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','MEMBER_EDIT')")
    public ResponseEntity<Long> countMemberExistingfacilities(MemberExistingfacilityCriteria criteria) {
        log.debug("REST request to count MemberExistingfacilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(memberExistingfacilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-existingfacilities/:id} : get the "id" memberExistingfacility.
     *
     * @param id the id of the memberExistingfacilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberExistingfacilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-existingfacilities/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','MEMBER_EDIT')")
    public ResponseEntity<MemberExistingfacilityDTO> getMemberExistingfacility(@PathVariable Long id) {
        log.debug("REST request to get MemberExistingfacility : {}", id);
        Optional<MemberExistingfacilityDTO> memberExistingfacilityDTO = memberExistingfacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberExistingfacilityDTO);
    }

    /**
     * {@code DELETE  /member-existingfacilities/:id} : delete the "id" memberExistingfacility.
     *
     * @param id the id of the memberExistingfacilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-existingfacilities/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<Void> deleteMemberExistingfacility(@PathVariable Long id) {
        log.debug("REST request to delete MemberExistingfacility : {}", id);
        memberExistingfacilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /member-existingfacilitiesList} : Create a new memberExistingfacility by list.
     *
     * @param memberExistingfacilityList the memberExistingfacilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberExistingfacility list, or with status {@code 400 (Bad Request)} if the memberExistingfacility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-existingfacilitiesList")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<List<MemberExistingfacilityDTO>> createMemberExistingfacility(
        @RequestBody List<MemberExistingfacilityDTO> memberExistingfacilityList
    ) throws URISyntaxException {
        log.debug("REST request to save list of MemberExistingfacility : {}", memberExistingfacilityList);
        List<MemberExistingfacilityDTO> resultList = new ArrayList<>();

        for (MemberExistingfacilityDTO memberExistingfacilityDTO : memberExistingfacilityList) {
            if (memberExistingfacilityDTO.getId() != null) {
                throw new BadRequestAlertException("A new memberExistingfacility cannot already have an ID", ENTITY_NAME, "idexists");
            }
            MemberExistingfacilityDTO result = memberExistingfacilityService.save(memberExistingfacilityDTO);
            resultList.add(result);
        }
        return ResponseEntity.created(new URI("/api/member-existingfacilities/")).body(resultList);
    }
}
