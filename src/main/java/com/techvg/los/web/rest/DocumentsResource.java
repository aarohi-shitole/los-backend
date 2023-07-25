package com.techvg.los.web.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.Status;
import com.techvg.los.repository.DocumentsRepository;
import com.techvg.los.service.DocumentsQueryService;
import com.techvg.los.service.DocumentsService;
import com.techvg.los.service.GuarantorQueryService;
import com.techvg.los.service.GuarantorService;
import com.techvg.los.service.LoanApplicationsService;
import com.techvg.los.service.MemberService;
import com.techvg.los.service.ProductQueryService;
import com.techvg.los.service.ProductService;
import com.techvg.los.service.RemarkHistoryQueryService;
import com.techvg.los.service.criteria.DocumentsCriteria;
import com.techvg.los.service.criteria.GuarantorCriteria;
import com.techvg.los.service.criteria.ProductCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria.DocumentHelperFilter;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.GuarantorDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.Documents}.
 */
@RestController
@RequestMapping("/api")
public class DocumentsResource {

    private final Logger log = LoggerFactory.getLogger(DocumentsResource.class);

    private static final String ENTITY_NAME = "documents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentsService documentsService;

    private final DocumentsRepository documentsRepository;

    private final DocumentsQueryService documentsQueryService;

    @Autowired
    ProductQueryService productQueryService;

    @Autowired
    GuarantorQueryService guarantorQueryService;

    @Autowired
    GuarantorService guarantorService;

    @Autowired
    ProductService productService;

    @Autowired
    private LoanApplicationsService loanApplicationsService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RemarkHistoryQueryService remarkHistoryQueryService;

    public DocumentsResource(
        DocumentsService documentsService,
        DocumentsRepository documentsRepository,
        DocumentsQueryService documentsQueryService
    ) {
        this.documentsService = documentsService;
        this.documentsRepository = documentsRepository;
        this.documentsQueryService = documentsQueryService;
    }

