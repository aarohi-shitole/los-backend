package com.techvg.los.service.mapper;

import com.techvg.los.domain.Member;
import com.techvg.los.domain.MemberBank;
import com.techvg.los.service.dto.MemberBankDTO;
import com.techvg.los.service.dto.MemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberBank} and its DTO {@link MemberBankDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberBankMapper extends EntityMapper<MemberBankDTO, MemberBank> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    MemberBankDTO toDto(MemberBank s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}
