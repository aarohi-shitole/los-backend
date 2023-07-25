package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanCatagory;
import com.techvg.los.service.dto.LoanCatagoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanCatagory} and its DTO {@link LoanCatagoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanCatagoryMapper extends EntityMapper<LoanCatagoryDTO, LoanCatagory> {}
