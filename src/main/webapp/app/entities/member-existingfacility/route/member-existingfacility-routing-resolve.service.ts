import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberExistingfacility } from '../member-existingfacility.model';
import { MemberExistingfacilityService } from '../service/member-existingfacility.service';

@Injectable({ providedIn: 'root' })
export class MemberExistingfacilityRoutingResolveService implements Resolve<IMemberExistingfacility | null> {
  constructor(protected service: MemberExistingfacilityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberExistingfacility | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberExistingfacility: HttpResponse<IMemberExistingfacility>) => {
          if (memberExistingfacility.body) {
            return of(memberExistingfacility.body);
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
