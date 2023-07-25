package com.techvg.los.service;

import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.service.criteria.DocumentsCriteria;
import com.techvg.los.service.criteria.LoanMemberDocumentsCriteria;
import com.techvg.los.service.criteria.OrgPrerequisiteDocCriteria;
import com.techvg.los.service.criteria.SecurityUserCriteria;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanMemberDocumentsDTO;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.dto.SecurityRoleDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import com.techvg.los.web.rest.errors.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link LoanApplications}.
 */
@Service
@Transactional
public class VerifyDocuments {

    private static final String ENTITY_NAME = "verifyDocuments";

    private final Logger log = LoggerFactory.getLogger(VerifyDocuments.class);

    @Autowired
    private SecurityUserQueryService securityUserQueryService;

    @Autowired
    private OrgPrerequisiteDocQueryService orgPrerequisiteDocQueryService;

    @Autowired
    private DocumentsQueryService documentsQueryService;

    @Autowired
    private LoanMemberDocumentsQueryService loanMemberDocumentsQueryService;

    public Boolean checkDocument(LoanApplicationsDTO loanApplicationsDTO) {
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
                    if (role.getRoleName().equalsIgnoreCase("ROLE_MAKER")) {
                        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!In role.getRoleName(): == org_maker");

                        LongFilter loanApplicationId = new LongFilter();
                        loanApplicationId.setEquals(loanApplicationsDTO.getId());

                        LoanMemberDocumentsCriteria loanMemberDocCriteria = new LoanMemberDocumentsCriteria();
                        loanMemberDocCriteria.setLoanApplicationsId(loanApplicationId);

                        BooleanFilter isDeleted = new BooleanFilter();
                        isDeleted.setEquals(false);
                        loanMemberDocCriteria.setIsDeleted(isDeleted);

                        List<LoanMemberDocumentsDTO> loanMemberDocList = loanMemberDocumentsQueryService.findByCriteria(
                            loanMemberDocCriteria
                        );

                        List<String> docs = new ArrayList<>();

                        if (loanMemberDocList != null) {
                            for (LoanMemberDocumentsDTO loanMemberDoc : loanMemberDocList) {
                                if (loanMemberDoc != null) {
                                    if (loanMemberDoc.getFreeField1() != null) {
                                        docs.add(loanMemberDoc.getFreeField1());
                                    }
                                }
                            }
                        }
                        StringFilter docType = new StringFilter();
                        docType.setIn(docs);

                        OrgPrerequisiteDocCriteria docCriteria = new OrgPrerequisiteDocCriteria();
                        docCriteria.setDocCatagory(docType);

                        List<OrgPrerequisiteDocDTO> docCatagoryList = orgPrerequisiteDocQueryService.findByCriteria(docCriteria);
                        for (OrgPrerequisiteDocDTO preRequisiteObj : docCatagoryList) {
                            if (preRequisiteObj.getIsmandatory()) {
                                DocumentsCriteria criteria = new DocumentsCriteria();

                                LongFilter idFilter = new LongFilter();
                                idFilter.setEquals(loanApplicationsDTO.getId());

                                StringFilter docSubType = new StringFilter();
                                docSubType.setContains(preRequisiteObj.getDocName());

                                BooleanFilter deleted = new BooleanFilter();
                                deleted.setEquals(false);

                                StringFilter tag = new StringFilter();
                                String tagString = DocumentHelper.LOAN_ONBOARD.getValue();
                                tag.setEquals(tagString);

                                criteria.setTag(tag);
                                criteria.setIsDeleted(deleted);
                                criteria.setDocSubType(docSubType);
                                criteria.setRefrenceId(idFilter);
                                List<DocumentsDTO> docList = documentsQueryService.findByCriteria(criteria);

                                if (docList.isEmpty()) {
                                    throw new NotFoundException(
                                        preRequisiteObj.getDocName() +
                                        " not found for " +
                                        loanApplicationsDTO.getApplicantName() +
                                        "," +
                                        " Please Upload selected documents! " +
                                        preRequisiteObj.getDocName() +
                                        " is mandatory Document"
                                    );
                                }
                            }
                        }
                    }

                    if (role.getRoleName().equalsIgnoreCase("ROLE_CHECKER")) {
                        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!In role.getRoleName(): == ROLE_CHECKER");

                        LongFilter loanApplicationId = new LongFilter();
                        loanApplicationId.setEquals(loanApplicationsDTO.getId());
                        LoanMemberDocumentsCriteria loanMemberDocCriteria = new LoanMemberDocumentsCriteria();
                        loanMemberDocCriteria.setLoanApplicationsId(loanApplicationId);

                        BooleanFilter isDeleted = new BooleanFilter();
                        isDeleted.setEquals(false);
                        loanMemberDocCriteria.setIsDeleted(isDeleted);

                        List<LoanMemberDocumentsDTO> loanMemberDocList = loanMemberDocumentsQueryService.findByCriteria(
                            loanMemberDocCriteria
                        );

                        List<String> docs = new ArrayList<>();

                        if (loanMemberDocList != null) {
                            for (LoanMemberDocumentsDTO loanMemberDoc : loanMemberDocList) {
                                if (loanMemberDoc != null) {
                                    if (loanMemberDoc.getFreeField1() != null) {
                                        docs.add(loanMemberDoc.getFreeField1());
                                    }
                                }
                            }
                        }
                        StringFilter docType = new StringFilter();
                        docType.setIn(docs);

                        OrgPrerequisiteDocCriteria docCriteria = new OrgPrerequisiteDocCriteria();
                        docCriteria.setDocCatagory(docType);
                        List<OrgPrerequisiteDocDTO> docCatagoryList = orgPrerequisiteDocQueryService.findByCriteria(docCriteria);

                        for (OrgPrerequisiteDocDTO preRequisiteObj : docCatagoryList) {
                            log.debug("!!!!!!!!!!!REST request to get OrgPrerequisiteDocDTO in verifyDocuments : {}");

                            if (preRequisiteObj.getIsmandatory()) {
                                log.debug("!!!!!!!!!!!In Is mandatory OrgPrerequisiteDocDTO in verifyDocuments : {}");
                                DocumentsCriteria criteria = new DocumentsCriteria();

                                LongFilter idFilter = new LongFilter();
                                idFilter.setEquals(loanApplicationsDTO.getId());

                                StringFilter docSubType = new StringFilter();
                                docSubType.setContains(preRequisiteObj.getDocName());

                                BooleanFilter deleted = new BooleanFilter();
                                deleted.setEquals(false);

                                StringFilter tag = new StringFilter();
                                String tagString = DocumentHelper.LOAN_ONBOARD.getValue();
                                tag.setEquals(tagString);

                                criteria.setTag(tag);
                                criteria.setDocSubType(docSubType);
                                criteria.setRefrenceId(idFilter);
                                criteria.setIsDeleted(deleted);
                                List<DocumentsDTO> docList = documentsQueryService.findByCriteria(criteria);

                                if (docList.isEmpty()) {
                                    log.debug("!!!!!!!!!!!DocList is empty in verifyDocuments : {}");
                                    throw new NotFoundException(
                                        preRequisiteObj.getDocName() +
                                        " not found for " +
                                        loanApplicationsDTO.getApplicantName() +
                                        "," +
                                        " Please add mandatory documents first! " +
                                        preRequisiteObj.getDocName() +
                                        " is mandatory Document"
                                    );
                                }
                                for (DocumentsDTO doc : docList) {
                                    if (
                                        doc.getIsRejected() == null || !doc.getIsRejected() && !doc.getHasVerified() && !doc.getIsDeleted()
                                    ) {
                                        log.debug("!!!!!!!!!!!Condition matches to give exception DocumentsDTO in verifyDocuments : {}");

                                        throw new BadRequestAlertException(
                                            "Invalid request to verify",
                                            ENTITY_NAME,
                                            "Please verify all the documents before proceed!"
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
    // public LoanApplicationsService(LoanApplicationsRepository
    // loanApplicationsRepository, LoanApplicationsMapper loanApplicationsMapper) {
    // this.loanApplicationsRepository = loanApplicationsRepository;
    // this.loanApplicationsMapper = loanApplicationsMapper;
    // }

}
