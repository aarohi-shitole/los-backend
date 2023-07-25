package com.techvg.los.service.mapper;

import com.techvg.los.domain.Vouchers;
import com.techvg.los.service.dto.VouchersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vouchers} and its DTO {@link VouchersDTO}.
 */
@Mapper(componentModel = "spring")
public interface VouchersMapper extends EntityMapper<VouchersDTO, Vouchers> {}
