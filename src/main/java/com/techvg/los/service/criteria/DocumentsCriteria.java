package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Documents} entity. This class is used
 * in {@link com.techvg.los.web.rest.DocumentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter docType;

    private StringFilter docSubType;

    private StringFilter tag;

    private StringFilter fileName;

    private StringFilter filePath;

    private StringFilter fileUrl;

    private LongFilter refrenceId;

    private BooleanFilter hasVerified;

    private StringFilter remark;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private BooleanFilter isRejected;

    private LongFilter memberId;

    //    private LongFilter guarantorId;

    private Boolean distinct;

    public DocumentsCriteria() {}

    public DocumentsCriteria(DocumentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.docType = other.docType == null ? null : other.docType.copy();
        this.docSubType = other.docSubType == null ? null : other.docSubType.copy();
        this.tag = other.tag == null ? null : other.tag.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.filePath = other.filePath == null ? null : other.filePath.copy();
        this.fileUrl = other.fileUrl == null ? null : other.fileUrl.copy();
        this.refrenceId = other.refrenceId == null ? null : other.refrenceId.copy();
        this.hasVerified = other.hasVerified == null ? null : other.hasVerified.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.isRejected = other.isRejected == null ? null : other.isRejected.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        //        this.guarantorId = other.guarantorId == null ? null : other.guarantorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentsCriteria copy() {
        return new DocumentsCriteria(this);
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

    public StringFilter getDocType() {
        return docType;
    }

    public StringFilter docType() {
        if (docType == null) {
            docType = new StringFilter();
        }
        return docType;
    }

    public void setDocType(StringFilter docType) {
        this.docType = docType;
    }

    public StringFilter getDocSubType() {
        return docSubType;
    }

    public StringFilter docSubType() {
        if (docSubType == null) {
            docSubType = new StringFilter();
        }
        return docSubType;
    }

    public void setDocSubType(StringFilter docSubType) {
        this.docSubType = docSubType;
    }

    public StringFilter getTag() {
        return tag;
    }

    public StringFilter tag() {
        if (tag == null) {
            tag = new StringFilter();
        }
        return tag;
    }

    public void setTag(StringFilter tag) {
        this.tag = tag;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFilePath() {
        return filePath;
    }

    public StringFilter filePath() {
        if (filePath == null) {
            filePath = new StringFilter();
        }
        return filePath;
    }

    public void setFilePath(StringFilter filePath) {
        this.filePath = filePath;
    }

    public StringFilter getFileUrl() {
        return fileUrl;
    }

    public StringFilter fileUrl() {
        if (fileUrl == null) {
            fileUrl = new StringFilter();
        }
        return fileUrl;
    }

    public void setFileUrl(StringFilter fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LongFilter getRefrenceId() {
        return refrenceId;
    }

    public LongFilter refrenceId() {
        if (refrenceId == null) {
            refrenceId = new LongFilter();
        }
        return refrenceId;
    }

    public void setRefrenceId(LongFilter refrenceId) {
        this.refrenceId = refrenceId;
    }

    public BooleanFilter getHasVerified() {
        return hasVerified;
    }

    public BooleanFilter hasVerified() {
        if (hasVerified == null) {
            hasVerified = new BooleanFilter();
        }
        return hasVerified;
    }

    public void setHasVerified(BooleanFilter hasVerified) {
        this.hasVerified = hasVerified;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
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

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public BooleanFilter getIsRejected() {
        return isRejected;
    }

    public BooleanFilter isRejected() {
        if (isRejected == null) {
            isRejected = new BooleanFilter();
        }
        return isRejected;
    }

    public void setIsRejected(BooleanFilter isRejected) {
        this.isRejected = isRejected;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    //    public LongFilter getGuarantorId() {
    //        return guarantorId;
    //    }
    //
    //    public LongFilter guarantorId() {
    //        if (guarantorId == null) {
    //            guarantorId = new LongFilter();
    //        }
    //        return guarantorId;
    //    }
    //
    //    public void setGuarantorId(LongFilter guarantorId) {
    //        this.guarantorId = guarantorId;
    //    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentsCriteria that = (DocumentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(docType, that.docType) &&
            Objects.equals(docSubType, that.docSubType) &&
            Objects.equals(tag, that.tag) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(fileUrl, that.fileUrl) &&
            Objects.equals(refrenceId, that.refrenceId) &&
            Objects.equals(hasVerified, that.hasVerified) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(isRejected, that.isRejected) &&
            Objects.equals(memberId, that.memberId) &&
            //            Objects.equals(guarantorId, that.guarantorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            docType,
            docSubType,
            tag,
            fileName,
            filePath,
            fileUrl,
            refrenceId,
            hasVerified,
            remark,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            isRejected,
            memberId,
            //   guarantorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (docType != null ? "docType=" + docType + ", " : "") +
            (docSubType != null ? "docSubType=" + docSubType + ", " : "") +
            (tag != null ? "tag=" + tag + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (filePath != null ? "filePath=" + filePath + ", " : "") +
            (fileUrl != null ? "fileUrl=" + fileUrl + ", " : "") +
            (refrenceId != null ? "refrenceId=" + refrenceId + ", " : "") +
            (hasVerified != null ? "hasVerified=" + hasVerified + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (isRejected != null ? "isRejected=" + isRejected + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
//            (guarantorId != null ? "guarantorId=" + guarantorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
