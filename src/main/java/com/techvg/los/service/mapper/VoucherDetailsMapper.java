package com.techvg.los.service.mapper;

import com.techvg.los.domain.VoucherDetails;
import com.techvg.los.service.dto.VoucherDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoucherDetails} and its DTO {@link VoucherDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface VoucherDetailsMapper extends EntityMapper<VoucherDetailsDTO, VoucherDetails> {}
