import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoanCatagory } from '../loan-catagory.model';
import { LoanCatagoryService } from '../service/loan-catagory.service';

@Injectable({ providedIn: 'root' })
export class LoanCatagoryRoutingResolveService implements Resolve<ILoanCatagory | null> {
  constructor(protected service: LoanCatagoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoanCatagory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loanCatagory: HttpResponse<ILoanCatagory>) => {
          if (loanCatagory.body) {
            return of(loanCatagory.body);
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
