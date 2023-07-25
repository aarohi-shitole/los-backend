import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccountHeadCode } from '../account-head-code.model';
import { AccountHeadCodeService } from '../service/account-head-code.service';

@Injectable({ providedIn: 'root' })
export class AccountHeadCodeRoutingResolveService implements Resolve<IAccountHeadCode | null> {
  constructor(protected service: AccountHeadCodeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountHeadCode | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accountHeadCode: HttpResponse<IAccountHeadCode>) => {
          if (accountHeadCode.body) {
            return of(accountHeadCode.body);
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
