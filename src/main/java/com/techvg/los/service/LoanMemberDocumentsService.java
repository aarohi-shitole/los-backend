package com.techvg.los.service;

import com.techvg.los.domain.LoanMemberDocuments;
import com.techvg.los.repository.LoanMemberDocumentsRepository;
import com.techvg.los.service.dto.LoanMemberDocumentsDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.mapper.LoanMemberDocumentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanMemberDocumentsService {

    private final Logger log = LoggerFactory.getLogger(LoanMemberDocumentsQueryService.class);

    private final LoanMemberDocumentsRepository loanMemberDocumentsRepository;

    private final LoanMemberDocumentsMapper loanMemberDocumentsMapper;

    public LoanMemberDocumentsService(
        LoanMemberDocumentsRepository loanMemberDocumentsRepository,
        LoanMemberDocumentsMapper loanMemberDocumentsMapper
    ) {
        this.loanMemberDocumentsRepository = loanMemberDocumentsRepository;
        this.loanMemberDocumentsMapper = loanMemberDocumentsMapper;
    }

    @Transactional(readOnly = true)
    public Optional<LoanMemberDocumentsDTO> findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return loanMemberDocumentsRepository.findById(id).map(loanMemberDocumentsMapper::toDto);
    }

    public LoanMemberDocumentsDTO save(LoanMemberDocumentsDTO loanMemberDocumentsDTO) {
        log.debug("Request to save loanMemberDocuments: {}", loanMemberDocumentsDTO);
        LoanMemberDocuments loanMemberDocuments = loanMemberDocumentsMapper.toEntity(loanMemberDocumentsDTO);
        loanMemberDocuments = loanMemberDocumentsRepository.save(loanMemberDocuments);
        return loanMemberDocumentsMapper.toDto(loanMemberDocuments);
    }

    public LoanMemberDocumentsDTO update(LoanMemberDocumentsDTO loanMemberDocumentsDTO) {
        log.debug("Request to update Member : {}", loanMemberDocumentsDTO);
        LoanMemberDocuments loanMemberDocuments = loanMemberDocumentsMapper.toEntity(loanMemberDocumentsDTO);
        loanMemberDocuments = loanMemberDocumentsRepository.save(loanMemberDocuments);
        return loanMemberDocumentsMapper.toDto(loanMemberDocuments);
    }

    @Transactional(readOnly = true)
    public Page<LoanMemberDocumentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return loanMemberDocumentsRepository.findAll(pageable).map(loanMemberDocumentsMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        loanMemberDocumentsRepository.deleteById(id);
    }
}