    /**
     * {@code POST  /documents} : Create a new documents.
     *
     * @param documentsDTO the documentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new documentsDTO, or with status {@code 400 (Bad Request)}
     *         if the documents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/uploadImage")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','DOC_EDIT','DOC_VIEW','LOAN_APPL_EDIT','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<DocumentsDTO> uploadImages(
        @RequestParam("file") MultipartFile file,
        @RequestParam("fileMetadata") String fileMetadata
    ) throws URISyntaxException {
        log.debug("fileMetadata : {}", fileMetadata);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        log.debug("incoming #############################################################", fileMetadata);

        DocumentsDTO documentsDTO = new DocumentsDTO();

        try {
            documentsDTO = gson.fromJson(fileMetadata, DocumentsDTO.class);
        } catch (Exception e) {
            // TODO: handle exception
            try {
                JSONObject json = new JSONObject(fileMetadata);
                documentsDTO.setDocType(json.getString("docType"));
                documentsDTO.setDocSubType(json.getString("docSubType"));
                documentsDTO.setRefrenceId(json.getLong("refrenceId"));
                documentsDTO.setTag(json.getString("tag"));
                documentsDTO.setHasVerified(json.getBoolean("hasVerified"));
                documentsDTO.setIsRejected(json.getBoolean("isRejected"));
                documentsDTO.setSecurityUserId(json.getLong("securityUserId"));

                JSONObject remarkJson = json.getJSONObject("remarkHistoryDTO");

                RemarkHistoryDTO remarkHistoryDTO = new RemarkHistoryDTO();

                remarkHistoryDTO.setTag(DocumentHelper.valueOf(remarkJson.getString("tag")));

                remarkHistoryDTO.setRemarkType(remarkJson.getString("remarkType"));

                remarkHistoryDTO.setRemarkSubType(remarkJson.getString("remarkSubType"));

                documentsDTO.setRemarkHistoryDTO(remarkHistoryDTO); // (json.getLong("securityUserId"));
            } catch (Exception e2) {
                log.debug("While parsing fileData:: ", e2);
            }
        }

        log.debug("REST request to save Documents : {}", documentsDTO);

        if (documentsDTO.getIsDeleted() == null) {
            documentsDTO.setIsDeleted(false);
        }

        Calendar calendar = Calendar.getInstance();
        long timeMilli2 = calendar.getTimeInMillis();

        String docName = null;
        if (documentsDTO.getRefrenceId() != null) {
            docName =
                documentsDTO.getDocType() +
                "_" +
                documentsDTO.getDocSubType() +
                "_" +
                documentsDTO.getRefrenceId() +
                "_" +
                timeMilli2 +
                "." +
                documentsDTO.getFileType();
        } else {
            docName = documentsDTO.getDocType() + "_" + documentsDTO.getDocSubType() + "_" + timeMilli2 + "." + documentsDTO.getFileType();
        }
        log.debug("docName", documentsDTO);

        String dc = Base64.getEncoder().encodeToString(docName.getBytes());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/uploads/files/").path(dc).toUriString();

        documentsDTO.setFileUrl(fileDownloadUri);
        documentsDTO.setFileName(docName);

        documentsDTO.setFreeField2(dc);

        DocumentsDTO result = documentsService.save(file, documentsDTO);

        // ----------------------------------------------------------------------------------------------------------------------
        // Added additional check for update the status for member on boarding in case
        // KYC document was rejected by checker or higher Authority
        if (
            null != documentsDTO.getIsRejected() &&
            null != documentsDTO.getTag() &&
            documentsDTO.getIsRejected() &&
            documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())
        ) {
            Optional<MemberDTO> member = memberService.findOne(documentsDTO.getRefrenceId());
            if (member.isPresent()) {
                MemberDTO memberDTO = member.get();
                memberDTO.setStatus(Status.REJECTED);
                memberService.save(memberDTO);
            }
        }
        // -----------------------------------------------------------------------------------------------------

        // ----------------------------------------------------------------------------------------------------------------------
        // Added additional check for update the status for loan on boarding in case
        // KYC and Loan document was rejected by checker or higher Authority
        if (
            null != documentsDTO.getIsRejected() &&
            null != documentsDTO.getTag() &&
            documentsDTO.getIsRejected() &&
            documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.LOAN_ONBOARD.getValue())
        ) {
            Optional<LoanApplicationsDTO> loan = loanApplicationsService.findOne(documentsDTO.getRefrenceId());
            if (loan.isPresent()) {
                LoanApplicationsDTO loanApplicationsDTO = loan.get();
                loanApplicationsDTO.setStatus(LoanStatus.REJECTED);
                loanApplicationsService.save(loanApplicationsDTO);
            }
        }
        // -----------------------------------------------------------------------------------------------------

        if (documentsDTO.getDocSubType().equalsIgnoreCase("Product")) {
            ProductCriteria productCriteria = new ProductCriteria();
            LongFilter refId = new LongFilter();
            refId.setEquals(documentsDTO.getRefrenceId());
            productCriteria.setId(refId);
            List<ProductDTO> productList = productQueryService.findByCriteria(productCriteria);
            if (productList.isEmpty()) {
                throw new BadRequestAlertException("Product should not be empty", ENTITY_NAME, "productNotExists");
            } else {
                productList.get(0).setProdIconUrl(documentsDTO.getFileUrl());
                productList.get(0).setProdColour(documentsDTO.getFreeField3());
                productService.save(productList.get(0));
            }
        }
        // ---------------------------------------------------------------------------------------------------------

        if (documentsDTO.getTag().equalsIgnoreCase("GUARANTOR_ONBOARD")) {
            GuarantorCriteria guarantorCriteria = new GuarantorCriteria();
            LongFilter refId = new LongFilter();
            refId.setEquals(documentsDTO.getRefrenceId());
            guarantorCriteria.setId(refId);
            List<GuarantorDTO> guarantorList = guarantorQueryService.findByCriteria(guarantorCriteria);
            if (guarantorList.isEmpty()) {
                throw new BadRequestAlertException("Guarantor should not be empty", ENTITY_NAME, "guarantorNotExists");
            } else {
                guarantorList.get(0).setProfileUrl(documentsDTO.getFileUrl());
                guarantorService.save(guarantorList.get(0));
            }
        }

        return ResponseEntity
            .created(new URI("/api/documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/uploadDoc")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','DOC_EDIT','LOAN_APPL_EDIT','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<DocumentsDTO> uploadDocuments(
        @RequestParam("file") MultipartFile file,
        @RequestParam("fileMetadata") String fileMetadata
    ) throws URISyntaxException {
        log.debug("fileMetadata : {}", fileMetadata);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        DocumentsDTO documentsDTO = gson.fromJson(fileMetadata, DocumentsDTO.class);

        log.debug("REST request to save Documents : {}", documentsDTO);
        if (documentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new documents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (documentsDTO.getIsDeleted() == null) {
            documentsDTO.setIsDeleted(false);
        }
        Calendar calendar = Calendar.getInstance();
        long timeMilli2 = calendar.getTimeInMillis();
        String docName = null;

        if (documentsDTO.getRefrenceId() != null) {
            docName =
                documentsDTO.getDocType() +
                "_" +
                documentsDTO.getDocSubType() +
                "_" +
                documentsDTO.getRefrenceId() +
                "_" +
                timeMilli2 +
                "." +
                documentsDTO.getFileType();
        } else {
            docName =
                documentsDTO.getDocType() +
                "_" +
                documentsDTO.getDocSubType() +
                "_" +
                documentsDTO.getMember() +
                "_" +
                timeMilli2 +
                "." +
                documentsDTO.getFileType();
        }

        log.debug("docName", documentsDTO);

        String dc = Base64.getEncoder().encodeToString(docName.getBytes());

        String url = dc + "." + documentsDTO.getFileType();

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/uploads/files/").path(url).toUriString();

        documentsDTO.setFileUrl(fileDownloadUri);
        documentsDTO.setFileName(docName);

        documentsDTO.setFreeField2(url);

        DocumentsDTO result = documentsService.save(file, documentsDTO);

        return ResponseEntity
            .created(new URI("/api/documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documents/:id} : Updates an existing documents.
     *
     * @param id           the id of the documentsDTO to save.
     * @param documentsDTO the documentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated documentsDTO, or with status {@code 400 (Bad Request)} if
     *         the documentsDTO is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the documentsDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documents/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','DOC_VERIFY','DOC_EDIT','LOAN_APPL_EDIT','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<DocumentsDTO> updateDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentsDTO documentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Documents : {}, {}", id, documentsDTO);
        if (documentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentsDTO result = documentsService.update(documentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /documents/:id} : Partial updates given fields of an existing
     * documents, field will ignore if it is null
     *
     * @param id           the id of the documentsDTO to save.
     * @param documentsDTO the documentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated documentsDTO, or with status {@code 400 (Bad Request)} if
     *         the documentsDTO is not valid, or with status {@code 404 (Not Found)}
     *         if the documentsDTO is not found, or with status
     *         {@code 500 (Internal Server Error)} if the documentsDTO couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentsDTO> partialUpdateDocuments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DocumentsDTO documentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Documents partially : {}, {}", id, documentsDTO);
        if (documentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentsDTO> result = documentsService.partialUpdate(documentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /documents} : get all the documents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of documents in body.
     */
    @GetMapping("/documents")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','DOC_EDIT','DOC_VERIFY','LOAN_APPL_VIEW','EDIT_LOAN_PROGRESS')")
    public ResponseEntity<List<DocumentsDTO>> getAllDocuments(
        DocumentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        BooleanFilter booleanFilter = new BooleanFilter();
        booleanFilter.setEquals(Boolean.FALSE);
        criteria.setIsDeleted(booleanFilter);

        log.debug("REST request to get Documents by criteria: {}", criteria);
        Page<DocumentsDTO> page = documentsQueryService.findByCriteria(criteria, pageable);

        for (DocumentsDTO documentObj : page.getContent()) {
            RemarkHistoryCriteria remarkCriteria = new RemarkHistoryCriteria();

            LongFilter idFilter = new LongFilter();

            if (documentObj.getTag().equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())) {
                idFilter.setEquals(documentObj.getMember().getId());
            } else {
                idFilter.setEquals(documentObj.getRefrenceId());
            }

            remarkCriteria.setReferenceId(idFilter);

            StringFilter subType = new StringFilter();
            subType.setContains(documentObj.getDocSubType());
            remarkCriteria.setRemarkSubType(subType);

            StringFilter type = new StringFilter();
            type.setContains("REJECTED");
            remarkCriteria.setRemarkType(type);

            DocumentHelperFilter tag = new DocumentHelperFilter();
            // TODO need to make dyanamic for otherdcuments as well
            String tagObj = documentObj.getTag();
            if (tagObj.equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())) {
                tag.setEquals(DocumentHelper.MEMBER_ONBOARD);
            } else {
                tag.setEquals(DocumentHelper.LOAN_ONBOARD);
            }
            remarkCriteria.setTag(tag);

