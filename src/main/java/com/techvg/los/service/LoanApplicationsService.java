package com.techvg.los.service;

import com.techvg.los.domain.ApplicantEligibility;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.enumeration.ApplicantType;
import com.techvg.los.domain.enumeration.BranchType;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.domain.enumeration.LoanElibilityConstants;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.repository.LoanApplicationsHistoryRepository;
import com.techvg.los.repository.LoanApplicationsRepository;
import com.techvg.los.service.criteria.DocumentsCriteria;
import com.techvg.los.service.criteria.IncomeDetailsCriteria;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria.ApplicantTypeFilter;
import com.techvg.los.service.criteria.LoanMemberDocumentsCriteria;
import com.techvg.los.service.criteria.MemberExistingfacilityCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria.DocumentHelperFilter;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanMemberDocumentsDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.mapper.LoanApplicationsMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link LoanApplications}.
 */
@Service
@Transactional
public class LoanApplicationsService {

    private static final String ENTITY_NAME = "loanApplications";

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsService.class);

    private final LoanApplicationsRepository loanApplicationsRepository;

    @Autowired
    private LoanApplicationsHistoryRepository loanApplicationsHistoryRepository;

    private final LoanApplicationsMapper loanApplicationsMapper;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LoanApplicationMembersService loanApplicationMembersService;

    @Autowired
    private LoanApplicationMembersQueryService loanApplicationMembersQueryService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private IncomeDetailsQueryService incomeDetailsQueryService;

    @Autowired
    private RemarkHistoryQueryService remarkHistoryQueryService;

    @Autowired
    private LoanAccountService loanAccountService;

    @Autowired
    private LoanMemberDocumentsService loanMemberDocumentsService;

    @Autowired
    private LoanMemberDocumentsQueryService loanMemberDocumentsQueryService;

    @Autowired
    private MemberExistingfacilityQueryService memberExistingfacilityQueryService;

    @Autowired
    private DocumentsQueryService documentsQueryService;

    @Autowired
    private DocumentsService documentsService;

    @Autowired
    LoanEligibity loanEligibity;

    public LoanApplicationsService(LoanApplicationsRepository loanApplicationsRepository, LoanApplicationsMapper loanApplicationsMapper) {
        this.loanApplicationsRepository = loanApplicationsRepository;
        this.loanApplicationsMapper = loanApplicationsMapper;
    }

    /**
     * Save a loanApplications.
     *
     * @param loanApplicationsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanApplicationsDTO save(LoanApplicationsDTO loanApplicationsDTO) {
        log.debug("Request to save LoanApplications : {}", loanApplicationsDTO);
        // -------create custom loan application number for loan-------
        LoanApplications loanApplications = null;

        if (loanApplicationsDTO.getApplicationNo() == null) {
            Long branchId = loanApplicationsDTO.getBranch().getId();

            Optional<BranchDTO> branchObj = branchService.findOne(branchId);

            if (!branchObj.isPresent()) {
                throw new BadRequestAlertException("Branch should not be empty", ENTITY_NAME, "branchNotExists");
            } else if (branchObj != null) {
                BranchDTO branchDTO = branchObj.get();
                if (BranchType.ZONAL_OFFICE.equals(branchDTO.getBranchType())) {
                    Optional<ProductDTO> productDTO = productService.findOne(loanApplicationsDTO.getProduct().getId());
                    String applicationNo = this.createApplicationNo(branchId, productDTO.get());
                    loanApplicationsDTO.setApplicationNo(applicationNo);
                }
            }
            loanApplications = loanApplicationsMapper.toEntity(loanApplicationsDTO);
            loanApplications = loanApplicationsRepository.save(loanApplications);
        }
        // -----------Update member id in loanApplicationMembers mapping for applicant
        // ----
        if (loanApplicationsDTO.getMemberId() != null) {
            LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
            LongFilter loanApplicationId = new LongFilter();
            if (loanApplications != null) {
                loanApplicationId.setEquals(loanApplications.getId());
            } else {
                loanApplicationId.setEquals(loanApplicationsDTO.getId());
            }

            LongFilter memberId = new LongFilter();
            memberId.setEquals(loanApplicationsDTO.getMemberId());

            ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
            applicantType.setEquals(ApplicantType.APPLICANT);

            loanApplicationMembersCriteria.setLoanApplicationId(loanApplicationId);
            loanApplicationMembersCriteria.setMemberId(memberId);
            loanApplicationMembersCriteria.setApplicantType(applicantType);

            List<LoanApplicationMembersDTO> loanApplicationMembers = loanApplicationMembersQueryService.findByCriteria(
                loanApplicationMembersCriteria
            );

            if (loanApplicationMembers.isEmpty()) {
                LoanApplicationMembersDTO LoanAppMembersObj = new LoanApplicationMembersDTO();
                LoanAppMembersObj.setMemberId(loanApplicationsDTO.getMemberId());
                LoanAppMembersObj.setLoanApplicationId(loanApplications.getId());
                LoanAppMembersObj.setApplicantType(ApplicantType.APPLICANT);
                log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj);
                LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj);

                log.debug("Saved mapping of loan application and member applicant : {}", loanAppMembers);
            }
        }

        // -----------Update member id in loanApplicationMembers mapping for
        // co-applicant ----
        if (loanApplicationsDTO.getCoApplicantList() != null) {
            List<MemberDTO> memberlist = loanApplicationsDTO.getCoApplicantList();

            for (MemberDTO member : memberlist) {
                LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
                LongFilter loanApplicationId = new LongFilter();
                loanApplicationId.setEquals(loanApplicationsDTO.getId());

                LongFilter memberId = new LongFilter();
                memberId.setEquals(member.getId());

                ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
                applicantType.setEquals(ApplicantType.CO_APPLICANT);

                loanApplicationMembersCriteria.setLoanApplicationId(loanApplicationId);
                loanApplicationMembersCriteria.setMemberId(memberId);
                loanApplicationMembersCriteria.setApplicantType(applicantType);

                List<LoanApplicationMembersDTO> loanApplicationMembers = loanApplicationMembersQueryService.findByCriteria(
                    loanApplicationMembersCriteria
                );

                if (loanApplicationMembers.isEmpty()) {
                    LoanApplicationMembersDTO LoanAppMembersObj2 = new LoanApplicationMembersDTO();
                    LoanAppMembersObj2.setMemberId(member.getId());
                    LoanAppMembersObj2.setApplicantType(ApplicantType.CO_APPLICANT);
                    LoanAppMembersObj2.setLoanApplicationId(loanApplications.getId());
                    log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj2);
                    LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj2);

                    log.debug("Saved mapping of loan application and member co applicant : {}", loanAppMembers);
                }
            }
        }

        // loanApplications = loanApplicationsMapper.toEntity(loanApplicationsDTO);
        // loanApplications = loanApplicationsRepository.save(loanApplications);
        // ----------------------------------------------------------------
        return loanApplicationsMapper.toDto(loanApplications);
    }

    /**
     * Update a loanApplications.
     *
     * @param loanApplicationsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanApplicationsDTO update(LoanApplicationsDTO loanApplicationsDTO) {
        log.debug("Request to update LoanApplications : {}", loanApplicationsDTO);

        LoanApplications loanApplications = null;

        loanApplications = loanApplicationsMapper.toEntity(loanApplicationsDTO);
        loanApplications = loanApplicationsRepository.save(loanApplications);

        // -----------Update member id in loanApplicationMembers mapping for
        // co-applicant ----------
        if (loanApplicationsDTO.getCoApplicantList() != null) {
            List<MemberDTO> memberlist = loanApplicationsDTO.getCoApplicantList();
            // ---------------Delete existing co-applicant ---------------------
            LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
            LongFilter loanApplicationId = new LongFilter();
            loanApplicationId.setEquals(loanApplications.getId());

            ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
            applicantType.setEquals(ApplicantType.CO_APPLICANT);

            loanApplicationMembersCriteria.setLoanApplicationId(loanApplicationId);
            loanApplicationMembersCriteria.setApplicantType(applicantType);

            List<LoanApplicationMembersDTO> loanApplicationMembersList = loanApplicationMembersQueryService.findByCriteria(
                loanApplicationMembersCriteria
            );

            if (!loanApplicationMembersList.isEmpty()) {
                for (LoanApplicationMembersDTO listDTO : loanApplicationMembersList) {
                    loanApplicationMembersService.delete(listDTO.getId());
                }
            }
            // ----------------------------------------------------------------------

            // ------------------Insert co-applicants DTO while update
            for (MemberDTO member : memberlist) {
                LoanApplicationMembersDTO LoanAppMembersObj2 = new LoanApplicationMembersDTO();
                LoanAppMembersObj2.setMemberId(member.getId());
                LoanAppMembersObj2.setApplicantType(ApplicantType.CO_APPLICANT);
                LoanAppMembersObj2.setLoanApplicationId(loanApplications.getId());
                log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj2);
                LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj2);

                log.debug("Saved mapping of loan application and member co applicant : {}", loanAppMembers);
            }
        }
        // ----------------------------------------------------------------
        return loanApplicationsMapper.toDto(loanApplications);
    }

    /**
     * Partially update a loanApplications.
     *
     * @param loanApplicationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanApplicationsDTO> partialUpdate(LoanApplicationsDTO loanApplicationsDTO) {
        log.debug("Request to partially update LoanApplications : {}", loanApplicationsDTO);

        return loanApplicationsRepository
            .findById(loanApplicationsDTO.getId())
            .map(existingLoanApplications -> {
                loanApplicationsMapper.partialUpdate(existingLoanApplications, loanApplicationsDTO);

                return existingLoanApplications;
            })
            .map(loanApplicationsRepository::save)
            .map(loanApplicationsMapper::toDto);
    }

    /**
     * Get all the loanApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanApplicationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanApplications");

        return loanApplicationsRepository.findAll(pageable).map(loanApplicationsMapper::toDto);
    }

    /**
     * Get one loanApplications by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanApplicationsDTO> findOne(Long id) {
        log.debug("Request to get LoanApplications : {}", id);
        Optional<LoanApplications> loanApplication = loanApplicationsRepository.findById(id);

        // -------------------Get co-borowwer of loan application---------------
        LoanApplicationMembersCriteria loanAppCriteria = new LoanApplicationMembersCriteria();

        LongFilter idFilter = new LongFilter();
        idFilter.setEquals(loanApplication.get().getId());
        loanAppCriteria.setLoanApplicationId(idFilter);

        ApplicantTypeFilter applicantfilter = new ApplicantTypeFilter();
        applicantfilter.setEquals(ApplicantType.CO_APPLICANT);
        loanAppCriteria.setApplicantType(applicantfilter);

        log.debug("Request to get LoanApplicationMembers by criteria : {}", loanAppCriteria);

        List<LoanApplicationMembersDTO> loanAppMemberList = loanApplicationMembersQueryService.findByCriteria(loanAppCriteria);
        List<MemberDTO> coApplicant = new ArrayList<>();

        for (LoanApplicationMembersDTO dtoObj : loanAppMemberList) {
            Optional<MemberDTO> memberDTO = memberService.findOne(dtoObj.getMemberId());
            coApplicant.add(memberDTO.get());
        }
        Optional<LoanApplicationsDTO> loanApplicationDTO = loanApplication.map(loanApplicationsMapper::toDto);
        loanApplicationDTO.get().setCoApplicantList(coApplicant);

        // ------------------------------------------------------------------

        // ------------Get document list----------------

        LoanMemberDocumentsCriteria docCriteria = new LoanMemberDocumentsCriteria();
        docCriteria.setLoanApplicationsId(idFilter);
        List<LoanMemberDocumentsDTO> docList = loanMemberDocumentsQueryService.findByCriteria(docCriteria);
        loanApplicationDTO.get().setDocumentsDTOList(docList);
        // ----------------------------------------------------

        // ------------Get loanHistory statuses----------------
        String[] s = loanApplicationsHistoryRepository.findDistinctStatus(loanApplication.get().getId());
        loanApplicationDTO.get().setLoanHistoryStatus(s);

        return loanApplicationDTO;
    }

    /**
     * Delete the loanApplications by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanApplications : {}", id);
        loanApplicationsRepository.deleteById(id);
    }

    /**
     * create separate customer number for zonal branch.
     *
     * @return CustomerId string.
     */
    private String createApplicationNo(Long branchId, ProductDTO loanProduct) {
        String applicatioNoString = null;
        String productCode = loanProduct.getProdCode();
        // int nextvalue =
        // loanApplicationsRepository.findApplicationNextvalue(branchId);
        // loanApplicationsRepository.updateApplicationNextvalue(nextvalue, nextvalue +
        // 1, branchId);

        // String formtedString = String.format("%05d", nextvalue);

        // int year = Calendar.getInstance().get(Calendar.YEAR);
        Calendar c = Calendar.getInstance();
        long timestamp = c.getTimeInMillis();

        // applicatioNoString = productCode + timestamp + "-" + formtedString + "T";

        applicatioNoString = productCode + timestamp + "T";

        return applicatioNoString;
    }

    /**
     * Get eligibility amount for loanApplication applied by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public ApplicantEligibility checkEligibleAmt(Long id) {
        ApplicantEligibility eligibility = new ApplicantEligibility();

        Optional<LoanApplicationsDTO> loanAppObject = this.findOne(id);
        double eligibilityAmt = 0.0;
        double outstandingAmt = 0.0;
        double netIncome = 0.0;

        // --------------------Check eligibility According to income
        // ---------------------------
        IncomeDetailsCriteria criteria = new IncomeDetailsCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(loanAppObject.get().getMemberId());
        criteria.setMemberId(longFilter);
        List<IncomeDetailsDTO> incomeList = incomeDetailsQueryService.findByCriteria(criteria);

        if (!incomeList.isEmpty()) {
            for (IncomeDetailsDTO incomeObj : incomeList) {
                if (incomeObj.getExpenses() != null) {
                    netIncome = incomeObj.getGrossIncome() - incomeObj.getExpenses();
                } else {
                    netIncome = incomeObj.getNetIncome();
                }

                double emiCapacity = netIncome * 60 / 100;
                double maxTenre = loanAppObject.get().getProduct().getMaxTenor();
                eligibilityAmt = emiCapacity * maxTenre;
            }
        } else {
            throw new BadRequestAlertException(
                "IncomeDetails should present for member..Please! Enter required Income details ",
                ENTITY_NAME,
                "IncomeDetails not exist"
            );
        }
        // ---------------------------------------------------------------------------------------

        // --------------------Check eligibility According mortgae valuation
        // ---------------------------
        RemarkHistoryCriteria remarkCriteria = new RemarkHistoryCriteria();
        longFilter = new LongFilter();
        longFilter.setEquals(loanAppObject.get().getId());
        remarkCriteria.setLoanApplicationsId(longFilter);

        DocumentHelperFilter tagFilter = new DocumentHelperFilter();
        tagFilter.setEquals(DocumentHelper.LOAN_VALUATION);
        remarkCriteria.setTag(tagFilter);

        List<RemarkHistoryDTO> remarkobj = remarkHistoryQueryService.findByCriteria(remarkCriteria);

        // ----------------------Member details-----------

        Optional<MemberDTO> memberDTO = memberService.findOne(loanAppObject.get().getMemberId());
        MemberExistingfacilityCriteria facilitiesCriteria = new MemberExistingfacilityCriteria();
        longFilter = new LongFilter();
        longFilter.setEquals(loanAppObject.get().getMemberId());
        facilitiesCriteria.setMemberId(longFilter);
        List<MemberExistingfacilityDTO> facilitiesList = memberExistingfacilityQueryService.findByCriteria(facilitiesCriteria);
        if (!facilitiesList.isEmpty()) {
            for (MemberExistingfacilityDTO facilityObj : facilitiesList) {
                outstandingAmt = facilityObj.getOutstandingInLac();
            }
        }

        // ------------------------------------------
        if (memberDTO.get().getOccupation() != null) {
            // eligibility.setOcupation(memberDTO.get().getOccupation().getValue());
        } else {
            throw new BadRequestAlertException("Occupation should present", ENTITY_NAME, "Occupation is null in EmploymentDTO");
        }

        if (memberDTO.get().getAge() != null) {
            eligibility.setApplicantAge(memberDTO.get().getAge());
            // TODO here changes for average age
        } else {
            throw new BadRequestAlertException("Age should present for member..Please! Enter required Age ", ENTITY_NAME, "Age is null");
        }

        eligibility.setDememdedAmt(loanAppObject.get().getDemandAmount());
        eligibility.setNetIncome(netIncome);
        eligibility.setEligibleAmt(eligibilityAmt);
        eligibility.setOutstanding(outstandingAmt);

        return eligibility;
    }

    /**
     * create separate loanAccount no for loan sanction amount
     *
     * @return CustomerId string.
     */
    private String createLoanNo(Long branchId, ProductDTO loanProduct) {
        String applicatioNoString = null;
        String productCode = loanProduct.getProdCode();
        Calendar c = Calendar.getInstance();
        long timestamp = c.getTimeInMillis();
        applicatioNoString = productCode + timestamp;

        return applicatioNoString;
    }

    /**
     * Save a loanAccount.
     *
     * @param loanApplicationsDTO the entity to convert into loanAccount entity to
     *                            save.
     * @return the loanAccount entity.
     */

    public LoanAccountDTO createLoanAccount(LoanApplicationsDTO loanApplicationsDTO) {
        Long branchId = loanApplicationsDTO.getBranch().getId();
        Optional<ProductDTO> productDTO = productService.findOne(loanApplicationsDTO.getProduct().getId());
        String loanAccountNo = this.createLoanNo(branchId, productDTO.get());

        // ------------Calculate date after 3 month for last disbursement date-------
        Calendar after3MonthDay = Calendar.getInstance();
        after3MonthDay = Calendar.getInstance();
        after3MonthDay.add(Calendar.MONTH, +3);
        after3MonthDay.add(Calendar.DAY_OF_MONTH, -1);
        after3MonthDay.set(Calendar.MILLISECOND, 0);
        after3MonthDay.set(Calendar.SECOND, 0);
        after3MonthDay.set(Calendar.MINUTE, 0);
        after3MonthDay.set(Calendar.HOUR_OF_DAY, 0);
        Date threeMonth = after3MonthDay.getTime();
        // -----------------------------------------------

        LoanAccountDTO loanAccountDTO = new LoanAccountDTO();
        loanAccountDTO.setLoanAccountNo(loanAccountNo);
        loanAccountDTO.setLastModified(loanApplicationsDTO.getLastModified());
        loanAccountDTO.setLastModifiedBy(loanApplicationsDTO.getLastModifiedBy());
        loanAccountDTO.setLoanAmount(loanApplicationsDTO.getSanctionAmt());
        loanAccountDTO.setRoi(loanApplicationsDTO.getSanctionRoi());
        loanAccountDTO.setStatus(LoanStatus.AWAITED_DIS);
        loanAccountDTO.setLoanApplicationsId(loanApplicationsDTO.getId());
        loanAccountDTO.setMemberId(loanApplicationsDTO.getMemberId());
        loanAccountDTO.setProduct(loanApplicationsDTO.getProduct());
        loanAccountDTO.setDisbursementAllowance(true);
        loanAccountDTO.setDisbursementDate(threeMonth.toInstant());

        log.debug("REST request to save LoanAccount : {}", loanAccountDTO);
        LoanAccountDTO result = loanAccountService.save(loanAccountDTO);

        return result;
    }

    public void createloanMemberDocuments(List<LoanMemberDocumentsDTO> loanMemberDocumentsList) {
        if (loanMemberDocumentsList != null && loanMemberDocumentsList.size() > 0) {
            for (LoanMemberDocumentsDTO loanMemberDocument : loanMemberDocumentsList) {
                LoanMemberDocumentsCriteria criteria1 = new LoanMemberDocumentsCriteria();

                StringFilter typeDoc = new StringFilter();
                String docType = loanMemberDocument.getFreeField1();
                typeDoc.setEquals(docType);

                LongFilter memberId1 = new LongFilter();
                long id1 = loanMemberDocument.getMemberId();
                memberId1.setEquals(id1);

                LongFilter loanApplicationsId1 = new LongFilter();
                long loanApplicationsId3 = loanMemberDocument.getLoanApplicationsId();
                loanApplicationsId1.setEquals(loanApplicationsId3);

                criteria1.setFreeField1(typeDoc);
                criteria1.setMemberId(memberId1);
                criteria1.setLoanApplicationsId(loanApplicationsId1);

                List<LoanMemberDocumentsDTO> list2 = loanMemberDocumentsQueryService.findByCriteria(criteria1);

                if (!list2.isEmpty()) {
                    for (LoanMemberDocumentsDTO singleObject : list2) {
                        singleObject.setIsDeleted(loanMemberDocument.getIsDeleted());

                        loanMemberDocumentsService.update(singleObject);
                    }
                } else if (list2.isEmpty()) {
                    loanMemberDocument.setIsDeleted(loanMemberDocument.getIsDeleted());
                    loanMemberDocumentsService.save(loanMemberDocument);
                }

                if (loanMemberDocument.getIsDeleted() != null && loanMemberDocument.getIsDeleted()) {
                    this.deleteApplicationDoc(loanMemberDocument);
                }
            }
        }
    }

    public void deleteApplicationDoc(LoanMemberDocumentsDTO loanMemberDocument) {
        DocumentsCriteria criteria = new DocumentsCriteria();

        StringFilter docType = new StringFilter();
        docType.setContains(loanMemberDocument.getFreeField1());
        criteria.setDocType(docType);

        LongFilter loanApplicationsId = new LongFilter();
        loanApplicationsId.setEquals(loanMemberDocument.getLoanApplicationsId());
        criteria.setRefrenceId(loanApplicationsId);

        StringFilter tag = new StringFilter();
        tag.setContains(DocumentHelper.LOAN_ONBOARD.getValue());
        criteria.setDocType(docType);

        List<DocumentsDTO> docList = documentsQueryService.findByCriteria(criteria);

        if (!docList.isEmpty()) {
            for (DocumentsDTO docObj : docList) {
                // -------------Get remark of document
                docObj.getDocSubType();
                RemarkHistoryCriteria remarkCriteria = new RemarkHistoryCriteria();
                DocumentHelperFilter tagFilter = new DocumentHelperFilter();
                tagFilter.setEquals(DocumentHelper.LOAN_ONBOARD);

                docType = new StringFilter();
                docType.setContains(docObj.getDocSubType());

                remarkCriteria.setTag(tagFilter);
                remarkCriteria.setRemarkSubType(docType);
                remarkCriteria.setReferenceId(loanApplicationsId);

                List<RemarkHistoryDTO> remarkList = remarkHistoryQueryService.findByCriteria(remarkCriteria);

                if (!remarkList.isEmpty()) {
                    docObj.setRemarkHistoryDTO(remarkList.get(0));
                }
                docObj.setIsDeleted(true);
                docObj.setSecurityUserId(remarkList.get(0).getSecurityUserId());
                DocumentsDTO result = documentsService.update(docObj);
            }
        }
    }

    public ApplicantEligibility validateLaonEligibility(Long loanApplicationId) {
        ApplicantEligibility applicantEligibility = null;

        Optional<LoanApplicationsDTO> loanAppObject = this.findOne(loanApplicationId);

        LoanApplicationsDTO loanApplicationDTO = loanAppObject.get();

        applicantEligibility = loanEligibityBasedOnLoanDetails(loanApplicationDTO);

        return applicantEligibility;
    }

    public ApplicantEligibility loanEligibityBasedOnLoanDetails(LoanApplicationsDTO loanApplicationDTO) {
        ApplicantEligibility applicantEligibility = null;
        Long loanApplicationId = loanApplicationDTO.getId();

        if (loanApplicationDTO != null) {
            long memberId = loanApplicationDTO.getMemberId();

            Optional<MemberDTO> memberDTO = memberService.findOne(memberId);

            if (memberDTO.get().getAge() != null) {
                double memberAge = memberDTO.get().getAge();
                String memberFullName =
                    memberDTO.get().getLastName() + " " + memberDTO.get().getFirstName() + " " + memberDTO.get().getMiddleName();

                if (LoanElibilityConstants.LOWER_AGE_LIMIT <= memberAge && memberAge <= LoanElibilityConstants.UPPER_AGE_LIMIT) {
                    applicantEligibility = loanEligibity.checkEligibleAmt(loanApplicationId, loanApplicationDTO);

                    applicantEligibility.setApplicantAge(memberAge);
                    applicantEligibility.setApplicantName(memberFullName);
                    applicantEligibility.setEmployeeType(memberDTO.get().getOccupation());
                } else {
                    throw new BadRequestAlertException(
                        "Member age should be above " +
                        LoanElibilityConstants.LOWER_AGE_LIMIT +
                        " and below " +
                        LoanElibilityConstants.UPPER_AGE_LIMIT +
                        ". ",
                        ENTITY_NAME,
                        "Member is not fitted in Age Criteria"
                    );
                }
            } else {
                throw new BadRequestAlertException(
                    "Age should present for member..Please! Enter required Age ",
                    ENTITY_NAME,
                    "Age is null"
                );
            }
        }

        return applicantEligibility;
    }
}
