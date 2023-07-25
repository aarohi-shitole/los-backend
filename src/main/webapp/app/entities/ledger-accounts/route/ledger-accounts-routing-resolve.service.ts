import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILedgerAccounts } from '../ledger-accounts.model';
import { LedgerAccountsService } from '../service/ledger-accounts.service';

@Injectable({ providedIn: 'root' })
export class LedgerAccountsRoutingResolveService implements Resolve<ILedgerAccounts | null> {
  constructor(protected service: LedgerAccountsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILedgerAccounts | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ledgerAccounts: HttpResponse<ILedgerAccounts>) => {
          if (ledgerAccounts.body) {
            return of(ledgerAccounts.body);
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
