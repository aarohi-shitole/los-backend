import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISequenceGenerator } from '../sequence-generator.model';
import { SequenceGeneratorService } from '../service/sequence-generator.service';

@Injectable({ providedIn: 'root' })
export class SequenceGeneratorRoutingResolveService implements Resolve<ISequenceGenerator | null> {
  constructor(protected service: SequenceGeneratorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenceGenerator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sequenceGenerator: HttpResponse<ISequenceGenerator>) => {
          if (sequenceGenerator.body) {
            return of(sequenceGenerator.body);
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
