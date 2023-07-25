package com.techvg.los.service.mapper;

import com.techvg.los.domain.Epay;
import com.techvg.los.service.dto.EpayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Epay} and its DTO {@link EpayDTO}.
 */
@Mapper(componentModel = "spring")
public interface EpayMapper extends EntityMapper<EpayDTO, Epay> {}
