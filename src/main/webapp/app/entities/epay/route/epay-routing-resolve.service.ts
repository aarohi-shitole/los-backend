import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEpay } from '../epay.model';
import { EpayService } from '../service/epay.service';

@Injectable({ providedIn: 'root' })
export class EpayRoutingResolveService implements Resolve<IEpay | null> {
  constructor(protected service: EpayService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEpay | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((epay: HttpResponse<IEpay>) => {
          if (epay.body) {
            return of(epay.body);
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
