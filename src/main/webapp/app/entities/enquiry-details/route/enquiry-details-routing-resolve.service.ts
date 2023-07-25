import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnquiryDetails } from '../enquiry-details.model';
import { EnquiryDetailsService } from '../service/enquiry-details.service';

@Injectable({ providedIn: 'root' })
export class EnquiryDetailsRoutingResolveService implements Resolve<IEnquiryDetails | null> {
  constructor(protected service: EnquiryDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnquiryDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((enquiryDetails: HttpResponse<IEnquiryDetails>) => {
          if (enquiryDetails.body) {
            return of(enquiryDetails.body);
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
