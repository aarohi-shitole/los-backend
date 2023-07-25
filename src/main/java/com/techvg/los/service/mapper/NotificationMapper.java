package com.techvg.los.service.mapper;

import com.techvg.los.domain.Member;
import com.techvg.los.domain.Notification;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.NotificationDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO
 * {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "sendFrom.id", source = "sendFrom.id")
    @Mapping(target = "sendTo.id", source = "sendTo.id")
    @Mapping(target = "loanApplications.id", source = "loanApplications.id")
    NotificationDTO toDto(Notification notification);

    @Mapping(target = "sendFrom.id", source = "sendFrom.id")
    @Mapping(target = "sendTo.id", source = "sendTo.id")
    @Mapping(target = "loanApplications.id", source = "loanApplications.id")
    Notification toEntity(NotificationDTO notificationDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract NotificationDTO toDtoId(Notification notification);
}
