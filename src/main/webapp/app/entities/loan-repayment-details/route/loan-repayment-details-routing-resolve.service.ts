import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import { LoanRepaymentDetailsService } from '../service/loan-repayment-details.service';

@Injectable({ providedIn: 'root' })
export class LoanRepaymentDetailsRoutingResolveService implements Resolve<ILoanRepaymentDetails | null> {
  constructor(protected service: LoanRepaymentDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanRepaymentDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanRepaymentDetails: HttpResponse<ILoanRepaymentDetails>) => {
          if (loanRepaymentDetails.body) {
            return of(loanRepaymentDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
