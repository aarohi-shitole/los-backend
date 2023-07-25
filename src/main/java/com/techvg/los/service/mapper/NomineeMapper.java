package com.techvg.los.service.mapper;

import com.techvg.los.domain.Member;
import com.techvg.los.domain.Nominee;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.NomineeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nominee} and its DTO {@link NomineeDTO}.
 */
@Mapper(componentModel = "spring")
public interface NomineeMapper extends EntityMapper<NomineeDTO, Nominee> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    NomineeDTO toDto(Nominee s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}
