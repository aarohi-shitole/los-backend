package com.techvg.los.service.mapper;

import com.techvg.los.domain.MasterAgreement;
import com.techvg.los.service.dto.MasterAgreementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MasterAgreement} and its DTO {@link MasterAgreementDTO}.
 */
@Mapper(componentModel = "spring")
public interface MasterAgreementMapper extends EntityMapper<MasterAgreementDTO, MasterAgreement> {}