            List<RemarkHistoryDTO> remarkList = remarkHistoryQueryService.findByCriteria(remarkCriteria);

            if (!remarkList.isEmpty()) {
                documentObj.setRemarkHistoryDTO(remarkList.get(0));
            }
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documents/count} : count all the documents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/documents/count")
    public ResponseEntity<Long> countDocuments(DocumentsCriteria criteria) {
        log.debug("REST request to count Documents by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /documents/:id} : get the "id" documents.
     *
     * @param id the id of the documentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the documentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentsDTO> getDocuments(@PathVariable Long id) {
        log.debug("REST request to get Documents : {}", id);
        Optional<DocumentsDTO> documentsDTO = documentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentsDTO);
    }

    /**
     * {@code DELETE  /documents/:id} : delete the "id" documents.
     *
     * @param id the id of the documentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocuments(@PathVariable Long id) {
        log.debug("REST request to delete Documents : {}", id);
        documentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/documents/list")
    public ResponseEntity<ArrayList<DocumentsDTO>> updateDocumentsList(@RequestBody ArrayList<DocumentsDTO> documentsDTOList)
        throws URISyntaxException {
        ArrayList<DocumentsDTO> resultList = new ArrayList<>();

        for (DocumentsDTO documentsDTO : documentsDTOList) {
            if (documentsDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            if (!Objects.equals(documentsDTO.getId(), documentsDTO.getId())) {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            }

            if (!documentsRepository.existsById(documentsDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            }

            DocumentsDTO result = documentsService.update(documentsDTO);

            // ----------------------------------------------------------------------------
            if (
                null != documentsDTO.getIsRejected() &&
                null != documentsDTO.getTag() &&
                documentsDTO.getIsRejected() &&
                documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())
            ) {
                Optional<MemberDTO> member = memberService.findOne(documentsDTO.getRefrenceId());
                if (member.isPresent()) {
                    MemberDTO memberDTO = member.get();
                    memberDTO.setStatus(Status.REJECTED);
                    memberService.save(memberDTO);
                }
            }
            // ------------------------------------------------------------------------------------

            if (
                null != documentsDTO.getIsRejected() &&
                null != documentsDTO.getTag() &&
                documentsDTO.getIsRejected() &&
                documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.LOAN_ONBOARD.getValue())
            ) {
                Optional<LoanApplicationsDTO> loan = loanApplicationsService.findOne(documentsDTO.getRefrenceId());
                if (loan.isPresent()) {
                    LoanApplicationsDTO loanApplicationsDTO = loan.get();
                    loanApplicationsDTO.setStatus(LoanStatus.REJECTED);
                    loanApplicationsService.save(loanApplicationsDTO);
                }
            }

            resultList.add(result);
        }

        return ResponseEntity.ok().body(resultList);
    }
}
