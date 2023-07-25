package com.techvg.los.domain;

import com.techvg.los.domain.enumeration.PaymentMode;
import com.techvg.los.domain.enumeration.VoucherCode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vouchers.
 */
@Entity
@Table(name = "vouchers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vouchers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "voucher_date")
    private Instant voucherDate;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private VoucherCode code;

    @Column(name = "narration")
    private String narration;

    @Column(name = "authorised_by")
    private String authorisedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private PaymentMode mode;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vouchers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVoucherDate() {
        return this.voucherDate;
    }

    public Vouchers voucherDate(Instant voucherDate) {
        this.setVoucherDate(voucherDate);
        return this;
    }

    public void setVoucherDate(Instant voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherNo() {
        return this.voucherNo;
    }

    public Vouchers voucherNo(String voucherNo) {
        this.setVoucherNo(voucherNo);
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getPreparedBy() {
        return this.preparedBy;
    }

    public Vouchers preparedBy(String preparedBy) {
        this.setPreparedBy(preparedBy);
        return this;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public VoucherCode getCode() {
        return this.code;
    }

    public Vouchers code(VoucherCode code) {
        this.setCode(code);
        return this;
    }

    public void setCode(VoucherCode code) {
        this.code = code;
    }

    public String getNarration() {
        return this.narration;
    }

    public Vouchers narration(String narration) {
        this.setNarration(narration);
        return this;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getAuthorisedBy() {
        return this.authorisedBy;
    }

    public Vouchers authorisedBy(String authorisedBy) {
        this.setAuthorisedBy(authorisedBy);
        return this;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public PaymentMode getMode() {
        return this.mode;
    }

    public Vouchers mode(PaymentMode mode) {
        this.setMode(mode);
        return this;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Vouchers isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Vouchers lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Vouchers lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Vouchers freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Vouchers freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Vouchers freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Vouchers freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public Vouchers freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public Vouchers freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vouchers)) {
            return false;
        }
        return id != null && id.equals(((Vouchers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vouchers{" +
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
