import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanAccount, NewLoanAccount } from '../loan-account.model';

export type PartialUpdateLoanAccount = Partial<ILoanAccount> & Pick<ILoanAccount, 'id'>;

type RestOf<T extends ILoanAccount | NewLoanAccount> = Omit<
  T,
  'loanStartDate' | 'loanEndDate' | 'loanPlannedClosureDate' | 'loanCloserDate' | 'emiStartDate' | 'lastModified'
> & {
  loanStartDate?: string | null;
  loanEndDate?: string | null;
  loanPlannedClosureDate?: string | null;
  loanCloserDate?: string | null;
  emiStartDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanAccount = RestOf<ILoanAccount>;

export type NewRestLoanAccount = RestOf<NewLoanAccount>;

export type PartialUpdateRestLoanAccount = RestOf<PartialUpdateLoanAccount>;

export type EntityResponseType = HttpResponse<ILoanAccount>;
export type EntityArrayResponseType = HttpResponse<ILoanAccount[]>;

@Injectable({ providedIn: 'root' })
export class LoanAccountService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanAccount: NewLoanAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanAccount);
    return this.http
      .post<RestLoanAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanAccount: ILoanAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanAccount);
    return this.http
      .put<RestLoanAccount>(`${this.resourceUrl}/${this.getLoanAccountIdentifier(loanAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanAccount: PartialUpdateLoanAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanAccount);
    return this.http
      .patch<RestLoanAccount>(`${this.resourceUrl}/${this.getLoanAccountIdentifier(loanAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanAccountIdentifier(loanAccount: Pick<ILoanAccount, 'id'>): number {
    return loanAccount.id;
  }

  compareLoanAccount(o1: Pick<ILoanAccount, 'id'> | null, o2: Pick<ILoanAccount, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanAccountIdentifier(o1) === this.getLoanAccountIdentifier(o2) : o1 === o2;
  }

  addLoanAccountToCollectionIfMissing<Type extends Pick<ILoanAccount, 'id'>>(
    loanAccountCollection: Type[],
    ...loanAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanAccounts: Type[] = loanAccountsToCheck.filter(isPresent);
    if (loanAccounts.length > 0) {
      const loanAccountCollectionIdentifiers = loanAccountCollection.map(
        loanAccountItem => this.getLoanAccountIdentifier(loanAccountItem)!
      );
      const loanAccountsToAdd = loanAccounts.filter(loanAccountItem => {
        const loanAccountIdentifier = this.getLoanAccountIdentifier(loanAccountItem);
        if (loanAccountCollectionIdentifiers.includes(loanAccountIdentifier)) {
          return false;
        }
        loanAccountCollectionIdentifiers.push(loanAccountIdentifier);
        return true;
      });
      return [...loanAccountsToAdd, ...loanAccountCollection];
    }
    return loanAccountCollection;
  }

  protected convertDateFromClient<T extends ILoanAccount | NewLoanAccount | PartialUpdateLoanAccount>(loanAccount: T): RestOf<T> {
    return {
      ...loanAccount,
      loanStartDate: loanAccount.loanStartDate?.toJSON() ?? null,
      loanEndDate: loanAccount.loanEndDate?.toJSON() ?? null,
      loanPlannedClosureDate: loanAccount.loanPlannedClosureDate?.toJSON() ?? null,
      loanCloserDate: loanAccount.loanCloserDate?.toJSON() ?? null,
      emiStartDate: loanAccount.emiStartDate?.toJSON() ?? null,
      lastModified: loanAccount.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanAccount: RestLoanAccount): ILoanAccount {
    return {
      ...restLoanAccount,
      loanStartDate: restLoanAccount.loanStartDate ? dayjs(restLoanAccount.loanStartDate) : undefined,
      loanEndDate: restLoanAccount.loanEndDate ? dayjs(restLoanAccount.loanEndDate) : undefined,
      loanPlannedClosureDate: restLoanAccount.loanPlannedClosureDate ? dayjs(restLoanAccount.loanPlannedClosureDate) : undefined,
      loanCloserDate: restLoanAccount.loanCloserDate ? dayjs(restLoanAccount.loanCloserDate) : undefined,
      emiStartDate: restLoanAccount.emiStartDate ? dayjs(restLoanAccount.emiStartDate) : undefined,
      lastModified: restLoanAccount.lastModified ? dayjs(restLoanAccount.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanAccount>): HttpResponse<ILoanAccount> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanAccount[]>): HttpResponse<ILoanAccount[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
