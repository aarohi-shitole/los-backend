import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeclearation } from '../declearation.model';
import { DeclearationService } from '../service/declearation.service';

@Injectable({ providedIn: 'root' })
export class DeclearationRoutingResolveService implements Resolve<IDeclearation | null> {
  constructor(protected service: DeclearationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeclearation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((declearation: HttpResponse<IDeclearation>) => {
          if (declearation.body) {
            return of(declearation.body);
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
