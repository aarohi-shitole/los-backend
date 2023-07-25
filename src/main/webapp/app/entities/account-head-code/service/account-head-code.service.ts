import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccountHeadCode, NewAccountHeadCode } from '../account-head-code.model';

export type PartialUpdateAccountHeadCode = Partial<IAccountHeadCode> & Pick<IAccountHeadCode, 'id'>;

type RestOf<T extends IAccountHeadCode | NewAccountHeadCode> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestAccountHeadCode = RestOf<IAccountHeadCode>;

export type NewRestAccountHeadCode = RestOf<NewAccountHeadCode>;

export type PartialUpdateRestAccountHeadCode = RestOf<PartialUpdateAccountHeadCode>;

export type EntityResponseType = HttpResponse<IAccountHeadCode>;
export type EntityArrayResponseType = HttpResponse<IAccountHeadCode[]>;

@Injectable({ providedIn: 'root' })
export class AccountHeadCodeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/account-head-codes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accountHeadCode: NewAccountHeadCode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountHeadCode);
    return this.http
      .post<RestAccountHeadCode>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(accountHeadCode: IAccountHeadCode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountHeadCode);
    return this.http
      .put<RestAccountHeadCode>(`${this.resourceUrl}/${this.getAccountHeadCodeIdentifier(accountHeadCode)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(accountHeadCode: PartialUpdateAccountHeadCode): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountHeadCode);
    return this.http
      .patch<RestAccountHeadCode>(`${this.resourceUrl}/${this.getAccountHeadCodeIdentifier(accountHeadCode)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAccountHeadCode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAccountHeadCode[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccountHeadCodeIdentifier(accountHeadCode: Pick<IAccountHeadCode, 'id'>): number {
    return accountHeadCode.id;
  }

  compareAccountHeadCode(o1: Pick<IAccountHeadCode, 'id'> | null, o2: Pick<IAccountHeadCode, 'id'> | null): boolean {
    return o1 && o2 ? this.getAccountHeadCodeIdentifier(o1) === this.getAccountHeadCodeIdentifier(o2) : o1 === o2;
  }

  addAccountHeadCodeToCollectionIfMissing<Type extends Pick<IAccountHeadCode, 'id'>>(
    accountHeadCodeCollection: Type[],
    ...accountHeadCodesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accountHeadCodes: Type[] = accountHeadCodesToCheck.filter(isPresent);
    if (accountHeadCodes.length > 0) {
      const accountHeadCodeCollectionIdentifiers = accountHeadCodeCollection.map(
        accountHeadCodeItem => this.getAccountHeadCodeIdentifier(accountHeadCodeItem)!
      );
      const accountHeadCodesToAdd = accountHeadCodes.filter(accountHeadCodeItem => {
        const accountHeadCodeIdentifier = this.getAccountHeadCodeIdentifier(accountHeadCodeItem);
        if (accountHeadCodeCollectionIdentifiers.includes(accountHeadCodeIdentifier)) {
          return false;
        }
        accountHeadCodeCollectionIdentifiers.push(accountHeadCodeIdentifier);
        return true;
      });
      return [...accountHeadCodesToAdd, ...accountHeadCodeCollection];
    }
    return accountHeadCodeCollection;
  }

  protected convertDateFromClient<T extends IAccountHeadCode | NewAccountHeadCode | PartialUpdateAccountHeadCode>(
    accountHeadCode: T
  ): RestOf<T> {
    return {
      ...accountHeadCode,
      lastModified: accountHeadCode.lastModified?.toJSON() ?? null,
      createdOn: accountHeadCode.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAccountHeadCode: RestAccountHeadCode): IAccountHeadCode {
    return {
      ...restAccountHeadCode,
      lastModified: restAccountHeadCode.lastModified ? dayjs(restAccountHeadCode.lastModified) : undefined,
      createdOn: restAccountHeadCode.createdOn ? dayjs(restAccountHeadCode.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAccountHeadCode>): HttpResponse<IAccountHeadCode> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAccountHeadCode[]>): HttpResponse<IAccountHeadCode[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
