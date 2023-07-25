import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRemarkHistory } from '../remark-history.model';
import { RemarkHistoryService } from '../service/remark-history.service';

@Injectable({ providedIn: 'root' })
export class RemarkHistoryRoutingResolveService implements Resolve<IRemarkHistory | null> {
  constructor(protected service: RemarkHistoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRemarkHistory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((remarkHistory: HttpResponse<IRemarkHistory>) => {
          if (remarkHistory.body) {
            return of(remarkHistory.body);
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
