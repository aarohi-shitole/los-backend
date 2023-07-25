import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVouchersHistory } from '../vouchers-history.model';
import { VouchersHistoryService } from '../service/vouchers-history.service';

@Injectable({ providedIn: 'root' })
export class VouchersHistoryRoutingResolveService implements Resolve<IVouchersHistory | null> {
  constructor(protected service: VouchersHistoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVouchersHistory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vouchersHistory: HttpResponse<IVouchersHistory>) => {
          if (vouchersHistory.body) {
            return of(vouchersHistory.body);
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
