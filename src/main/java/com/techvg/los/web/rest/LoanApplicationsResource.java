package com.techvg.los.web.rest;

import com.techvg.los.domain.ApplicantEligibility;
import com.techvg.los.domain.LoanApplicationAggregateCount;
import com.techvg.los.domain.enumeration.ApplicantType;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.repository.LoanApplicationsHistoryRepository;
import com.techvg.los.repository.LoanApplicationsRepository;
import com.techvg.los.service.LoanApplicationMembersQueryService;
import com.techvg.los.service.LoanApplicationsHistoryService;
import com.techvg.los.service.LoanApplicationsQueryService;
import com.techvg.los.service.LoanApplicationsService;
import com.techvg.los.service.MemberAssetsQueryService;
import com.techvg.los.service.MemberBankQueryService;
import com.techvg.los.service.MemberService;
import com.techvg.los.service.RemarkHistoryQueryService;
import com.techvg.los.service.RemarkHistoryService;
import com.techvg.los.service.SecurityUserService;
import com.techvg.los.service.VerifyDocuments;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria.ApplicantTypeFilter;
import com.techvg.los.service.criteria.LoanApplicationsCriteria;
import com.techvg.los.service.criteria.LoanApplicationsCriteria.LoanStatusFilter;
import com.techvg.los.service.criteria.MemberAssetsCriteria;
import com.techvg.los.service.criteria.MemberBankCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria.DocumentHelperFilter;
import com.techvg.los.service.dto.Count;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.dto.MemberBankDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.dto.SecurityRoleDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
 * REST controller for managing {@link com.techvg.los.domain.LoanApplications}.
 */
