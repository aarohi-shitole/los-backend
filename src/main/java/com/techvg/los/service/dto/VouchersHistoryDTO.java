package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.VoucherCode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.VouchersHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VouchersHistoryDTO implements Serializable {

    private Long id;

    private Instant createdOn;

    private String createdBy;

    private Instant voucherDate;

    private VoucherCode code;

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

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Instant voucherDate) {
        this.voucherDate = voucherDate;
    }

    public VoucherCode getCode() {
        return code;
    }

    public void setCode(VoucherCode code) {
        this.code = code;
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
        if (!(o instanceof VouchersHistoryDTO)) {
            return false;
        }

        VouchersHistoryDTO vouchersHistoryDTO = (VouchersHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vouchersHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersHistoryDTO{" +
            "id=" + getId() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", code='" + getCode() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
