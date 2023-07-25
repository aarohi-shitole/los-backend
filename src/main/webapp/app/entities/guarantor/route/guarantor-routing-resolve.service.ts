import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuarantor } from '../guarantor.model';
import { GuarantorService } from '../service/guarantor.service';

@Injectable({ providedIn: 'root' })
export class GuarantorRoutingResolveService implements Resolve<IGuarantor | null> {
  constructor(protected service: GuarantorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGuarantor | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((guarantor: HttpResponse<IGuarantor>) => {
          if (guarantor.body) {
            return of(guarantor.body);
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
