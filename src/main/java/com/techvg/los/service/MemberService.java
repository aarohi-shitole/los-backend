package com.techvg.los.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.repository.MemberRepository;
import com.techvg.los.service.criteria.DocumentsCriteria;
import com.techvg.los.service.criteria.OrgPrerequisiteDocCriteria;
import com.techvg.los.service.criteria.SecurityUserCriteria;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.dto.SecurityRoleDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import com.techvg.los.service.mapper.MemberMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import com.techvg.los.web.rest.errors.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Member}.
 */
@Service
@Transactional
public class MemberService {

    private static final String ENTITY_NAME = "document";

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    @Autowired
    private DocumentsQueryService documentsQueryService;

    @Autowired
    private OrgPrerequisiteDocQueryService orgPrerequisiteDocQueryService;

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberDTO save(MemberDTO memberDTO) {
        log.debug("Request to save Member : {}", memberDTO);
        // -------create custom customerId for member-------

        if (memberDTO.getCustomerId() == null) {
            Long organisationId = memberDTO.getOrganisation().getId();
            // Optional<OrganisationDTO> organisationObj =
            // organisationService.findOne(organisationId);
            // OrganisationDTO organisationDTO = organisationObj.get();
            // if (BranchType.ZONAL_OFFICE.equals(organisationDTO.getBranchType())) {
            String customerId = this.createCustomerNo(organisationId);
            memberDTO.setCustomerId(customerId);
            // }
        }
        // -----------------------------------------------------

        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        //
        // if (memberDTO.getMemberId() != null && memberDTO.getLoanApplicationId() !=
        // null) {
        // LoanApplicationMembersDTO LoanAppMembersObj = new
        // LoanApplicationMembersDTO();
        //
        // LoanApplicationMembersCriteria criteria = new
        // LoanApplicationMembersCriteria();
        // LongFilter memberFilter = new LongFilter();
        // memberFilter.setEquals(memberDTO.getMemberId());
        // criteria.setMemberId(memberFilter);
        //
        // LongFilter applicationFilter = new LongFilter();
        // applicationFilter.setEquals(memberDTO.getLoanApplicationId());
        // criteria.setLoanApplicationId(applicationFilter);
        //
        // StringFilter applicantFilter = new StringFilter();
        // applicantFilter.setEquals("Applicant");
        // criteria.setApplicantType(applicantFilter);
        //
        // List<LoanApplicationMembersDTO> loanAppMemberList =
        // loanApplicationMembersQueryService
        // .findByCriteria(criteria);
        // if (!loanAppMemberList.isEmpty()) {
        // LoanAppMembersObj.setMemberId(member.getId());
        // LoanAppMembersObj.setLoanApplicationId(memberDTO.getLoanApplicationId());
        // LoanAppMembersObj.setApplicantType("Co-Applicant");;
        // log.debug("Request to save LoanApplicationMembers : {}", LoanAppMembersObj);
        // LoanApplicationMembersDTO loanAppMembers =
        // loanApplicationMembersService.save(LoanAppMembersObj);
        //
        // log.debug("Saved mapping of loan application and member : {}",
        // loanAppMembers);
        // }
        // }
        // -----------------------------------------------------
        return memberMapper.toDto(member);
    }

    /**
     * Update a member.
     *
     * @param memberDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberDTO update(MemberDTO memberDTO) {
        log.debug("Request to update Member : {}", memberDTO);
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    /**
     * Partially update a member.
     *
     * @param memberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberDTO> partialUpdate(MemberDTO memberDTO) {
        log.debug("Request to partially update Member : {}", memberDTO);

        return memberRepository
            .findById(memberDTO.getId())
            .map(existingMember -> {
                memberMapper.partialUpdate(existingMember, memberDTO);

                return existingMember;
            })
            .map(memberRepository::save)
            .map(memberMapper::toDto);
    }

    /**
     * Get all the members.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return memberRepository.findAll(pageable).map(memberMapper::toDto);
    }

    /**
     * Get one member by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberDTO> findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return memberRepository.findById(id).map(memberMapper::toDto);
    }

    /**
     * Delete the member by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.deleteById(id);
    }

    /**
     * create separate customer number for zonal branch.
     *
     * @return CustomerId string.
     */
    private String createCustomerNo(Long organisationId) {
        String customerIdString = null;

        int nextvalue = memberRepository.findCustomerNextvalue(organisationId);
        memberRepository.updateCustomerNextvalue(nextvalue, nextvalue + 1, organisationId);

        String formtedString = String.format("%03d", nextvalue);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        customerIdString = "LOK" + year + "/" + formtedString;

        return customerIdString;
    }

