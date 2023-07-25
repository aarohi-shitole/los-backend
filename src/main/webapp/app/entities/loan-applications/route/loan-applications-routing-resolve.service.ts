import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanApplications } from '../loan-applications.model';
import { LoanApplicationsService } from '../service/loan-applications.service';

@Injectable({ providedIn: 'root' })
export class LoanApplicationsRoutingResolveService implements Resolve<ILoanApplications | null> {
  constructor(protected service: LoanApplicationsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanApplications | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanApplications: HttpResponse<ILoanApplications>) => {
          if (loanApplications.body) {
            return of(loanApplications.body);
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
