import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherDetails } from '../voucher-details.model';
import { VoucherDetailsService } from '../service/voucher-details.service';

@Injectable({ providedIn: 'root' })
export class VoucherDetailsRoutingResolveService implements Resolve<IVoucherDetails | null> {
  constructor(protected service: VoucherDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoucherDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((voucherDetails: HttpResponse<IVoucherDetails>) => {
          if (voucherDetails.body) {
            return of(voucherDetails.body);
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
