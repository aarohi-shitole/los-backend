import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterestConfig } from '../interest-config.model';
import { InterestConfigService } from '../service/interest-config.service';

@Injectable({ providedIn: 'root' })
export class InterestConfigRoutingResolveService implements Resolve<IInterestConfig | null> {
  constructor(protected service: InterestConfigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterestConfig | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((interestConfig: HttpResponse<IInterestConfig>) => {
          if (interestConfig.body) {
            return of(interestConfig.body);
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
