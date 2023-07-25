package com.techvg.los.web.rest;

import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.repository.GuarantorRepository;
import com.techvg.los.service.DocumentsQueryService;
import com.techvg.los.service.GuarantorQueryService;
import com.techvg.los.service.GuarantorService;
import com.techvg.los.service.criteria.DocumentsCriteria;
import com.techvg.los.service.criteria.GuarantorCriteria;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.GuarantorDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.los.domain.Guarantor}.
 */
@RestController
@RequestMapping("/api")
public class GuarantorResource {

    private final Logger log = LoggerFactory.getLogger(GuarantorResource.class);

    private static final String ENTITY_NAME = "guarantor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuarantorService guarantorService;

    private final GuarantorRepository guarantorRepository;

    private final GuarantorQueryService guarantorQueryService;

    @Autowired
    private DocumentsQueryService documentsQueryService;

    public GuarantorResource(
        GuarantorService guarantorService,
        GuarantorRepository guarantorRepository,
        GuarantorQueryService guarantorQueryService
    ) {
        this.guarantorService = guarantorService;
        this.guarantorRepository = guarantorRepository;
        this.guarantorQueryService = guarantorQueryService;
    }

    /**
     * {@code POST  /guarantors} : Create a new guarantor.
     *
     * @param guarantorDTO the guarantorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guarantorDTO, or with status {@code 400 (Bad Request)} if the guarantor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guarantors")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<GuarantorDTO> createGuarantor(@Valid @RequestBody GuarantorDTO guarantorDTO) throws URISyntaxException {
        log.debug("REST request to save Guarantor : {}", guarantorDTO);
        if (guarantorDTO.getId() != null) {
            throw new BadRequestAlertException("A new guarantor cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // PAN-----------------------------------------------------------------------------------------------------------------

        if (guarantorDTO.getPanCard() != null) {
            GuarantorCriteria criteria = new GuarantorCriteria();
            StringFilter panCard = new StringFilter();
            panCard.setEquals(guarantorDTO.getPanCard());
            criteria.setPanCard(panCard);

            long pancardCount = guarantorQueryService.countByCriteria(criteria);

            if (pancardCount > 0) {
                throw new BadRequestAlertException("Guarantor already exists with same PAN", ENTITY_NAME, "idexists");
            }
        }

        // END--------------------------------------------------------------------------------------------------------------------

        // Aadhar-----------------------------------------------------------------------------------------------------------------
        if (guarantorDTO.getAadharCardNo() != null) {
            GuarantorCriteria criteria = new GuarantorCriteria();
            StringFilter aadharCardNo = new StringFilter();
            aadharCardNo.setEquals(guarantorDTO.getAadharCardNo());
            criteria.setAadharCardNo(aadharCardNo);

            long aadharCount = guarantorQueryService.countByCriteria(criteria);

            if (aadharCount > 0) {
                throw new BadRequestAlertException("Guarantor already exists with same Aadhar", ENTITY_NAME, "idexists");
            }
        }
        // END------------------------------------------------------------------------------------------------------------------

        GuarantorDTO result = guarantorService.save(guarantorDTO);
        return ResponseEntity
            .created(new URI("/api/guarantors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guarantors/:id} : Updates an existing guarantor.
     *
     * @param id the id of the guarantorDTO to save.
     * @param guarantorDTO the guarantorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guarantorDTO,
     * or with status {@code 400 (Bad Request)} if the guarantorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guarantorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guarantors/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<GuarantorDTO> updateGuarantor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GuarantorDTO guarantorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Guarantor : {}, {}", id, guarantorDTO);
        if (guarantorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guarantorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guarantorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuarantorDTO result = guarantorService.update(guarantorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guarantorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /guarantors/:id} : Partial updates given fields of an existing guarantor, field will ignore if it is null
     *
     * @param id the id of the guarantorDTO to save.
     * @param guarantorDTO the guarantorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guarantorDTO,
     * or with status {@code 400 (Bad Request)} if the guarantorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guarantorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guarantorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/guarantors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuarantorDTO> partialUpdateGuarantor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GuarantorDTO guarantorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Guarantor partially : {}, {}", id, guarantorDTO);
        if (guarantorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guarantorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guarantorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuarantorDTO> result = guarantorService.partialUpdate(guarantorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guarantorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /guarantors} : get all the guarantors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guarantors in body.
     */
    @GetMapping("/guarantors")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_VIEW','EDIT_LOAN_PROGRESS','LOAN_APPL_VIEW')")
    public ResponseEntity<List<GuarantorDTO>> getAllGuarantors(
        GuarantorCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Guarantors by criteria: {}", criteria);
        Page<GuarantorDTO> page = guarantorQueryService.findByCriteria(criteria, pageable);

        List<GuarantorDTO> guarantorList = page.getContent();
        // -------------Add document list in Guarantor response-------
        for (GuarantorDTO guarantorObj : guarantorList) {
            DocumentsCriteria docCriteria = new DocumentsCriteria();

            LongFilter idfilter = new LongFilter();
            idfilter.setEquals(guarantorObj.getId());
            docCriteria.setRefrenceId(idfilter);

            StringFilter tagFilter = new StringFilter();
            DocumentHelper tag = (DocumentHelper.GUARANTOR_ONBOARD);
            tagFilter.setContains(tag.toString());
            docCriteria.setTag(tagFilter);

            List<DocumentsDTO> docs = documentsQueryService.findByCriteria(docCriteria);

            if (!docs.isEmpty()) {
                guarantorObj.setDocuments(docs);
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guarantors/count} : count all the guarantors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/guarantors/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_VIEW','EDIT_LOAN_PROGRESS','LOAN_APPL_VIEW')")
    public ResponseEntity<Long> countGuarantors(GuarantorCriteria criteria) {
        log.debug("REST request to count Guarantors by criteria: {}", criteria);
        return ResponseEntity.ok().body(guarantorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /guarantors/:id} : get the "id" guarantor.
     *
     * @param id the id of the guarantorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guarantorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guarantors/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_VIEW','EDIT_LOAN_PROGRESS','LOAN_APPL_EDIT')")
    public ResponseEntity<GuarantorDTO> getGuarantor(@PathVariable Long id) {
        log.debug("REST request to get Guarantor : {}", id);
        Optional<GuarantorDTO> guarantorDTO = guarantorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guarantorDTO);
    }

    /**
     * {@code DELETE  /guarantors/:id} : delete the "id" guarantor.
     *
     * @param id the id of the guarantorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guarantors/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_VIEW')")
    public ResponseEntity<Void> deleteGuarantor(@PathVariable Long id) {
        log.debug("REST request to delete Guarantor : {}", id);
        guarantorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /guarantors/List} : Create a list of new guarantors of member.
     *
     * @param List<guarantorDTO> the guarantorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guarantorDTO array list, or with status {@code 400 (Bad Request)} if the guarantor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guarantors/list")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','GUARANTOR_EDIT','LOAN_APPL_EDIT')")
    public ResponseEntity<List<GuarantorDTO>> createGuarantorList(@RequestBody List<GuarantorDTO> guarantorDTOList)
        throws URISyntaxException {
        log.debug("REST request to save multiple Guarantors : {}", guarantorDTOList);

        List<GuarantorDTO> guarantorArry = new ArrayList<GuarantorDTO>();
        GuarantorDTO guarantor = null;

        for (GuarantorDTO guarantorObj : guarantorDTOList) {
            if (guarantorObj.getId() != null) {
                throw new BadRequestAlertException("A new guarantor cannot already have an ID", ENTITY_NAME, "idexists");
            }
            guarantor = guarantorService.save(guarantorObj);
            guarantorArry.add(guarantor);
        }

        return ResponseEntity
            .created(new URI("/api/guarantors/list" + guarantor.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, guarantor.getId().toString()))
            .body(guarantorArry);
    }
}
