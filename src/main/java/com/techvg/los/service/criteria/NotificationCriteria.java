package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.NotificationType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Notification} entity. This class is used
 * in {@link com.techvg.los.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering NotificationType
     */
    public static class NotificationTypeFilter extends Filter<NotificationType> {

        public NotificationTypeFilter() {}

        public NotificationTypeFilter(NotificationTypeFilter filter) {
            super(filter);
        }

        @Override
        public NotificationTypeFilter copy() {
            return new NotificationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter message;

    private NotificationTypeFilter notificationType;

    private BooleanFilter isActionRequired;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter sendFrom;

    private LongFilter sendTo;

    private LongFilter loanApplicationsId;

    private Boolean distinct;

    public NotificationCriteria() {}

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.notificationType = other.notificationType == null ? null : other.notificationType.copy();
        this.isActionRequired = other.isActionRequired == null ? null : other.isActionRequired.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.sendFrom = other.sendFrom == null ? null : other.sendFrom.copy();
        this.sendTo = other.sendTo == null ? null : other.sendTo.copy();
        this.loanApplicationsId = other.loanApplicationsId == null ? null : other.loanApplicationsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public NotificationTypeFilter getNotificationType() {
        return notificationType;
    }

    public NotificationTypeFilter notificationType() {
        if (notificationType == null) {
            notificationType = new NotificationTypeFilter();
        }
        return notificationType;
    }

    public void setNotificationType(NotificationTypeFilter notificationType) {
        this.notificationType = notificationType;
    }

    public BooleanFilter getIsActionRequired() {
        return isActionRequired;
    }

    public BooleanFilter isActionRequired() {
        if (isActionRequired == null) {
            isActionRequired = new BooleanFilter();
        }
        return isActionRequired;
    }

    public void setIsActionRequired(BooleanFilter isActionRequired) {
        this.isActionRequired = isActionRequired;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getSendFrom() {
        return sendFrom;
    }

    public LongFilter sendFrom() {
        if (sendFrom == null) {
            sendFrom = new LongFilter();
        }
        return sendFrom;
    }

    public void setSendFrom(LongFilter sendFrom) {
        this.sendFrom = sendFrom;
    }

    public LongFilter getSendTo() {
        return sendTo;
    }

    public LongFilter sendTo() {
        if (sendTo == null) {
            sendTo = new LongFilter();
        }
        return sendTo;
    }

    public void setSendTo(LongFilter sendTo) {
        this.sendTo = sendTo;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    public LongFilter getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public LongFilter loanApplicationsId() {
        if (loanApplicationsId == null) {
            loanApplicationsId = new LongFilter();
        }
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(LongFilter loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificationCriteria that = (NotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(notificationType, that.notificationType) &&
            Objects.equals(isActionRequired, that.isActionRequired) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(sendFrom, that.sendFrom) &&
            Objects.equals(sendTo, that.sendTo) &&
            Objects.equals(loanApplicationsId, that.loanApplicationsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            message,
            notificationType,
            isActionRequired,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            sendFrom,
            sendTo,
            loanApplicationsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (notificationType != null ? "notificationType=" + notificationType + ", " : "") +
            (isActionRequired != null ? "isActionRequired=" + isActionRequired + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (sendFrom != null ? "sendFrom=" + sendFrom + ", " : "") +
            (sendTo != null ? "sendTo=" + sendTo + ", " : "") +
            (loanApplicationsId != null ? "loanApplicationsId=" + loanApplicationsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