    public Boolean checkDocument(MemberDTO memberDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        StringFilter uName = new StringFilter();
        uName.setEquals(userName);

        SecurityUserCriteria securityUserCriteria = new SecurityUserCriteria();
        securityUserCriteria.setUsername(uName);

        List<SecurityUserDTO> securityUserList = securityUserQueryService.findByCriteria(securityUserCriteria);
        if (!securityUserList.isEmpty()) {
            for (SecurityUserDTO securityUser : securityUserList) {
                Set<SecurityRoleDTO> roleList = securityUser.getSecurityRoles();

                for (SecurityRoleDTO role : roleList) {
                    if (role.getRoleName() != null && role.getRoleName().equalsIgnoreCase("ROLE_MAKER")) {
                        OrgPrerequisiteDocCriteria docCriteria = new OrgPrerequisiteDocCriteria();
                        StringFilter docType = new StringFilter();
                        List<String> docs = new ArrayList<>();
                        docs.add("IDENTIFICATION");
                        docs.add("PERSONAL");
                        docs.add("ADDRESS PROOF");
                        docType.setIn(docs);
                        docCriteria.setDocCatagory(docType);
                        List<OrgPrerequisiteDocDTO> docCatagoryList = orgPrerequisiteDocQueryService.findByCriteria(docCriteria);
                        for (OrgPrerequisiteDocDTO preRequisiteObj : docCatagoryList) {
                            if (preRequisiteObj.getIsmandatory() != null && preRequisiteObj.getIsmandatory()) {
                                DocumentsCriteria criteria = new DocumentsCriteria();

                                LongFilter idFilter = new LongFilter();
                                idFilter.setEquals(memberDTO.getId());

                                StringFilter docSubType = new StringFilter();
                                docSubType.setContains(preRequisiteObj.getDocName());

                                BooleanFilter deleted = new BooleanFilter();
                                deleted.setEquals(false);

                                criteria.setIsDeleted(deleted);
                                criteria.setDocSubType(docSubType);
                                criteria.setRefrenceId(idFilter);
                                List<DocumentsDTO> docList = documentsQueryService.findByCriteria(criteria);

                                if (docList != null && docList.isEmpty()) {
                                    throw new NotFoundException(
                                        preRequisiteObj.getDocName() +
                                        " not found for " +
                                        memberDTO.getFirstName() +
                                        "," +
                                        " Please Upload! " +
                                        preRequisiteObj.getDocName() +
                                        " is mandatory Document"
                                    );
                                }
                            }
                        }
                    }

                    if (role.getRoleName() != null && role.getRoleName().equalsIgnoreCase("ROLE_CHECKER")) {
                        OrgPrerequisiteDocCriteria docCriteria = new OrgPrerequisiteDocCriteria();
                        StringFilter docType = new StringFilter();
                        List<String> docs = new ArrayList<>();
                        docs.add("IDENTIFICATION");
                        docs.add("PERSONAL");
                        docs.add("ADDRESS PROOF");
                        docType.setIn(docs);
                        docCriteria.setDocCatagory(docType);
                        List<OrgPrerequisiteDocDTO> docCatagoryList = orgPrerequisiteDocQueryService.findByCriteria(docCriteria);
                        for (OrgPrerequisiteDocDTO preRequisiteObj : docCatagoryList) {
                            if (preRequisiteObj.getIsmandatory() != null && preRequisiteObj.getIsmandatory()) {
                                DocumentsCriteria criteria = new DocumentsCriteria();

                                LongFilter idFilter = new LongFilter();
                                idFilter.setEquals(memberDTO.getId());

                                StringFilter docSubType = new StringFilter();
                                docSubType.setContains(preRequisiteObj.getDocName());
                                //criteria.setDocSubType(docSubType);

                                StringFilter tag = new StringFilter();
                                String tagString = DocumentHelper.MEMBER_ONBOARD.getValue();
                                tag.setEquals(tagString);

                                BooleanFilter deleted = new BooleanFilter();
                                deleted.setEquals(false);

                                criteria.setIsDeleted(deleted);
                                criteria.setDocSubType(docSubType);
                                criteria.setRefrenceId(idFilter);
                                criteria.setTag(tag);
                                List<DocumentsDTO> docList = documentsQueryService.findByCriteria(criteria);

                                if (docList != null && docList.isEmpty()) {
                                    throw new NotFoundException(
                                        preRequisiteObj.getDocName() +
                                        " not found for " +
                                        memberDTO.getFirstName() +
                                        "," +
                                        " Please add mandatory Documents first! " +
                                        preRequisiteObj.getDocName() +
                                        " is mandatory Document"
                                    );
                                }
                                for (DocumentsDTO doc : docList) {
                                    if (!doc.getIsRejected() && !doc.getHasVerified() && !doc.getIsDeleted()) {
                                        throw new BadRequestAlertException(
                                            "Invalid request to verify",
                                            ENTITY_NAME,
                                            "Please verify all the documents before proceed"
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
