import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';
import { OrgPrerequisiteDocService } from '../service/org-prerequisite-doc.service';

@Injectable({ providedIn: 'root' })
export class OrgPrerequisiteDocRoutingResolveService implements Resolve<IOrgPrerequisiteDoc | null> {
  constructor(protected service: OrgPrerequisiteDocService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrgPrerequisiteDoc | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orgPrerequisiteDoc: HttpResponse<IOrgPrerequisiteDoc>) => {
          if (orgPrerequisiteDoc.body) {
            return of(orgPrerequisiteDoc.body);
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
