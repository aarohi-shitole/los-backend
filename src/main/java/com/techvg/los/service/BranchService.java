package com.techvg.los.service;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.enumeration.BranchType;
import com.techvg.los.repository.AddressRepository;
import com.techvg.los.repository.BranchRepository;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.mapper.BranchMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchService.class);

    private final BranchRepository branchRepository;

    private final BranchMapper branchMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    public BranchService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    /**
     * Save a branch.
     *
     * @param branchDTO the entity to save.
     * @return the persisted entity.
     */
    public BranchDTO save(BranchDTO branchDTO) {
        log.debug("Request to save Branch : {}", branchDTO);
        //----------------Save address of branch --------------------
        if (branchDTO.getAddress() != null) {
            AddressDTO addressDTO = branchDTO.getAddress();
            if (addressDTO.getId() != null) {
                throw new BadRequestAlertException("A new address cannot already have an ID", "Address", "idexists");
            }
            AddressDTO addressObj = addressService.save(addressDTO);
            branchDTO.setAddress(addressObj);
        }
        //-------------------------------------------------------------
        Branch branch = branchMapper.toEntity(branchDTO);
        branch = branchRepository.save(branch);
        Long branchId = branch.getId();
        //------------ Update SequenceGenrator table after saving branch----
        if (BranchType.ZONAL_OFFICE.equals(branch.getBranchType())) {
            int next_val_member = 1;
            int next_val_loan_app = 1;
            int next_val_loan_account = 1;
            int next_val_voucher = 1;
            branchRepository.updateSequenceGenrator(next_val_member, next_val_loan_app, next_val_loan_account, next_val_voucher, branchId);
        }
        //------------------------------------------------------------------
        return branchMapper.toDto(branch);
    }

    /**
     * Update a branch.
     *
     * @param branchDTO the entity to save.
     * @return the persisted entity.
     */
    public BranchDTO update(BranchDTO branchDTO) {
        log.debug("Request to update Branch : {}", branchDTO);
        //----------------update address of branch -------------------------------------------
        if (branchDTO.getAddress() != null) {
            AddressDTO addressDTO = branchDTO.getAddress();
            if (addressDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", "Address", "idnull");
            }
            if (!addressRepository.existsById(addressDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", "Address", "idnotfound");
            }
            AddressDTO addressObj = addressService.update(addressDTO);
        }
        //-----------------------------------------------------------------------

        Branch branch = branchMapper.toEntity(branchDTO);
        branch = branchRepository.save(branch);
        return branchMapper.toDto(branch);
    }

    /**
     * Partially update a branch.
     *
     * @param branchDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BranchDTO> partialUpdate(BranchDTO branchDTO) {
        log.debug("Request to partially update Branch : {}", branchDTO);

        return branchRepository
            .findById(branchDTO.getId())
            .map(existingBranch -> {
                branchMapper.partialUpdate(existingBranch, branchDTO);

                return existingBranch;
            })
            .map(branchRepository::save)
            .map(branchMapper::toDto);
    }

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BranchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Branches");
        return branchRepository.findAll(pageable).map(branchMapper::toDto);
    }

    /**
     * Get one branch by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BranchDTO> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id).map(branchMapper::toDto);
    }

    /**
     * Delete the branch by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
