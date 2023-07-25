package com.techvg.los.service.mapper;

import com.techvg.los.domain.MemberLimit;
import com.techvg.los.service.dto.MemberLimitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberLimit} and its DTO {@link MemberLimitDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberLimitMapper extends EntityMapper<MemberLimitDTO, MemberLimit> {}
