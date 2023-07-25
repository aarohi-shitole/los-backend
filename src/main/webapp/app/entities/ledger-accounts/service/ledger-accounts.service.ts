import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILedgerAccounts, NewLedgerAccounts } from '../ledger-accounts.model';

export type PartialUpdateLedgerAccounts = Partial<ILedgerAccounts> & Pick<ILedgerAccounts, 'id'>;

type RestOf<T extends ILedgerAccounts | NewLedgerAccounts> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLedgerAccounts = RestOf<ILedgerAccounts>;

export type NewRestLedgerAccounts = RestOf<NewLedgerAccounts>;

export type PartialUpdateRestLedgerAccounts = RestOf<PartialUpdateLedgerAccounts>;

export type EntityResponseType = HttpResponse<ILedgerAccounts>;
export type EntityArrayResponseType = HttpResponse<ILedgerAccounts[]>;

@Injectable({ providedIn: 'root' })
export class LedgerAccountsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ledger-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ledgerAccounts: NewLedgerAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ledgerAccounts);
    return this.http
      .post<RestLedgerAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ledgerAccounts: ILedgerAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ledgerAccounts);
    return this.http
      .put<RestLedgerAccounts>(`${this.resourceUrl}/${this.getLedgerAccountsIdentifier(ledgerAccounts)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ledgerAccounts: PartialUpdateLedgerAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ledgerAccounts);
    return this.http
      .patch<RestLedgerAccounts>(`${this.resourceUrl}/${this.getLedgerAccountsIdentifier(ledgerAccounts)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLedgerAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLedgerAccounts[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLedgerAccountsIdentifier(ledgerAccounts: Pick<ILedgerAccounts, 'id'>): number {
    return ledgerAccounts.id;
  }

  compareLedgerAccounts(o1: Pick<ILedgerAccounts, 'id'> | null, o2: Pick<ILedgerAccounts, 'id'> | null): boolean {
    return o1 && o2 ? this.getLedgerAccountsIdentifier(o1) === this.getLedgerAccountsIdentifier(o2) : o1 === o2;
  }

  addLedgerAccountsToCollectionIfMissing<Type extends Pick<ILedgerAccounts, 'id'>>(
    ledgerAccountsCollection: Type[],
    ...ledgerAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ledgerAccounts: Type[] = ledgerAccountsToCheck.filter(isPresent);
    if (ledgerAccounts.length > 0) {
      const ledgerAccountsCollectionIdentifiers = ledgerAccountsCollection.map(
        ledgerAccountsItem => this.getLedgerAccountsIdentifier(ledgerAccountsItem)!
      );
      const ledgerAccountsToAdd = ledgerAccounts.filter(ledgerAccountsItem => {
        const ledgerAccountsIdentifier = this.getLedgerAccountsIdentifier(ledgerAccountsItem);
        if (ledgerAccountsCollectionIdentifiers.includes(ledgerAccountsIdentifier)) {
          return false;
        }
        ledgerAccountsCollectionIdentifiers.push(ledgerAccountsIdentifier);
        return true;
      });
      return [...ledgerAccountsToAdd, ...ledgerAccountsCollection];
    }
    return ledgerAccountsCollection;
  }

  protected convertDateFromClient<T extends ILedgerAccounts | NewLedgerAccounts | PartialUpdateLedgerAccounts>(
    ledgerAccounts: T
  ): RestOf<T> {
    return {
      ...ledgerAccounts,
      lastModified: ledgerAccounts.lastModified?.toJSON() ?? null,
      createdOn: ledgerAccounts.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLedgerAccounts: RestLedgerAccounts): ILedgerAccounts {
    return {
      ...restLedgerAccounts,
      lastModified: restLedgerAccounts.lastModified ? dayjs(restLedgerAccounts.lastModified) : undefined,
      createdOn: restLedgerAccounts.createdOn ? dayjs(restLedgerAccounts.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLedgerAccounts>): HttpResponse<ILedgerAccounts> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLedgerAccounts[]>): HttpResponse<ILedgerAccounts[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
