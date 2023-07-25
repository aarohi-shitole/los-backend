import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISchemesDetails } from '../schemes-details.model';
import { SchemesDetailsService } from '../service/schemes-details.service';

@Injectable({ providedIn: 'root' })
export class SchemesDetailsRoutingResolveService implements Resolve<ISchemesDetails | null> {
  constructor(protected service: SchemesDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchemesDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((schemesDetails: HttpResponse<ISchemesDetails>) => {
          if (schemesDetails.body) {
            return of(schemesDetails.body);
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