@RestController
@RequestMapping("/api")
public class LoanApplicationsResource {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsResource.class);

    private static final String ENTITY_NAME = "loanApplications";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanApplicationsService loanApplicationsService;

    private final LoanApplicationsRepository loanApplicationsRepository;

    private final LoanApplicationsQueryService loanApplicationsQueryService;

    @Autowired
    private LoanApplicationMembersQueryService loanApplicationMembersQueryService;

    @Autowired
    private LoanApplicationsHistoryService loanApplicationsHistoryService;

    @Autowired
    private LoanApplicationsHistoryRepository loanApplicationsHistoryRepository;

    @Autowired
    private RemarkHistoryService remarkHistoryService;

    @Autowired
    private RemarkHistoryQueryService remarkHistoryQueryService;

    @Autowired
    private MemberBankQueryService memberBankQueryService;

    @Autowired
    private MemberAssetsQueryService memberAssetsQueryService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private MemberService membersService;

    @Autowired
    private VerifyDocuments verifyDocuments;

    public LoanApplicationsResource(
        LoanApplicationsService loanApplicationsService,
        LoanApplicationsRepository loanApplicationsRepository,
        LoanApplicationsQueryService loanApplicationsQueryService
    ) {
        this.loanApplicationsService = loanApplicationsService;
        this.loanApplicationsRepository = loanApplicationsRepository;
        this.loanApplicationsQueryService = loanApplicationsQueryService;
    }

    /**
     * {@code POST  /loan-applications} : Create a new loanApplications.
     *
     * @param loanApplicationsDTO the loanApplicationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new loanApplicationsDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplications has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-applications")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_EDIT')")
    public ResponseEntity<LoanApplicationsDTO> createLoanApplications(@RequestBody LoanApplicationsDTO loanApplicationsDTO)
        throws URISyntaxException {
        log.debug("REST request to save LoanApplications : {}", loanApplicationsDTO);
        if (loanApplicationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanApplications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (loanApplicationsDTO.getApplicationNo() != null) {
            throw new BadRequestAlertException(
                "A new loanApplications cannot already have an loan application number",
                ENTITY_NAME,
                "applicationNoexists"
            );
        }
        if (loanApplicationsDTO.getProduct() == null) {
            throw new BadRequestAlertException(
                "A loanApplication should have loan product..!Please select required loan product ",
                ENTITY_NAME,
                "loanproduct not exist"
            );
        }

        MemberBankCriteria memberBankCriteria = new MemberBankCriteria();
        LongFilter memberId = new LongFilter();
        memberId.setEquals(loanApplicationsDTO.getMemberId());
        memberBankCriteria.setMemberId(memberId);
        List<MemberBankDTO> memberBankList = memberBankQueryService.findByCriteria(memberBankCriteria);
        if (memberBankList.isEmpty()) {
            throw new BadRequestAlertException(
                "A loanApplication should have Member associate with at list one bank..Please! Fill required bank details ",
                ENTITY_NAME,
                "bank not exist for member"
            );
        }

        LoanApplicationsDTO result = loanApplicationsService.save(loanApplicationsDTO);

        LoanApplicationsHistoryDTO loanApplicationsHistoryDTO = new LoanApplicationsHistoryDTO();
        Optional<LoanApplicationsDTO> loanApplication = loanApplicationsService.findOne(result.getId());

        if (loanApplication.isPresent()) {
            loanApplicationsHistoryDTO.setLoanApplication(loanApplication.get());
            loanApplicationsHistoryDTO.setApplicationNo(loanApplication.get().getApplicationNo());
            loanApplicationsHistoryDTO.setAssignedFrom(loanApplication.get().getAssignedFrom());
            loanApplicationsHistoryDTO.setAssignedTo(loanApplication.get().getAssignedTo());
            loanApplicationsHistoryDTO.setCommityAmt(loanApplication.get().getCommityAmt());
            loanApplicationsHistoryDTO.setCommityRoi(loanApplication.get().getCommityRoi());
            loanApplicationsHistoryDTO.setCostOfInvestment(loanApplication.get().getCostOfInvestment());
            loanApplicationsHistoryDTO.setDemandAmount(loanApplication.get().getDemandAmount());
            loanApplicationsHistoryDTO.setDemandedLandAreaInHector(loanApplication.get().getDemandedLandAreaInHector());
            loanApplicationsHistoryDTO.setDownPaymentAmt(loanApplication.get().getDownPaymentAmt());
            loanApplicationsHistoryDTO.setExtentMorgageValue(loanApplication.get().getExtentMorgageValue());
            // loanApplicationsHistoryDTO.setFreeField3(loanApplication.get().getFreeField3());
            // loanApplicationsHistoryDTO.setFreeField4(loanApplication.get().getFreeField4());
            // loanApplicationsHistoryDTO.setFreeField5(loanApplication.get().getFreeField5());
            // loanApplicationsHistoryDTO.setFreeField6(loanApplication.get().getFreeField6());
            // loanApplicationsHistoryDTO.setFreeField7(loanApplication.get().getFreeField7());
            loanApplicationsHistoryDTO.setIsInsured(loanApplication.get().getIsInsured());
            loanApplicationsHistoryDTO.setLastModified(loanApplication.get().getLastModified());
            loanApplicationsHistoryDTO.setLastModifiedBy(loanApplication.get().getLastModifiedBy());
            loanApplicationsHistoryDTO.setLoanBenefitingArea(loanApplication.get().getLoanBenefitingArea());
            loanApplicationsHistoryDTO.setLoanPurpose(loanApplication.get().getLoanPurpose());
            loanApplicationsHistoryDTO.setLoanSteps(loanApplication.get().getLoanSteps());
            loanApplicationsHistoryDTO.setbranch(loanApplication.get().getBranch());
            loanApplicationsHistoryDTO.setProduct(loanApplication.get().getProduct());
            // loanApplicationsHistoryDTO.setSecurityUserId(loanApplication.get().getSecurityUserId());
            loanApplicationsHistoryDTO.setLtvRatio(loanApplication.get().getLtvRatio());
            loanApplicationsHistoryDTO.setMargin(loanApplication.get().getMargin());
            loanApplicationsHistoryDTO.setMemberId(loanApplication.get().getMemberId());
            loanApplicationsHistoryDTO.setMoratorium(loanApplication.get().getMoratorium());
            loanApplicationsHistoryDTO.setMortgageDate(loanApplication.get().getMortgageDate());
            loanApplicationsHistoryDTO.setMortgageDeedNo(loanApplication.get().getMortgageDeedNo());
            loanApplicationsHistoryDTO.setPenalInterest(loanApplication.get().getPenalInterest());
            loanApplicationsHistoryDTO.setProcessingFee(loanApplication.get().getProcessingFee());
            loanApplicationsHistoryDTO.setRoi(loanApplication.get().getRoi());
            loanApplicationsHistoryDTO.setSanctionAmt(loanApplication.get().getSanctionAmt());
            loanApplicationsHistoryDTO.setSanctionRoi(loanApplication.get().getSanctionRoi());
            loanApplicationsHistoryDTO.setSeasonOfCropLoan(loanApplication.get().getSeasonOfCropLoan());
            loanApplicationsHistoryDTO.setSecurityDocUrl(loanApplication.get().getSecurityDocUrl());
            loanApplicationsHistoryDTO.setSecurityOffered(loanApplication.get().getSecurityOffered());
            loanApplicationsHistoryDTO.setStatus(loanApplication.get().getStatus());
            loanApplicationsHistoryDTO.setYear(loanApplication.get().getYear());
        }
        LoanApplicationsHistoryDTO result1 = loanApplicationsHistoryService.save(loanApplicationsHistoryDTO);

        return ResponseEntity
            .created(new URI("/api/loan-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-applications/:id} : Updates an existing loanApplications.
     *
     * @param id                  the id of the loanApplicationsDTO to save.
     * @param loanApplicationsDTO the loanApplicationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated loanApplicationsDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplicationsDTO is not valid, or
     *         with status {@code 500 (Internal Server Error)} if the
     *         loanApplicationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-applications/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_EDIT')")
    public ResponseEntity<LoanApplicationsDTO> updateLoanApplications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanApplicationsDTO loanApplicationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanApplications : {}, {}", id, loanApplicationsDTO);
        if (loanApplicationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanApplicationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanApplicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (loanApplicationsDTO.getStatus() == LoanStatus.VERIFIED) {
            MemberAssetsCriteria criteria = new MemberAssetsCriteria();
            long loanApplicationsId = loanApplicationsDTO.getId();

            LongFilter loanId = new LongFilter();
            loanId.setEquals(loanApplicationsId);
            criteria.setLoanApplicationsId(loanId);
            List<MemberAssetsDTO> memberAssetlist = memberAssetsQueryService.findByCriteria(criteria);

            if (memberAssetlist.isEmpty()) {
                throw new BadRequestAlertException("Please add asset for loan", ENTITY_NAME, "Asset not exist for this loan application!");
            }
        }

        if (loanApplicationsDTO.getStatus().equals(LoanStatus.SANCTIONED) || loanApplicationsDTO.getStatus().equals(LoanStatus.DISBURSED)) {
            String appNo = loanApplicationsDTO.getApplicationNo();
            if (appNo.contains("T")) {
                String a = appNo.substring(0, appNo.length() - 1);
                loanApplicationsDTO.setApplicationNo(a);
            }
        }

        Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(loanApplicationsDTO.getSecurityUserId());

        boolean hasLoanApplicationRejected = false;
        boolean hasCheckerUser = false;
        // -----------------------------------------------------------------------------------------------------
        ArrayList<RemarkHistoryDTO> list = loanApplicationsDTO.getRemarkList() != null
            ? loanApplicationsDTO.getRemarkList()
            : new ArrayList<>();

        if (securityUserDTO.isPresent()) {
            SecurityUserDTO securityUser = securityUserDTO.get();

            hasCheckerUser = isRoleInSet("ROLE_CHECKER", securityUser.getSecurityRoles()); // securityUser.getSecurityRoles().contains("ROLE_CHECKER");

            if (hasCheckerUser) {
                for (RemarkHistoryDTO remarkHistoryDTO : list) {
                    if (null != remarkHistoryDTO.getFreeField3() && remarkHistoryDTO.getFreeField3().equalsIgnoreCase("Rejected")) {
                        hasLoanApplicationRejected = true;
                    }
                }
            }
        }
        // -------------------------------------------------------------------------------------------------------

        if (hasLoanApplicationRejected) {
            loanApplicationsDTO.setStatus(LoanStatus.REJECTED);
        } else if (
            loanApplicationsDTO.getStatus() == LoanStatus.REJECTED &&
            !(null != loanApplicationsDTO.getHasFilledInfoWithDoc() && loanApplicationsDTO.getHasFilledInfoWithDoc())
        ) {
            loanApplicationsDTO.setStatus(LoanStatus.DRAFT);
        } else if (
            loanApplicationsDTO.getStatus() == LoanStatus.REJECTED &&
            (null != loanApplicationsDTO.getHasFilledInfoWithDoc() && loanApplicationsDTO.getHasFilledInfoWithDoc())
        ) {
            loanApplicationsDTO.setStatus(LoanStatus.REJECTED);
        } else if (
            hasCheckerUser &&
            loanApplicationsDTO.getStatus() != LoanStatus.REJECTED &&
            (null != loanApplicationsDTO.getHasFilledInfoWithDoc() && loanApplicationsDTO.getHasFilledInfoWithDoc())
        ) {
            loanApplicationsDTO.setStatus(LoanStatus.VERIFIED);
        }

        // if (hasMemberRejected) {
        // memberDTO.setStatus(Status.REJECTED);
        // } else if (memberDTO.getStatus() == Status.REJECTED &&
        // !memberDTO.getHasFilledInfoWithDoc()) {
        // memberDTO.setStatus(Status.DRAFT);
        // } else if (hasCheckerUser && memberDTO.getHasFilledInfoWithDoc()) {
        // memberDTO.setStatus(Status.VERIFIED);
        // }

        if (null != loanApplicationsDTO.getHasFilledInfoWithDoc() && loanApplicationsDTO.getHasFilledInfoWithDoc()) {
            verifyDocuments.checkDocument(loanApplicationsDTO);
        }

        log.debug("!!!!!!!!!!!REST request Update loanApplications after verifyDocuments : {}");
        LoanApplicationsDTO result = loanApplicationsService.update(loanApplicationsDTO);

        // -----------------------------------------------------------------------------------------------------
        ArrayList<RemarkHistoryDTO> resultListOfRemark = new ArrayList<RemarkHistoryDTO>();

        for (RemarkHistoryDTO remarkHistoryDTO : list) {
            remarkHistoryDTO.setReferenceId(result.getId());
            remarkHistoryDTO.setLastModified(result.getLastModified());
            remarkHistoryDTO.setLastModifiedBy(result.getLastModifiedBy());
            remarkHistoryDTO.setSecurityUserId(result.getSecurityUserId());
            remarkHistoryDTO.setLoanApplications(result);
            remarkHistoryDTO.setCreatedOn(Instant.now());
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
                    remarkHistoryDTO.setCreatedOn(remarkHistory.getCreatedOn());
                }
            }

            RemarkHistoryDTO submitedRemark = remarkHistoryService.save(remarkHistoryDTO);
            resultListOfRemark.add(submitedRemark);
        }

        result.setRemarkList(resultListOfRemark);

        // -------------------------------------------------------------------------------------------------------

        // ------------------ Create loan account after loan sanction----------
        if (result.getStatus().equals(LoanStatus.SANCTIONED) && result.getSanctionAmt() != null) {
            LoanAccountDTO loanAccount = loanApplicationsService.createLoanAccount(result);
        }

        LoanApplicationsHistoryDTO loanApplicationsHistoryDTO = new LoanApplicationsHistoryDTO();
        Optional<LoanApplicationsDTO> loanApplication = loanApplicationsService.findOne(result.getId());
        // result.getId());
        if (loanApplication.isPresent()) {
            loanApplicationsHistoryDTO.setLoanApplication(loanApplication.get());
            loanApplicationsHistoryDTO.setApplicationNo(loanApplication.get().getApplicationNo());
            loanApplicationsHistoryDTO.setAssignedFrom(loanApplication.get().getAssignedFrom());
            loanApplicationsHistoryDTO.setAssignedTo(loanApplication.get().getAssignedTo());
            loanApplicationsHistoryDTO.setCommityAmt(loanApplication.get().getCommityAmt());
            loanApplicationsHistoryDTO.setCommityRoi(loanApplication.get().getCommityRoi());
            loanApplicationsHistoryDTO.setCostOfInvestment(loanApplication.get().getCostOfInvestment());
            loanApplicationsHistoryDTO.setDemandAmount(loanApplication.get().getDemandAmount());
            loanApplicationsHistoryDTO.setDemandedLandAreaInHector(loanApplication.get().getDemandedLandAreaInHector());
            loanApplicationsHistoryDTO.setDownPaymentAmt(loanApplication.get().getDownPaymentAmt());
            loanApplicationsHistoryDTO.setExtentMorgageValue(loanApplication.get().getExtentMorgageValue());
            // loanApplicationsHistoryDTO.setFreeField3(loanApplication.get().getFreeField3());
            // loanApplicationsHistoryDTO.setFreeField4(loanApplication.get().getFreeField4());
            // loanApplicationsHistoryDTO.setFreeField5(loanApplication.get().getFreeField5());
            // loanApplicationsHistoryDTO.setFreeField6(loanApplication.get().getFreeField6());
            // loanApplicationsHistoryDTO.setFreeField7(loanApplication.get().getFreeField7());
            loanApplicationsHistoryDTO.setIsInsured(loanApplication.get().getIsInsured());
            loanApplicationsHistoryDTO.setLastModified(loanApplication.get().getLastModified());
            loanApplicationsHistoryDTO.setLastModifiedBy(loanApplication.get().getLastModifiedBy());
            loanApplicationsHistoryDTO.setLoanBenefitingArea(loanApplication.get().getLoanBenefitingArea());
            loanApplicationsHistoryDTO.setLoanPurpose(loanApplication.get().getLoanPurpose());
            loanApplicationsHistoryDTO.setLoanSteps(loanApplication.get().getLoanSteps());
            loanApplicationsHistoryDTO.setbranch(loanApplication.get().getBranch());
            loanApplicationsHistoryDTO.setProduct(loanApplication.get().getProduct());
            // loanApplicationsHistoryDTO.setSecurityUserId(loanApplication.get().getSecurityUserId());
            loanApplicationsHistoryDTO.setLtvRatio(loanApplication.get().getLtvRatio());
            loanApplicationsHistoryDTO.setMargin(loanApplication.get().getMargin());
            loanApplicationsHistoryDTO.setMemberId(loanApplication.get().getMemberId());
            loanApplicationsHistoryDTO.setMoratorium(loanApplication.get().getMoratorium());
            loanApplicationsHistoryDTO.setMortgageDate(loanApplication.get().getMortgageDate());
            loanApplicationsHistoryDTO.setMortgageDeedNo(loanApplication.get().getMortgageDeedNo());
            loanApplicationsHistoryDTO.setPenalInterest(loanApplication.get().getPenalInterest());
            loanApplicationsHistoryDTO.setProcessingFee(loanApplication.get().getProcessingFee());
            loanApplicationsHistoryDTO.setRoi(loanApplication.get().getRoi());
            loanApplicationsHistoryDTO.setSanctionAmt(loanApplication.get().getSanctionAmt());
            loanApplicationsHistoryDTO.setSanctionRoi(loanApplication.get().getSanctionRoi());
            loanApplicationsHistoryDTO.setSeasonOfCropLoan(loanApplication.get().getSeasonOfCropLoan());
            loanApplicationsHistoryDTO.setSecurityDocUrl(loanApplication.get().getSecurityDocUrl());
            loanApplicationsHistoryDTO.setSecurityOffered(loanApplication.get().getSecurityOffered());
            loanApplicationsHistoryDTO.setStatus(loanApplication.get().getStatus());
            loanApplicationsHistoryDTO.setYear(loanApplication.get().getYear());
        }

        if (loanApplicationsDTO.getDocumentsDTOList() != null) {
            loanApplicationsService.createloanMemberDocuments(loanApplicationsDTO.getDocumentsDTOList());
        }

        LoanApplicationsHistoryDTO result1 = loanApplicationsHistoryService.save(loanApplicationsHistoryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanApplicationsDTO.getId().toString()))
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
     * {@code PATCH  /loan-applications/:id} : Partial updates given fields of an
     * existing loanApplications, field will ignore if it is null
     *
     * @param id                  the id of the loanApplicationsDTO to save.
     * @param loanApplicationsDTO the loanApplicationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated loanApplicationsDTO, or with status
     *         {@code 400 (Bad Request)} if the loanApplicationsDTO is not valid, or
     *         with status {@code 404 (Not Found)} if the loanApplicationsDTO is not
     *         found, or with status {@code 500 (Internal Server Error)} if the
     *         loanApplicationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-applications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanApplicationsDTO> partialUpdateLoanApplications(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanApplicationsDTO loanApplicationsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanApplications partially : {}, {}", id, loanApplicationsDTO);
        if (loanApplicationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanApplicationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanApplicationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanApplicationsDTO> result = loanApplicationsService.partialUpdate(loanApplicationsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanApplicationsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-applications} : get all the loanApplications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of loanApplications in body.
     */
    @GetMapping("/loan-applications")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_APPL_EDIT','LOAN_ACC_VIEW','LOAN_LIST_VIEW')")
    public ResponseEntity<List<LoanApplicationsDTO>> getAllLoanApplications(
        LoanApplicationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanApplications by criteria: {}", criteria);

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
        Page<LoanApplicationsDTO> page = loanApplicationsQueryService.findByCriteria(criteria, pageable);
        ArrayList<LoanApplicationsDTO> tempList = new ArrayList<LoanApplicationsDTO>();
        for (LoanApplicationsDTO loanApplicationsDTO : page) {
            LoanApplicationMembersCriteria memberCriteria2 = new LoanApplicationMembersCriteria();
            LongFilter memberId = new LongFilter();
            memberId.setEquals(loanApplicationsDTO.getMemberId());
            memberCriteria2.setMemberId(memberId);

            ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
            applicantType.setEquals(ApplicantType.APPLICANT);
            memberCriteria2.setApplicantType(applicantType);

            List<LoanApplicationMembersDTO> loanAppMemberList = loanApplicationMembersQueryService.findByCriteria(memberCriteria2);
            for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
                Optional<MemberDTO> membersDTO = membersService.findOne(dtoObj.getMemberId());
                String applicantName =
                    membersDTO.get().getLastName() + " " + membersDTO.get().getFirstName() + " " + membersDTO.get().getMiddleName();
                loanApplicationsDTO.setApplicantName(applicantName);
            }

            ApplicantEligibility applicantEligibility = loanApplicationsService.loanEligibityBasedOnLoanDetails(loanApplicationsDTO);
            loanApplicationsDTO.setApplicantEligibility(applicantEligibility);

            String[] s = loanApplicationsHistoryRepository.findDistinctStatus(loanApplicationsDTO.getId());
            loanApplicationsDTO.setLoanHistoryStatus(s);
            tempList.add(loanApplicationsDTO);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(tempList);
    }

    //

    @GetMapping("/loan-applications/member/{memberId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_APPL_EDIT','LOAN_ACC_VIEW','LOAN_LIST_VIEW')")
    public ResponseEntity<List<LoanApplicationsDTO>> getAllLoanApplicationsByMember(
        @PathVariable(value = "memberId", required = false) final Long memberId
    ) {
        List<LoanApplicationsDTO> list = loanApplicationsQueryService.findLoansByMemberId(memberId);

        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /loan-applications/count} : count all the loanApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/loan-applications/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_APPL_EDIT','LOAN_ACC_VIEW','LOAN_LIST_VIEW')")
    public ResponseEntity<Count> countLoanApplications(LoanApplicationsCriteria criteria) {
        log.debug("REST request to count LoanApplications by criteria: {}", criteria);

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
        count.count = loanApplicationsQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /loan-applications/aggregate/count} : count all the
     * loanApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/loan-applications/aggregate/count")
    @PreAuthorize(
        "hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_LIST_VIEW','DASHBOARD_MAKER_VIEW','DASHBOARD_PO_VIEW','DASHBOARD_CEO_VIEW')"
    )
    public ResponseEntity<LoanApplicationAggregateCount> aggregateCountLoanApplications(LoanApplicationsCriteria criteria) {
        log.debug("REST request to count LoanApplications by criteria: {}", criteria);

        LoanApplicationAggregateCount count = new LoanApplicationAggregateCount();
        count.totalLoanApplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        LoanStatusFilter status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_LEG_VAL);
        criteria.setStatus(status);
        count.AwaitedForLegalNvalCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.DRAFT);
        criteria.setStatus(status);
        count.daftLoanApplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        List<LoanStatus> statusList = new ArrayList<LoanStatus>();
        statusList.add(LoanStatus.PENDING);
        statusList.add(LoanStatus.AWAITED);
        statusList.add(LoanStatus.AWAITED_COMM_REM);
        statusList.add(LoanStatus.AWAITED_DIR_REM);
        statusList.add(LoanStatus.AWAITED_SAN);
        status.setIn(statusList);
        criteria.setStatus(status);
        count.pendingLoanApplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.CREATED);
        criteria.setStatus(status);
        count.createdLoanapplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.SANCTIONED);
        criteria.setStatus(status);
        count.sancationedLoanApplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.VERIFIED);
        criteria.setStatus(status);
        count.verified = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.REJECTED);
        criteria.setStatus(status);
        count.rejected = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_LEG_VAL);
        criteria.setStatus(status);
        count.awaitedForLegalVal = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_COMM_REM);
        criteria.setStatus(status);
        count.awaitedCommRemark = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.APPLIED);
        criteria.setStatus(status);
        count.AppliedLoanApplicationCount = loanApplicationsQueryService.countByCriteria(criteria);

        status = new LoanStatusFilter();
        status.setEquals(LoanStatus.AWAITED_DIR_REM);
        criteria.setStatus(status);
        count.AwaitedForDirRemCount = loanApplicationsQueryService.countByCriteria(criteria);

        return ResponseEntity.ok().body(count);
    }

    /**
     * {@code GET  /loan-applications/:id} : get the "id" loanApplications.
     *
     * @param id the id of the loanApplicationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the loanApplicationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-applications/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_APPL_EDIT')")
    public ResponseEntity<LoanApplicationsDTO> getLoanApplications(@PathVariable Long id) {
        log.debug("REST request to get LoanApplications : {}", id);
        Optional<LoanApplicationsDTO> loanApplicationsDTO = loanApplicationsService.findOne(id);

        if (loanApplicationsDTO.get().getMemberId() != null) {
            // LoanApplicationMembersCriteria membersCriteria=new
            // LoanApplicationMembersCriteria();
            // LongFilter memberId2=new LongFilter();
            // memberId2.setEquals(loanApplicationDTO.get().getMemberId());
            // membersCriteria.setMemberId(memberId2);
            //
            // ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
            // applicantType.setEquals(ApplicantType.APPLICANT);
            // membersCriteria.setApplicantType(applicantType);
            // List<LoanApplicationMembersDTO> loanAppMemberList =
            // loanApplicationMembersQueryService.findByCriteria(membersCriteria);
            // for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
            Optional<MemberDTO> membersDTO = membersService.findOne(loanApplicationsDTO.get().getMemberId());
            String applicantName =
                membersDTO.get().getLastName() + " " + membersDTO.get().getFirstName() + " " + membersDTO.get().getMiddleName();
            loanApplicationsDTO.get().setApplicantName(applicantName);
        }
        return ResponseUtil.wrapOrNotFound(loanApplicationsDTO);
    }

    /**
     * {@code DELETE  /loan-applications/:id} : delete the "id" loanApplications.
     *
     * @param id the id of the loanApplicationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-applications/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_EDIT')")
    public ResponseEntity<Void> deleteLoanApplications(@PathVariable Long id) {
        log.debug("REST request to delete LoanApplications : {}", id);
        loanApplicationsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/loan-applications/eligibility/check/{id}")
    public ResponseEntity<ApplicantEligibility> checkEligibility(@PathVariable Long id) {
        log.debug("REST request to check LoanApplication Eligible amount : {}", id);
        ApplicantEligibility result = loanApplicationsService.validateLaonEligibility(id);
        // return ResponseUtil.wrapOrNotFound(result);
        return ResponseEntity.ok().body(result);
    }
    // @GetMapping("/loan-applications/member-documents")
    // //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','LOAN_APPL_VIEW','LOAN_APPL_EDIT','LOAN_ACC_VIEW','LOAN_LIST_VIEW')")
    // public ResponseEntity<List<LoanApplicationsDTO>>
    // getAllLoanApplicationsDocuments(
    // LoanApplicationsCriteria criteria,
    // @org.springdoc.api.annotations.ParameterObject Pageable pageable
    // ) {
    // log.debug("REST request to get LoanApplications by criteria: {}", criteria);
    //
    // if (criteria.getMemberId() != null) {
    // LoanApplicationMembersCriteria memberCriteria = new
    // LoanApplicationMembersCriteria();
    // memberCriteria.setMemberId(criteria.getMemberId());
    // LongFilter idFilter = new LongFilter();
    // List<LoanApplicationMembersDTO> loanAppMemberList =
    // loanApplicationMembersQueryService.findByCriteria(memberCriteria);
    // for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
    // List<Long> appIdList = new ArrayList<>();
    // appIdList.add(dtoObj.getLoanApplicationId());
    // idFilter.setIn(appIdList);
    // }
    // criteria.setMemberId(null);
    // criteria.setId(idFilter);
    // }
    // Page<LoanApplicationsDTO> page =
    // loanApplicationsQueryService.findByCriteria(criteria, pageable);
    // HttpHeaders headers =
    // PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
    // page);
    // return ResponseEntity.ok().headers(headers).body(page.getContent());
    // }
}
