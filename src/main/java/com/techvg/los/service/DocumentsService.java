package com.techvg.los.service;

import com.techvg.los.domain.Documents;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.repository.DocumentsRepository;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.mapper.DocumentsMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link Documents}.
 */
@Service
@Transactional
public class DocumentsService {

    private final Logger log = LoggerFactory.getLogger(DocumentsService.class);

    private final DocumentsRepository documentsRepository;

    private final DocumentsMapper documentsMapper;

    @Autowired
    private RemarkHistoryService remarkHistoryService;

    @Value("${upload.path}")
    private String uploadPath;

    public DocumentsService(DocumentsRepository documentsRepository, DocumentsMapper documentsMapper) {
        this.documentsRepository = documentsRepository;
        this.documentsMapper = documentsMapper;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    /**
     * Save a documents.
     *
     * @param documentsDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentsDTO save(MultipartFile file, DocumentsDTO documentsDTO) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }

            // String docName = documentsDTO.getDocSubType() + documentsDTO.getDocType() +
            // documentsDTO.getMemberId();

            root = Paths.get(uploadPath + "/" + documentsDTO.getFreeField2());
            Files.copy(file.getInputStream(), root);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        log.debug("Request to save Documents : {}", documentsDTO);
        Documents documents = documentsMapper.toEntity(documentsDTO);
        documents = documentsRepository.save(documents);

        // ------------------------------------------------------------------------
        // Added remark for very first time of document upload
        RemarkHistoryDTO remarkHistoryObj = null;
        if (documentsDTO.getRemarkHistoryDTO() != null) {
            RemarkHistoryDTO remarkHistoryDTO = documentsDTO.getRemarkHistoryDTO();
            if (documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())) {
                remarkHistoryDTO.setReferenceId(documents.getMember().getId());
            } else {
                remarkHistoryDTO.setReferenceId(documents.getRefrenceId());
            }
            remarkHistoryDTO.setSecurityUserId(documentsDTO.getSecurityUserId());
            remarkHistoryDTO.setLastModified(Instant.now());
            remarkHistoryDTO.setLastModifiedBy(documentsDTO.getLastModifiedBy());
            remarkHistoryDTO.setFreeField3(documentsDTO.getFreeField4());
            remarkHistoryObj = remarkHistoryService.save(remarkHistoryDTO);
            documentsDTO.setRemarkHistoryDTO(remarkHistoryObj);
        }

        // --------------------------------------------------------------------------------------
        DocumentsDTO documentdto = documentsMapper.toDto(documents);
        documentdto.setRemarkHistoryDTO(remarkHistoryObj);
        return documentdto;
    }

    /**
     * Update a documents.
     *
     * @param documentsDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentsDTO update(DocumentsDTO documentsDTO) {
        log.debug("Request to update Documents : {}", documentsDTO);

        RemarkHistoryDTO remarkHistoryDTO = null;
        if (documentsDTO.getRemarkHistoryDTO() != null) {
            remarkHistoryDTO = documentsDTO.getRemarkHistoryDTO();
            if (documentsDTO.getTag().equalsIgnoreCase(DocumentHelper.MEMBER_ONBOARD.getValue())) {
                remarkHistoryDTO.setReferenceId(documentsDTO.getMember().getId());
            } else {
                remarkHistoryDTO.setReferenceId(documentsDTO.getRefrenceId());
            }
            remarkHistoryDTO.setSecurityUserId(documentsDTO.getSecurityUserId());
            remarkHistoryDTO.setLastModified(Instant.now());
            remarkHistoryDTO.setLastModifiedBy(documentsDTO.getLastModifiedBy());
            remarkHistoryDTO.setSecurityUserId(documentsDTO.getSecurityUserId());
            remarkHistoryDTO.setFreeField3(documentsDTO.getFreeField4());
            remarkHistoryDTO.setIsDeleted(documentsDTO.getIsDeleted());
            remarkHistoryDTO = remarkHistoryService.save(remarkHistoryDTO);
            // documentsDTO.setRemarkHistoryDTO(remarkHistoryObj);
        }
        Documents documents = documentsMapper.toEntity(documentsDTO);
        documents = documentsRepository.save(documents);
        DocumentsDTO documentdto = documentsMapper.toDto(documents);
        documentdto.setRemarkHistoryDTO(remarkHistoryDTO);
        return documentdto;
    }

    /**
     * Partially update a documents.
     *
     * @param documentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentsDTO> partialUpdate(DocumentsDTO documentsDTO) {
        log.debug("Request to partially update Documents : {}", documentsDTO);

        return documentsRepository
            .findById(documentsDTO.getId())
            .map(existingDocuments -> {
                documentsMapper.partialUpdate(existingDocuments, documentsDTO);

                return existingDocuments;
            })
            .map(documentsRepository::save)
            .map(documentsMapper::toDto);
    }

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentsRepository.findAll(pageable).map(documentsMapper::toDto);
    }

    /**
     * Get one documents by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentsDTO> findOne(Long id) {
        log.debug("Request to get Documents : {}", id);
        return documentsRepository.findById(id).map(documentsMapper::toDto);
    }

    /**
     * Delete the documents by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Documents : {}", id);
        documentsRepository.deleteById(id);
    }
}
