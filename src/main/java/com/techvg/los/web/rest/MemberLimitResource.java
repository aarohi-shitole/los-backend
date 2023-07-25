package com.techvg.los.web.rest;

import com.techvg.los.repository.MemberLimitRepository;
import com.techvg.los.service.MemberLimitQueryService;
import com.techvg.los.service.MemberLimitService;
import com.techvg.los.service.criteria.MemberLimitCriteria;
import com.techvg.los.service.dto.MemberLimitDTO;
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
 * REST controller for managing {@link com.techvg.los.domain.MemberLimit}.
 */
@RestController
@RequestMapping("/api")
public class MemberLimitResource {

    private final Logger log = LoggerFactory.getLogger(MemberLimitResource.class);

    private static final String ENTITY_NAME = "memberLimit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberLimitService memberLimitService;

    private final MemberLimitRepository memberLimitRepository;

    private final MemberLimitQueryService memberLimitQueryService;

    public MemberLimitResource(
        MemberLimitService memberLimitService,
        MemberLimitRepository memberLimitRepository,
        MemberLimitQueryService memberLimitQueryService
    ) {
        this.memberLimitService = memberLimitService;
        this.memberLimitRepository = memberLimitRepository;
        this.memberLimitQueryService = memberLimitQueryService;
    }

    /**
     * {@code POST  /member-limits} : Create a new memberLimit.
     *
     * @param memberLimitDTO the memberLimitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberLimitDTO, or with status {@code 400 (Bad Request)} if the memberLimit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-limits")
    public ResponseEntity<MemberLimitDTO> createMemberLimit(@RequestBody MemberLimitDTO memberLimitDTO) throws URISyntaxException {
        log.debug("REST request to save MemberLimit : {}", memberLimitDTO);
        if (memberLimitDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberLimit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberLimitDTO result = memberLimitService.save(memberLimitDTO);
        return ResponseEntity
            .created(new URI("/api/member-limits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-limits/:id} : Updates an existing memberLimit.
     *
     * @param id the id of the memberLimitDTO to save.
     * @param memberLimitDTO the memberLimitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberLimitDTO,
     * or with status {@code 400 (Bad Request)} if the memberLimitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberLimitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-limits/{id}")
    public ResponseEntity<MemberLimitDTO> updateMemberLimit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberLimitDTO memberLimitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberLimit : {}, {}", id, memberLimitDTO);
        if (memberLimitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberLimitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberLimitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberLimitDTO result = memberLimitService.update(memberLimitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberLimitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-limits/:id} : Partial updates given fields of an existing memberLimit, field will ignore if it is null
     *
     * @param id the id of the memberLimitDTO to save.
     * @param memberLimitDTO the memberLimitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberLimitDTO,
     * or with status {@code 400 (Bad Request)} if the memberLimitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberLimitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberLimitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-limits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberLimitDTO> partialUpdateMemberLimit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberLimitDTO memberLimitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberLimit partially : {}, {}", id, memberLimitDTO);
        if (memberLimitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberLimitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberLimitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberLimitDTO> result = memberLimitService.partialUpdate(memberLimitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberLimitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-limits} : get all the memberLimits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberLimits in body.
     */
    @GetMapping("/member-limits")
    public ResponseEntity<List<MemberLimitDTO>> getAllMemberLimits(
        MemberLimitCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberLimits by criteria: {}", criteria);
        Page<MemberLimitDTO> page = memberLimitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-limits/count} : count all the memberLimits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/member-limits/count")
    public ResponseEntity<Long> countMemberLimits(MemberLimitCriteria criteria) {
        log.debug("REST request to count MemberLimits by criteria: {}", criteria);
        return ResponseEntity.ok().body(memberLimitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-limits/:id} : get the "id" memberLimit.
     *
     * @param id the id of the memberLimitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberLimitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-limits/{id}")
    public ResponseEntity<MemberLimitDTO> getMemberLimit(@PathVariable Long id) {
        log.debug("REST request to get MemberLimit : {}", id);
        Optional<MemberLimitDTO> memberLimitDTO = memberLimitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberLimitDTO);
    }

    /**
     * {@code DELETE  /member-limits/:id} : delete the "id" memberLimit.
     *
     * @param id the id of the memberLimitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-limits/{id}")
    public ResponseEntity<Void> deleteMemberLimit(@PathVariable Long id) {
        log.debug("REST request to delete MemberLimit : {}", id);
        memberLimitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
