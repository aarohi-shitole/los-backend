import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanAccount } from '../loan-account.model';
import { LoanAccountService } from '../service/loan-account.service';

@Injectable({ providedIn: 'root' })
export class LoanAccountRoutingResolveService implements Resolve<ILoanAccount | null> {
  constructor(protected service: LoanAccountService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanAccount | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanAccount: HttpResponse<ILoanAccount>) => {
          if (loanAccount.body) {
            return of(loanAccount.body);
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
