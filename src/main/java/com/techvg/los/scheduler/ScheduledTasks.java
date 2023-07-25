package com.techvg.los.scheduler;

import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.service.LoanAccountQueryService;
import com.techvg.los.service.LoanAccountService;
import com.techvg.los.service.criteria.LoanAccountCriteria;
import com.techvg.los.service.dto.LoanAccountDTO;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LoanAccountQueryService loanAccountQueryService;

    @Autowired
    private LoanAccountService loanAccountService;

    @Scheduled(cron = "${schedular.loan.cron}")
    public void reportCurrentTime() {
        System.out.println("sheduler on");
        try {
            checkLoanStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLoanStatus() {
        LoanAccountCriteria criteria = new LoanAccountCriteria();

        Pageable pageable = Pageable.ofSize(1000000);
        Page<LoanAccountDTO> page = loanAccountQueryService.findByCriteria(criteria, pageable);

        for (LoanAccountDTO loanAccountDTO : page.getContent()) {
            Instant today = getInstantDate(0);
            Instant disbursementDate = loanAccountDTO.getDisbursementDate();

            ZoneId zone = ZoneId.of("Asia/Kolkata");
            int diff = 1;
            try {
                Period period = Period.between(LocalDate.ofInstant(today, zone), LocalDate.ofInstant(disbursementDate, zone));
                diff = Math.abs(period.getDays());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (diff > 1 && loanAccountDTO.getStatus().equals(LoanStatus.AWAITED_DIS)) {
                loanAccountDTO.setDisbursementAllowance(false);

                loanAccountService.save(loanAccountDTO);
            }
        }
    }

    private Instant getInstantDate(int monthAgo) {
        Calendar c1 = Calendar.getInstance();

        if (monthAgo != 0) {
            c1.add(Calendar.DAY_OF_MONTH, monthAgo);
        }

        Date dateOne = c1.getTime();

        return dateOne.toInstant();
    }
}
