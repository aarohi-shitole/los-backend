import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAmortizationDetails } from '../amortization-details.model';
import { AmortizationDetailsService } from '../service/amortization-details.service';

@Injectable({ providedIn: 'root' })
export class AmortizationDetailsRoutingResolveService implements Resolve<IAmortizationDetails | null> {
  constructor(protected service: AmortizationDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAmortizationDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((amortizationDetails: HttpResponse<IAmortizationDetails>) => {
          if (amortizationDetails.body) {
            return of(amortizationDetails.body);
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
