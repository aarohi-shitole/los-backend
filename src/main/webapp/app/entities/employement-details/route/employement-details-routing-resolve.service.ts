import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployementDetails } from '../employement-details.model';
import { EmployementDetailsService } from '../service/employement-details.service';

@Injectable({ providedIn: 'root' })
export class EmployementDetailsRoutingResolveService implements Resolve<IEmployementDetails | null> {
  constructor(protected service: EmployementDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployementDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employementDetails: HttpResponse<IEmployementDetails>) => {
          if (employementDetails.body) {
            return of(employementDetails.body);
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
