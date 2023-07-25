package com.techvg.los.service.dto;

import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.NotificationType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.los.domain.Notification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationDTO implements Serializable {

    private Long id;

    @NotNull
    private String message;

    private NotificationType notificationType;

    private Boolean isActionRequired;

    private String freeField1;

    private String freeField2;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private SecurityUser sendFrom;

    private SecurityUser sendTo;

    private LoanApplicationsDTO loanApplications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Boolean getIsActionRequired() {
        return isActionRequired;
    }

    public void setIsActionRequired(Boolean isActionRequired) {
        this.isActionRequired = isActionRequired;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUser getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(SecurityUser sendFrom) {
        this.sendFrom = sendFrom;
    }

    public SecurityUser getSendTo() {
        return sendTo;
    }

    public void setSendTo(SecurityUser sendTo) {
        this.sendTo = sendTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", notificationType='" + getNotificationType() + "'" +
            ", isActionRequired='" + getIsActionRequired() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", sendFrom=" + getSendFrom() +
            ", sendTo=" + getSendTo() +
            ", loanApplications=" + getLoanApplications() +
            "}";
    }

    public LoanApplicationsDTO getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(LoanApplicationsDTO loanApplications) {
        this.loanApplications = loanApplications;
    }
}
