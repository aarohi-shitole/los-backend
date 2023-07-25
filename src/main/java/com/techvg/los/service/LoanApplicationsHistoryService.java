package com.techvg.los.service;

import com.techvg.los.domain.LoanApplicationsHistory;
import com.techvg.los.domain.enumeration.ApplicantType;
import com.techvg.los.domain.enumeration.BranchType;
import com.techvg.los.repository.LoanApplicationsHistoryRepository;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria.ApplicantTypeFilter;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.mapper.LoanApplicationsHistoryMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Service Implementation for managing {@link LoanApplicationsHistory}.
 */
@Service
@Transactional
public class LoanApplicationsHistoryService {

    private static final String ENTITY_NAME = "loanApplicationsHistory";

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsHistoryService.class);

    private final LoanApplicationsHistoryRepository loanApplicationsHistoryRepository;

    private final LoanApplicationsHistoryMapper loanApplicationsHistoryMapper;

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

    public LoanApplicationsHistoryService(
        LoanApplicationsHistoryRepository loanApplicationsHistoryRepository,
        LoanApplicationsHistoryMapper loanApplicationsHistoryMapper
    ) {
        this.loanApplicationsHistoryRepository = loanApplicationsHistoryRepository;
        this.loanApplicationsHistoryMapper = loanApplicationsHistoryMapper;
    }

    /**
     * Save a loanApplicationsHistory.
     *
     * @param loanApplicationsHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanApplicationsHistoryDTO save(LoanApplicationsHistoryDTO loanApplicationsHistoryDTO) {
        log.debug("Request to save LoanApplicationsHistory : {}", loanApplicationsHistoryDTO);
        // -------create custom loan application number for loan-------
        LoanApplicationsHistory loanApplicationsHistory = null;

        //        if (loanApplicationsHistoryDTO.getApplicationNo() == null) {
        //            Long branchId = loanApplicationsHistoryDTO.getBranch().getId();
        //
        //            Optional<BranchDTO> branchObj = branchService.findOne(branchId);
        //
        //            loanApplicationsHistory = loanApplicationsHistoryMapper.toEntity(loanApplicationsHistoryDTO);
        //            loanApplicationsHistory = loanApplicationsHistoryRepository.save(loanApplicationsHistory);
        //        }
        //        //-----------Update member id in loanApplicationMembers mapping for applicant ----
        //        if (loanApplicationsHistoryDTO.getMemberId() != null) {
        //            LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
        //            LongFilter loanApplicationId = new LongFilter();
        //            loanApplicationId.setEquals(loanApplicationsHistory.getId());
        //
        //            LongFilter memberId = new LongFilter();
        //            memberId.setEquals(loanApplicationsHistoryDTO.getMemberId());
        //
        //            ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
        //            applicantType.setEquals(ApplicantType.APPLICANT);
        //
        //            loanApplicationMembersCriteria.setLoanApplicationId(loanApplicationId);
        //            loanApplicationMembersCriteria.setMemberId(memberId);
        //            loanApplicationMembersCriteria.setApplicantType(applicantType);
        //
        //            List<LoanApplicationMembersDTO> loanApplicationMembers = loanApplicationMembersQueryService.findByCriteria(
        //                loanApplicationMembersCriteria
        //            );
        //
        //            if (loanApplicationMembers.isEmpty()) {
        //                LoanApplicationMembersDTO LoanAppMembersObj = new LoanApplicationMembersDTO();
        //                LoanAppMembersObj.setMemberId(loanApplicationsHistoryDTO.getMemberId());
        //                LoanAppMembersObj.setLoanApplicationId(loanApplicationsHistory.getId());
        //                LoanAppMembersObj.setApplicantType(ApplicantType.APPLICANT);
        //                log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj);
        //                LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj);
        //
        //                log.debug("Saved mapping of loan application and member applicant : {}", loanAppMembers);
        //            }
        //        }
        //
        //        //-----------Update member id in loanApplicationMembers mapping for co-applicant ----
        //        if (loanApplicationsHistoryDTO.getCoApplicantList() != null) {
        //            List<MemberDTO> memberlist = loanApplicationsHistoryDTO.getCoApplicantList();
        //
        //            for (MemberDTO member : memberlist) {
        //                LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
        //                LongFilter loanApplicationId = new LongFilter();
        //                loanApplicationId.setEquals(loanApplicationsHistory.getId());
        //
        //                LongFilter memberId = new LongFilter();
        //                memberId.setEquals(member.getId());
        //
        //                ApplicantTypeFilter applicantType = new ApplicantTypeFilter();
        //                applicantType.setEquals(ApplicantType.CO_APPLICANT);
        //
        //                loanApplicationMembersCriteria.setLoanApplicationId(loanApplicationId);
        //                loanApplicationMembersCriteria.setMemberId(memberId);
        //                loanApplicationMembersCriteria.setApplicantType(applicantType);
        //
        //                List<LoanApplicationMembersDTO> loanApplicationMembers = loanApplicationMembersQueryService.findByCriteria(
        //                    loanApplicationMembersCriteria
        //                );
        //
        //                if (loanApplicationMembers.isEmpty()) {
        //                    LoanApplicationMembersDTO LoanAppMembersObj2 = new LoanApplicationMembersDTO();
        //                    LoanAppMembersObj2.setMemberId(member.getId());
        //                    LoanAppMembersObj2.setApplicantType(ApplicantType.CO_APPLICANT);
        //                    LoanAppMembersObj2.setLoanApplicationId(loanApplicationsHistory.getId());
        //                    log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj2);
        //                    LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj2);
        //
        //                    log.debug("Saved mapping of loan application and member co applicant : {}", loanAppMembers);
        //                }
        //            }
        //        }

        loanApplicationsHistory = loanApplicationsHistoryMapper.toEntity(loanApplicationsHistoryDTO);
        loanApplicationsHistory = loanApplicationsHistoryRepository.save(loanApplicationsHistory);
        return loanApplicationsHistoryMapper.toDto(loanApplicationsHistory);
    }

    /**
     * Update a loanApplicationsHistory.
     *
     * @param loanApplicationsHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanApplicationsHistoryDTO update(LoanApplicationsHistoryDTO loanApplicationsHistoryDTO) {
        log.debug("Request to update LoanApplicationsHistory : {}", loanApplicationsHistoryDTO);

        LoanApplicationsHistory loanApplicationsHistory = null;

        loanApplicationsHistory = loanApplicationsHistoryMapper.toEntity(loanApplicationsHistoryDTO);
        loanApplicationsHistory = loanApplicationsHistoryRepository.save(loanApplicationsHistory);

        //-----------Update member id in loanApplicationMembers mapping for co-applicant ----------
        if (loanApplicationsHistoryDTO.getCoApplicantList() != null) {
            List<MemberDTO> memberlist = loanApplicationsHistoryDTO.getCoApplicantList();
            //---------------Delete existing co-applicant ---------------------
            LoanApplicationMembersCriteria loanApplicationMembersCriteria = new LoanApplicationMembersCriteria();
            LongFilter loanApplicationId = new LongFilter();
            loanApplicationId.setEquals(loanApplicationsHistory.getId());

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
            //----------------------------------------------------------------------

            //------------------Insert co-applicants DTO while update
            for (MemberDTO member : memberlist) {
                LoanApplicationMembersDTO LoanAppMembersObj2 = new LoanApplicationMembersDTO();
                LoanAppMembersObj2.setMemberId(member.getId());
                LoanAppMembersObj2.setApplicantType(ApplicantType.CO_APPLICANT);
                LoanAppMembersObj2.setLoanApplicationId(loanApplicationsHistory.getId());
                log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj2);
                LoanApplicationMembersDTO loanAppMembers = loanApplicationMembersService.save(LoanAppMembersObj2);

                log.debug("Saved mapping of loan application and member co applicant : {}", loanAppMembers);
            }
        }
        // ----------------------------------------------------------------
        return loanApplicationsHistoryMapper.toDto(loanApplicationsHistory);
    }

    /**
     * Partially update a loanApplicationsHistory.
     *
     * @param loanApplicationsHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanApplicationsHistoryDTO> partialUpdate(LoanApplicationsHistoryDTO loanApplicationsHistoryDTO) {
        log.debug("Request to partially update LoanApplicationsHistory : {}", loanApplicationsHistoryDTO);

        return loanApplicationsHistoryRepository
            .findById(loanApplicationsHistoryDTO.getId())
            .map(existingLoanApplicationsHistory -> {
                loanApplicationsHistoryMapper.partialUpdate(existingLoanApplicationsHistory, loanApplicationsHistoryDTO);

                return existingLoanApplicationsHistory;
            })
            .map(loanApplicationsHistoryRepository::save)
            .map(loanApplicationsHistoryMapper::toDto);
    }

    /**
     * Get all the loanApplicationsHistory.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanApplicationsHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanApplicationsHistory");
        return loanApplicationsHistoryRepository.findAll(pageable).map(loanApplicationsHistoryMapper::toDto);
    }

    /**
     * Get one loanApplicationsHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanApplicationsHistoryDTO> findOne(Long id) {
        log.debug("Request to get LoanApplicationsHistory : {}", id);
        Optional<LoanApplicationsHistory> loanApplication = loanApplicationsHistoryRepository.findById(id);

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
        Optional<LoanApplicationsHistoryDTO> loanApplicationDTO = loanApplication.map(loanApplicationsHistoryMapper::toDto);
        loanApplicationDTO.get().setCoApplicantList(coApplicant);

        // ------------------------------------------------------------------
        return loanApplicationDTO;
    }

    /**
     * Delete the loanApplicationsHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanApplicationsHistory : {}", id);
        loanApplicationsHistoryRepository.deleteById(id);
    }
    /**
     * create separate customer number for zonal branch.
     *
     * @return CustomerId string.
     */
    //    private String createApplicationNo(Long branchId, ProductDTO loanProduct) {
    //        String applicatioNoString = null;
    //        String productCode = loanProduct.getProdCode();
    //
    //        int nextvalue = loanApplicationsHistoryRepository.findApplicationNextvalue(branchId);
    //        loanApplicationsHistoryRepository.updateApplicationNextvalue(nextvalue, nextvalue + 1, branchId);
    //
    //        String formtedString = String.format("%05d", nextvalue);
    //
    //        int year = Calendar.getInstance().get(Calendar.YEAR);
    //
    //        applicatioNoString = "LOK" + productCode + year + "-" + formtedString;
    //
    //        return applicatioNoString;
    //    }
}
