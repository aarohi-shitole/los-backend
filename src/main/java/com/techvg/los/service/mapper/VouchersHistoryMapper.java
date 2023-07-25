package com.techvg.los.service.mapper;

import com.techvg.los.domain.VouchersHistory;
import com.techvg.los.service.dto.VouchersHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VouchersHistory} and its DTO {@link VouchersHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface VouchersHistoryMapper extends EntityMapper<VouchersHistoryDTO, VouchersHistory> {}
