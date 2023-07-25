package com.techvg.los.web.rest;

import com.techvg.los.domain.enumeration.Status;
import com.techvg.los.repository.MemberRepository;
import com.techvg.los.service.MemberQueryService;
import com.techvg.los.service.MemberService;
import com.techvg.los.service.RemarkHistoryQueryService;
import com.techvg.los.service.RemarkHistoryService;
import com.techvg.los.service.SecurityUserService;
import com.techvg.los.service.criteria.MemberCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria.DocumentHelperFilter;
import com.techvg.los.service.dto.Count;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.dto.SecurityRoleDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.Member}.
 */
@RestController
@RequestMapping("/api")
public class MemberResource {

    private final Logger log = LoggerFactory.getLogger(MemberResource.class);

    private static final String ENTITY_NAME = "member";

    @Autowired
    private RemarkHistoryService remarkHistoryService;

    @Autowired
    private RemarkHistoryQueryService remarkHistoryQueryService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final MemberQueryService memberQueryService;

    @Autowired
    private SecurityUserService securityUserService;

    public MemberResource(MemberService memberService, MemberRepository memberRepository, MemberQueryService memberQueryService) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.memberQueryService = memberQueryService;
    }

    /**
     * {@code POST  /members} : Create a new member.
     *
     * @param memberDTO the memberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new memberDTO, or with status {@code 400 (Bad Request)} if
     *         the member has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) throws URISyntaxException {
        log.debug("REST request to save Member : {}", memberDTO);
        if (memberDTO.getId() != null) {
            throw new BadRequestAlertException("A new member cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (memberDTO.getCustomerId() != null) {
            throw new BadRequestAlertException("A new member cannot already have a customerId", ENTITY_NAME, "customerexists");
        }

        // PAN-----------------------------------------------------------------------------------------------------------------

        if (memberDTO.getPanCard() != null) {
            MemberCriteria criteria = new MemberCriteria();
            StringFilter panCard = new StringFilter();
            panCard.setEquals(memberDTO.getPanCard());
            criteria.setPanCard(panCard);

            long pancardCount = memberQueryService.countByCriteria(criteria);

            if (pancardCount > 0) {
                throw new BadRequestAlertException("Member already exists with same PAN", ENTITY_NAME, "idexists");
            }
        }

        // END--------------------------------------------------------------------------------------------------------------------

        // Aadhar-----------------------------------------------------------------------------------------------------------------
        if (memberDTO.getAadharCardNo() != null) {
            MemberCriteria criteria = new MemberCriteria();
            StringFilter aadharCardNo = new StringFilter();
            aadharCardNo.setEquals(memberDTO.getAadharCardNo());
            criteria.setAadharCardNo(aadharCardNo);

            long aadharCount = memberQueryService.countByCriteria(criteria);

            if (aadharCount > 0) {
                throw new BadRequestAlertException("Member already exists with same Aadhar", ENTITY_NAME, "idexists");
            }
        }
        // Aadhar
        // END------------------------------------------------------------------------------------------------------------------

        // Passport-----------------------------------------------------------------------------------------------------------------
        if (memberDTO.getPassportNo() != null) {
            MemberCriteria criteria = new MemberCriteria();
            StringFilter passportNo = new StringFilter();
            passportNo.setEquals(memberDTO.getPassportNo());
            criteria.setPassportNo(passportNo);

            long passportCount = memberQueryService.countByCriteria(criteria);

            if (passportCount > 0) {
                throw new BadRequestAlertException("Member already exists with same Passport No", ENTITY_NAME, "idexists");
            }
        }

        // END------------------------------------------------------------------------------------------------------------------

        MemberDTO result = memberService.save(memberDTO);
        return ResponseEntity
            .created(new URI("/api/members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /members/:id} : Updates an existing member.
     *
     * @param id        the id of the memberDTO to save.
     * @param memberDTO the memberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated memberDTO, or with status {@code 400 (Bad Request)} if
     *         the memberDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the memberDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/members/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<MemberDTO> updateMember(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MemberDTO memberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Member : {}, {}", id, memberDTO);
        if (memberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(memberDTO.getSecurityUserId());

        boolean hasMemberRejected = false;

        boolean hasCheckerUser = false;

        ArrayList<RemarkHistoryDTO> list = memberDTO.getRemarkList() != null ? memberDTO.getRemarkList() : new ArrayList<>();
        if (securityUserDTO.isPresent()) {
            SecurityUserDTO securityUser = securityUserDTO.get();

            hasCheckerUser = isRoleInSet("ROLE_CHECKER", securityUser.getSecurityRoles()); // securityUser.getSecurityRoles().contains("ROLE_CHECKER");

            if (hasCheckerUser) {
                // -----------------------------------------------------------------------------------------------------

                for (RemarkHistoryDTO remarkHistoryDTO : list) {
                    if (null != remarkHistoryDTO.getFreeField3() && remarkHistoryDTO.getFreeField3().equalsIgnoreCase("Rejected")) {
                        hasMemberRejected = true;
                    }
                }
            }
            // -------------------------------------------------------------------------------------------------------

        }

        if (hasMemberRejected) {
            memberDTO.setStatus(Status.REJECTED);
        } else if (
            memberDTO.getStatus() == Status.REJECTED &&
            !(null != memberDTO.getHasFilledInfoWithDoc() && memberDTO.getHasFilledInfoWithDoc())
        ) {
            memberDTO.setStatus(Status.DRAFT);
        } else if (
            memberDTO.getStatus() == Status.REJECTED && (null != memberDTO.getHasFilledInfoWithDoc() && memberDTO.getHasFilledInfoWithDoc())
        ) {
            memberDTO.setStatus(Status.REJECTED);
        } else if (
            hasCheckerUser &&
            memberDTO.getStatus() != Status.REJECTED &&
            (null != memberDTO.getHasFilledInfoWithDoc() && memberDTO.getHasFilledInfoWithDoc())
        ) {
            memberDTO.setStatus(Status.VERIFIED);
        }

        if (null != memberDTO.getHasFilledInfoWithDoc() && memberDTO.getHasFilledInfoWithDoc()) {
            memberService.checkDocument(memberDTO);
        }

        MemberDTO result = memberService.update(memberDTO);

        // -----------------------------------------------------------------------------------------------------

        ArrayList<RemarkHistoryDTO> resultListOfRemark = new ArrayList<RemarkHistoryDTO>();

        for (RemarkHistoryDTO remarkHistoryDTO : list) {
            remarkHistoryDTO.setReferenceId(result.getId());
            remarkHistoryDTO.setLastModified(result.getLastModified());
            remarkHistoryDTO.setLastModifiedBy(result.getLastModifiedBy());
            remarkHistoryDTO.setSecurityUserId(result.getSecurityUserId());

            RemarkHistoryCriteria remarkHistoryCriteria = new RemarkHistoryCriteria();
            LongFilter refId = new LongFilter();
            refId.setEquals(remarkHistoryDTO.getReferenceId());

            StringFilter remarkSubType = new StringFilter();
            remarkSubType.setEquals(remarkHistoryDTO.getRemarkSubType());

            StringFilter remarkType = new StringFilter();
            remarkType.setEquals(remarkHistoryDTO.getRemarkType());

            DocumentHelperFilter tag = new DocumentHelperFilter();
            tag.setEquals(remarkHistoryDTO.getTag());

            remarkHistoryCriteria.setReferenceId(refId);
            remarkHistoryCriteria.setRemarkSubType(remarkSubType);
            remarkHistoryCriteria.setRemarkType(remarkType);
            remarkHistoryCriteria.setTag(tag);

            List<RemarkHistoryDTO> remarkHistoryList = remarkHistoryQueryService.findByCriteria(remarkHistoryCriteria);

            if (remarkHistoryList != null) {
                for (RemarkHistoryDTO remarkHistory : remarkHistoryList) {
                    remarkHistoryDTO.setId(remarkHistory.getId());
                }
            }

            RemarkHistoryDTO submitedRemark = remarkHistoryService.save(remarkHistoryDTO);
            resultListOfRemark.add(submitedRemark);
        }

        result.setRemarkList(resultListOfRemark);
        // -------------------------------------------------------------------------------------------------------

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberDTO.getId().toString()))
            .body(result);
    }

    boolean isRoleInSet(String imcomingRole, Set<? extends SecurityRoleDTO> set) {
        boolean result = false;

        for (SecurityRoleDTO o : set) {
            if (o.getRoleName().equalsIgnoreCase(imcomingRole)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * {@code PATCH  /members/:id} : Partial updates given fields of an existing
     * member, field will ignore if it is null
     *
     * @param id        the id of the memberDTO to save.
     * @param memberDTO the memberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated memberDTO, or with status {@code 400 (Bad Request)} if
     *         the memberDTO is not valid, or with status {@code 404 (Not Found)} if
     *         the memberDTO is not found, or with status
     *         {@code 500 (Internal Server Error)} if the memberDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberDTO> partialUpdateMember(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MemberDTO memberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Member partially : {}, {}", id, memberDTO);
        if (memberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberDTO> result = memberService.partialUpdate(memberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /members} : get all the members.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of members in body.
     */
    @GetMapping("/members")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW')")
    public ResponseEntity<List<MemberDTO>> getAllMembers(
        MemberCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Members by criteria: {}", criteria);
        Page<MemberDTO> page = memberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /members/count} : count all the members.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/members/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','LOAN_APPL_VIEW')")
    public ResponseEntity<Count> countMembers(MemberCriteria criteria) {
        log.debug("REST request to count Members by criteria: {}", criteria);
        Count count = new Count();
        count.count = memberQueryService.countByCriteria(criteria);
        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /members/:id} : get the "id" member.
     *
     * @param id the id of the memberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the memberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/members/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_VIEW','MEMBER_EDIT')")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long id) {
        log.debug("REST request to get Member : {}", id);
        Optional<MemberDTO> memberDTO = memberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberDTO);
    }

    /**
     * {@code DELETE  /members/:id} : delete the "id" member.
     *
     * @param id the id of the memberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/members/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','MEMBER_EDIT')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        log.debug("REST request to delete Member : {}", id);
        memberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
