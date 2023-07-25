import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVouchers } from '../vouchers.model';
import { VouchersService } from '../service/vouchers.service';

@Injectable({ providedIn: 'root' })
export class VouchersRoutingResolveService implements Resolve<IVouchers | null> {
  constructor(protected service: VouchersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVouchers | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vouchers: HttpResponse<IVouchers>) => {
          if (vouchers.body) {
            return of(vouchers.body);
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
