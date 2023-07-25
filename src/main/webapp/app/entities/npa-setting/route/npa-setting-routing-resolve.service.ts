import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INpaSetting } from '../npa-setting.model';
import { NpaSettingService } from '../service/npa-setting.service';

@Injectable({ providedIn: 'root' })
export class NpaSettingRoutingResolveService implements Resolve<INpaSetting | null> {
  constructor(protected service: NpaSettingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INpaSetting | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((npaSetting: HttpResponse<INpaSetting>) => {
          if (npaSetting.body) {
            return of(npaSetting.body);
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
