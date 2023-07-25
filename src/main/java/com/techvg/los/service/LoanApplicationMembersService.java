package com.techvg.los.service;

import com.techvg.los.domain.LoanApplicationMembers;
import com.techvg.los.repository.LoanApplicationMembersRepository;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.mapper.LoanApplicationMembersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanApplicationMembersService {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationMembersQueryService.class);

    private final LoanApplicationMembersRepository loanApplicationMembersRepository;

    private final LoanApplicationMembersMapper loanApplicationMembersMapper;

    public LoanApplicationMembersService(
        LoanApplicationMembersRepository loanApplicationMembersRepository,
        LoanApplicationMembersMapper loanApplicationMembersMapper
    ) {
        this.loanApplicationMembersRepository = loanApplicationMembersRepository;
        this.loanApplicationMembersMapper = loanApplicationMembersMapper;
    }

    public LoanApplicationMembersDTO save(LoanApplicationMembersDTO loanApplicationMembersDTO) {
        log.debug("Request to save loanApplicationMembers: {}", loanApplicationMembersDTO);
        LoanApplicationMembers loanApplicationMembers = loanApplicationMembersMapper.toEntity(loanApplicationMembersDTO);
        loanApplicationMembers = loanApplicationMembersRepository.save(loanApplicationMembers);
        return loanApplicationMembersMapper.toDto(loanApplicationMembers);
    }

    public LoanApplicationMembersDTO update(LoanApplicationMembersDTO loanApplicationMembersDTO) {
        log.debug("Request to update Member : {}", loanApplicationMembersDTO);
        LoanApplicationMembers loanApplicationMembers = loanApplicationMembersMapper.toEntity(loanApplicationMembersDTO);
        loanApplicationMembers = loanApplicationMembersRepository.save(loanApplicationMembers);
        return loanApplicationMembersMapper.toDto(loanApplicationMembers);
    }

    @Transactional(readOnly = true)
    public Page<LoanApplicationMembersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return loanApplicationMembersRepository.findAll(pageable).map(loanApplicationMembersMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        loanApplicationMembersRepository.deleteById(id);
    }
}
