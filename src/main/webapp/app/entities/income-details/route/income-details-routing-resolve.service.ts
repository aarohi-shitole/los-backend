import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIncomeDetails } from '../income-details.model';
import { IncomeDetailsService } from '../service/income-details.service';

@Injectable({ providedIn: 'root' })
export class IncomeDetailsRoutingResolveService implements Resolve<IIncomeDetails | null> {
  constructor(protected service: IncomeDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncomeDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((incomeDetails: HttpResponse<IIncomeDetails>) => {
          if (incomeDetails.body) {
            return of(incomeDetails.body);
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
