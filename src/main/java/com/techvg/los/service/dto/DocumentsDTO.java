package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.techvg.los.domain.Documents} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentsDTO implements Serializable {

    private Long id;

    private String docType;

    private String docSubType;

    private String tag;

    private String fileName;

    private String fileType;

    private String filePath;

    private String fileUrl;

    private Long refrenceId;

    private Boolean hasVerified;

    private String remark;

    private String lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private String createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Boolean isRejected;

    private MemberDTO member;

    private RemarkHistoryDTO remarkHistoryDTO;

    private Long securityUserId;

    // private Long guarantorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(Long securityUserId) {
        this.securityUserId = securityUserId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocSubType() {
        return docSubType;
    }

    public void setDocSubType(String docSubType) {
        this.docSubType = docSubType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(Long refrenceId) {
        this.refrenceId = refrenceId;
    }

    public Boolean getHasVerified() {
        return hasVerified;
    }

    public void setHasVerified(Boolean hasVerified) {
        this.hasVerified = hasVerified;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    //    public Long getGuarantorId() {
    //        return guarantorId;
    //    }
    //
    //    public void setGuarantorId(Long guarantorId) {
    //        this.guarantorId = guarantorId;
    //    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentsDTO)) {
            return false;
        }

        DocumentsDTO documentsDTO = (DocumentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentsDTO{" +
            "id=" + getId() +
            ", docType='" + getDocType() + "'" +
            ", docSubType='" + getDocSubType() + "'" +
            ", tag='" + getTag() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", refrenceId=" + getRefrenceId() +
            ", hasVerified='" + getHasVerified() + "'" +
            ", remark='" + getRemark() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", isRejected='" + getIsRejected() + "'" +
            ", member=" + getMember() +
            ", securityUserId=" + getSecurityUserId()+
//            ", guarantorId=" + getGuarantorId() +
            "}";
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public RemarkHistoryDTO getRemarkHistoryDTO() {
        return remarkHistoryDTO;
    }

    public void setRemarkHistoryDTO(RemarkHistoryDTO remarkHistoryDTO) {
        this.remarkHistoryDTO = remarkHistoryDTO;
    }
}
