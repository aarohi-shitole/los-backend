import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVouchersHistory, NewVouchersHistory } from '../vouchers-history.model';

export type PartialUpdateVouchersHistory = Partial<IVouchersHistory> & Pick<IVouchersHistory, 'id'>;

type RestOf<T extends IVouchersHistory | NewVouchersHistory> = Omit<T, 'createdOn' | 'voucherDate'> & {
  createdOn?: string | null;
  voucherDate?: string | null;
};

export type RestVouchersHistory = RestOf<IVouchersHistory>;

export type NewRestVouchersHistory = RestOf<NewVouchersHistory>;

export type PartialUpdateRestVouchersHistory = RestOf<PartialUpdateVouchersHistory>;

export type EntityResponseType = HttpResponse<IVouchersHistory>;
export type EntityArrayResponseType = HttpResponse<IVouchersHistory[]>;

@Injectable({ providedIn: 'root' })
export class VouchersHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vouchers-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vouchersHistory: NewVouchersHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchersHistory);
    return this.http
      .post<RestVouchersHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vouchersHistory: IVouchersHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchersHistory);
    return this.http
      .put<RestVouchersHistory>(`${this.resourceUrl}/${this.getVouchersHistoryIdentifier(vouchersHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vouchersHistory: PartialUpdateVouchersHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchersHistory);
    return this.http
      .patch<RestVouchersHistory>(`${this.resourceUrl}/${this.getVouchersHistoryIdentifier(vouchersHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVouchersHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVouchersHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVouchersHistoryIdentifier(vouchersHistory: Pick<IVouchersHistory, 'id'>): number {
    return vouchersHistory.id;
  }

  compareVouchersHistory(o1: Pick<IVouchersHistory, 'id'> | null, o2: Pick<IVouchersHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getVouchersHistoryIdentifier(o1) === this.getVouchersHistoryIdentifier(o2) : o1 === o2;
  }

  addVouchersHistoryToCollectionIfMissing<Type extends Pick<IVouchersHistory, 'id'>>(
    vouchersHistoryCollection: Type[],
    ...vouchersHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vouchersHistories: Type[] = vouchersHistoriesToCheck.filter(isPresent);
    if (vouchersHistories.length > 0) {
      const vouchersHistoryCollectionIdentifiers = vouchersHistoryCollection.map(
        vouchersHistoryItem => this.getVouchersHistoryIdentifier(vouchersHistoryItem)!
      );
      const vouchersHistoriesToAdd = vouchersHistories.filter(vouchersHistoryItem => {
        const vouchersHistoryIdentifier = this.getVouchersHistoryIdentifier(vouchersHistoryItem);
        if (vouchersHistoryCollectionIdentifiers.includes(vouchersHistoryIdentifier)) {
          return false;
        }
        vouchersHistoryCollectionIdentifiers.push(vouchersHistoryIdentifier);
        return true;
      });
      return [...vouchersHistoriesToAdd, ...vouchersHistoryCollection];
    }
    return vouchersHistoryCollection;
  }

  protected convertDateFromClient<T extends IVouchersHistory | NewVouchersHistory | PartialUpdateVouchersHistory>(
    vouchersHistory: T
  ): RestOf<T> {
    return {
      ...vouchersHistory,
      createdOn: vouchersHistory.createdOn?.toJSON() ?? null,
      voucherDate: vouchersHistory.voucherDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVouchersHistory: RestVouchersHistory): IVouchersHistory {
    return {
      ...restVouchersHistory,
      createdOn: restVouchersHistory.createdOn ? dayjs(restVouchersHistory.createdOn) : undefined,
      voucherDate: restVouchersHistory.voucherDate ? dayjs(restVouchersHistory.voucherDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVouchersHistory>): HttpResponse<IVouchersHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVouchersHistory[]>): HttpResponse<IVouchersHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
