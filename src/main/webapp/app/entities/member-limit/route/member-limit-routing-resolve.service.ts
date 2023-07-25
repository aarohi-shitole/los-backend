import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberLimit } from '../member-limit.model';
import { MemberLimitService } from '../service/member-limit.service';

@Injectable({ providedIn: 'root' })
export class MemberLimitRoutingResolveService implements Resolve<IMemberLimit | null> {
  constructor(protected service: MemberLimitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberLimit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberLimit: HttpResponse<IMemberLimit>) => {
          if (memberLimit.body) {
            return of(memberLimit.body);
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
