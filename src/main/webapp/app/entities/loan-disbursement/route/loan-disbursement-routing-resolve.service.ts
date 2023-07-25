import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanDisbursement } from '../loan-disbursement.model';
import { LoanDisbursementService } from '../service/loan-disbursement.service';

@Injectable({ providedIn: 'root' })
export class LoanDisbursementRoutingResolveService implements Resolve<ILoanDisbursement | null> {
  constructor(protected service: LoanDisbursementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanDisbursement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanDisbursement: HttpResponse<ILoanDisbursement>) => {
          if (loanDisbursement.body) {
            return of(loanDisbursement.body);
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
