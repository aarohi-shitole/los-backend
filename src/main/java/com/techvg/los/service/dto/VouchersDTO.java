package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.PaymentMode;
import com.techvg.los.domain.enumeration.VoucherCode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.Vouchers} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VouchersDTO implements Serializable {

    private Long id;

    private Instant voucherDate;

    private String voucherNo;

    private String preparedBy;

    private VoucherCode code;

    private String narration;

    private String authorisedBy;

    private PaymentMode mode;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Instant voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public VoucherCode getCode() {
        return code;
    }

    public void setCode(VoucherCode code) {
        this.code = code;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VouchersDTO)) {
            return false;
        }

        VouchersDTO vouchersDTO = (VouchersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vouchersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersDTO{" +
            "id=" + getId() +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", preparedBy='" + getPreparedBy() + "'" +
            ", code='" + getCode() + "'" +
            ", narration='" + getNarration() + "'" +
            ", authorisedBy='" + getAuthorisedBy() + "'" +
            ", mode='" + getMode() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
