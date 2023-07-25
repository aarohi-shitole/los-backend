import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMasterAgreement } from '../master-agreement.model';
import { MasterAgreementService } from '../service/master-agreement.service';

@Injectable({ providedIn: 'root' })
export class MasterAgreementRoutingResolveService implements Resolve<IMasterAgreement | null> {
  constructor(protected service: MasterAgreementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMasterAgreement | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((masterAgreement: HttpResponse<IMasterAgreement>) => {
          if (masterAgreement.body) {
            return of(masterAgreement.body);
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
