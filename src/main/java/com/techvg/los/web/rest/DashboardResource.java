package com.techvg.los.web.rest;

import com.techvg.los.domain.BranchwiseLoans;
import com.techvg.los.domain.LoanTypeCount;
import com.techvg.los.domain.TotalMonthlyLoans;
import com.techvg.los.domain.WeeklyLoanCount;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.service.BranchQueryService;
import com.techvg.los.service.LoanAccountQueryService;
import com.techvg.los.service.LoanApplicationsQueryService;
import com.techvg.los.service.LoanCatagoryService;
import com.techvg.los.service.ProductQueryService;
import com.techvg.los.service.criteria.BranchCriteria;
import com.techvg.los.service.criteria.LoanAccountCriteria;
import com.techvg.los.service.criteria.LoanApplicationsCriteria;
import com.techvg.los.service.criteria.LoanApplicationsCriteria.LoanStatusFilter;
import com.techvg.los.service.criteria.ProductCriteria;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanCatagoryDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;

@RestController
@RequestMapping("/api")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DocumentsResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private LoanAccountQueryService loanAccountQueryService;

    @Autowired
    private LoanApplicationsQueryService loanApplicationsQueryService;

    @Autowired
    private BranchQueryService branchQueryService;

    @Autowired
    private LoanCatagoryService loanCatagoryService;

    @Autowired
    private ProductQueryService productQueryservice;

    @GetMapping("/loan-applications/monthlycount")
    public ResponseEntity<List<TotalMonthlyLoans>> getHistorialLoansCount() {
        log.debug("REST request to count LoanApplications by criteria: {}");

        List<TotalMonthlyLoans> totalMonthlyLoansList = new ArrayList<TotalMonthlyLoans>();

        for (int i = 0; i < 6; i++) {
            // For new Loan application
            LoanApplicationsCriteria loanApplicationsCriteria = new LoanApplicationsCriteria();
            LoanStatusFilter loanStatusFilter = new LoanStatusFilter();
            List<LoanStatus> in = new ArrayList<>();

            in.add(LoanStatus.CLOSED);
            in.add(LoanStatus.ACTIVE);

            loanStatusFilter.setNotIn(in);
            loanApplicationsCriteria.setStatus(loanStatusFilter);

            InstantFilter lastModifiedDate = new InstantFilter();
            Instant instantNow = getInstantDate(-i);
            lastModifiedDate.setLessThanOrEqual(instantNow);
            Instant instantNow1 = getInstantDate(-(i + 1));
            lastModifiedDate.setGreaterThan(instantNow1);
            loanApplicationsCriteria.setLastModified(lastModifiedDate);

            Pageable pageable = Pageable.ofSize(1000000);
            Page<LoanApplicationsDTO> page = loanApplicationsQueryService.findByCriteria(loanApplicationsCriteria, pageable);

            TotalMonthlyLoans totalMonthlyLoan = new TotalMonthlyLoans();

            Calendar c1 = Calendar.getInstance();

            if (i != 0) {
                c1.add(Calendar.MONTH, -i);
            }

            Date dateOne = c1.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            totalMonthlyLoan.date = sdf.format(dateOne);

            int month = i + 1;
            totalMonthlyLoan.month = "Month " + month;
            totalMonthlyLoan.NewLoan = page.getContent().size();
            // For active loans
            for (LoanApplicationsDTO loanApplicationsDTO : page.getContent()) {
                LoanAccountCriteria loanAccountCriteria = new LoanAccountCriteria();

                LongFilter loanApplicationsId = new LongFilter();
                loanApplicationsId.setEquals(loanApplicationsDTO.getId());
                loanAccountCriteria.setLoanApplicationsId(loanApplicationsId);
                Page<LoanAccountDTO> loanAccountpage = loanAccountQueryService.findByCriteria(loanAccountCriteria, pageable);
                if (loanAccountpage.getContent().size() != 0) totalMonthlyLoan.totalActiveLoan += 1;
            }

            totalMonthlyLoansList.add(totalMonthlyLoan);
        }
        return ResponseEntity.ok().body(totalMonthlyLoansList);
    }

    private Instant getInstantDate(int monthAgo) {
        Calendar c1 = Calendar.getInstance();

        if (monthAgo != 0) {
            c1.add(Calendar.MONTH, monthAgo);
        }

        Date dateOne = c1.getTime();

        return dateOne.toInstant();
    }

    @GetMapping("/loan-applications/branchwise-count")
    public ResponseEntity<List<BranchwiseLoans>> getbranchwiseLoansCount() {
        log.debug("REST request to count LoanApplications by criteria: {}");

        List<BranchwiseLoans> branchwiseActiveLoansList = new ArrayList<BranchwiseLoans>();

        BranchCriteria branchCriteria = new BranchCriteria();
        LongFilter orgId = new LongFilter();
        long id = 1;
        orgId.setEquals(id);
        branchCriteria.setOrganisationId(orgId);

        List<BranchDTO> branchList = branchQueryService.findByCriteria(branchCriteria);
        for (BranchDTO branch : branchList) {
            Long branchId = branch.getId();

            LoanApplicationsCriteria loanApplicationsCriteria = new LoanApplicationsCriteria();
            LongFilter branchId2 = new LongFilter();
            branchId2.setEquals(branchId);
            loanApplicationsCriteria.setBranchId(branchId2);

            BranchwiseLoans branchwiseLoans = new BranchwiseLoans();
            branchwiseLoans.branchName = branch.getBranchName();

            List<LoanApplicationsDTO> loanApplicationsList = loanApplicationsQueryService.findByCriteria(loanApplicationsCriteria);
            for (LoanApplicationsDTO loanApplication : loanApplicationsList) {
                if (loanApplication.getSanctionAmt() == null) {
                    loanApplication.setSanctionAmt(0.0);
                }

                LoanAccountCriteria loanAccountCriteria = new LoanAccountCriteria();

                LongFilter loanApplicationsId = new LongFilter();
                loanApplicationsId.setEquals(loanApplication.getId());
                loanAccountCriteria.setLoanApplicationsId(loanApplicationsId);
                List<LoanAccountDTO> loanAccountList = loanAccountQueryService.findByCriteria(loanAccountCriteria);
                for (LoanAccountDTO LoanAccount : loanAccountList) {
                    if (LoanAccount.getId() != null) {
                        branchwiseLoans.totalActiveLoan += 1;

                        branchwiseLoans.activeLoanamount += loanApplication.getSanctionAmt();
                    }
                }
                branchwiseLoans.totalNewLoan += 1;
                branchwiseLoans.newLoanLoanamount += loanApplication.getSanctionAmt();
            }

            branchwiseActiveLoansList.add(branchwiseLoans);
        }
        return ResponseEntity.ok().body(branchwiseActiveLoansList);
    }

    @GetMapping("/loan-applications/loan-typewise-amount")
    public ResponseEntity<List<LoanTypeCount>> getLoanTypewiseList() {
        log.debug("REST request to count LoanApplications by criteria: {}");

        List<LoanTypeCount> loanTypeCountList = new ArrayList<LoanTypeCount>();

        Pageable pageable = Pageable.ofSize(1000000);
        Page<LoanCatagoryDTO> loanCatagoryList = loanCatagoryService.findAll(pageable);
        for (LoanCatagoryDTO loanCatagory : loanCatagoryList) {
            if (loanCatagory.getId() == null) {
                throw new BadRequestAlertException("Loan catagory should not be empty", applicationName, applicationName);
            }
            LoanTypeCount loanTypeCount = new LoanTypeCount();
            loanTypeCount.loanTypeName = loanCatagory.getProductName();

            long loanCatagoryId = loanCatagory.getId();
            ProductCriteria productCriteria = new ProductCriteria();
            LongFilter loanCatagoryId2 = new LongFilter();
            loanCatagoryId2.setEquals(loanCatagoryId);
            productCriteria.setLoanCatagoryId(loanCatagoryId2);

            double totalLoanAmount = 0;
            List<ProductDTO> productList = productQueryservice.findByCriteria(productCriteria);
            for (ProductDTO product : productList) {
                if (product.getId() == null) {
                    throw new BadRequestAlertException("product should not be empty", applicationName, applicationName);
                }
                long productId = product.getId();
                LoanApplicationsCriteria loanApplicationsCriteria = new LoanApplicationsCriteria();
                LongFilter productId2 = new LongFilter();
                productId2.setEquals(productId);
                loanApplicationsCriteria.setProductId(productId2);
                List<LoanApplicationsDTO> loanApplicationsList = loanApplicationsQueryService.findByCriteria(loanApplicationsCriteria);

                for (LoanApplicationsDTO loanApplication : loanApplicationsList) {
                    if (loanApplication.getSanctionAmt() == null) {
                        loanApplication.setSanctionAmt(0.0);
                    }
                    totalLoanAmount += loanApplication.getSanctionAmt();
                    loanTypeCount.totalLoan += 1;
                }
            }
            loanTypeCount.loanAmount = totalLoanAmount;
            loanTypeCountList.add(loanTypeCount);
        }
        return ResponseEntity.ok().body(loanTypeCountList);
    }

    @GetMapping("/loan-applications/weeklyloan")
    public ResponseEntity<List<WeeklyLoanCount>> getWeelkyLoans() {
        List<WeeklyLoanCount> totalWeeklyLoanList = new ArrayList<WeeklyLoanCount>();
        for (int i = 0; i < 7; i++) {
            LoanApplicationsCriteria criteria = new LoanApplicationsCriteria();
            LoanStatusFilter loanStatus = new LoanStatusFilter();
            loanStatus.setEquals(LoanStatus.SANCTIONED);
            criteria.setStatus(loanStatus);

            InstantFilter loanApplicationDate = new InstantFilter();
            Instant now = Instant.now();
            Instant day = now.minus(i, ChronoUnit.DAYS);

            Instant yesterday = day.minus(+1, ChronoUnit.DAYS);
            loanApplicationDate.setGreaterThanOrEqual(yesterday);
            loanApplicationDate.setLessThanOrEqual(day);
            criteria.setLastModified(loanApplicationDate);

            Pageable pageable = Pageable.ofSize(1000000);
            Page<LoanApplicationsDTO> page = loanApplicationsQueryService.findByCriteria(criteria, pageable);
            WeeklyLoanCount totalWeeklyLoan = new WeeklyLoanCount();
            totalWeeklyLoan.totalSanctionedLoan = page.getTotalElements();

            totalWeeklyLoan.date = loanApplicationDate.getLessThanOrEqual().toString();

            Date myDate = Date.from(day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            int day1 = calendar.get(Calendar.DAY_OF_WEEK);

            String s = null;
            switch (day1) {
                case 1:
                    s = "Sunday";
                    break;
                case 2:
                    s = "Monday";
                    break;
                case 3:
                    s = "Tuesday";
                    break;
                case 4:
                    s = "Wednesday";
                    break;
                case 5:
                    s = "Thursday";
                    break;
                case 6:
                    s = "Friday";
                    break;
                case 7:
                    s = "Saturday";
                    break;
                default:
                    System.out.println("Weekend");
                    break;
            }
            totalWeeklyLoan.day = s;

            totalWeeklyLoanList.add(totalWeeklyLoan);
        }
        return ResponseEntity.ok().body(totalWeeklyLoanList);
    }
}
