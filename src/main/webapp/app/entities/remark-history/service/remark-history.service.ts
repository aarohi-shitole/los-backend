import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRemarkHistory, NewRemarkHistory } from '../remark-history.model';

export type PartialUpdateRemarkHistory = Partial<IRemarkHistory> & Pick<IRemarkHistory, 'id'>;

type RestOf<T extends IRemarkHistory | NewRemarkHistory> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

export type RestRemarkHistory = RestOf<IRemarkHistory>;

export type NewRestRemarkHistory = RestOf<NewRemarkHistory>;

export type PartialUpdateRestRemarkHistory = RestOf<PartialUpdateRemarkHistory>;

export type EntityResponseType = HttpResponse<IRemarkHistory>;
export type EntityArrayResponseType = HttpResponse<IRemarkHistory[]>;

@Injectable({ providedIn: 'root' })
export class RemarkHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/remark-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(remarkHistory: NewRemarkHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remarkHistory);
    return this.http
      .post<RestRemarkHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(remarkHistory: IRemarkHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remarkHistory);
    return this.http
      .put<RestRemarkHistory>(`${this.resourceUrl}/${this.getRemarkHistoryIdentifier(remarkHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(remarkHistory: PartialUpdateRemarkHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(remarkHistory);
    return this.http
      .patch<RestRemarkHistory>(`${this.resourceUrl}/${this.getRemarkHistoryIdentifier(remarkHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRemarkHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRemarkHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRemarkHistoryIdentifier(remarkHistory: Pick<IRemarkHistory, 'id'>): number {
    return remarkHistory.id;
  }

  compareRemarkHistory(o1: Pick<IRemarkHistory, 'id'> | null, o2: Pick<IRemarkHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getRemarkHistoryIdentifier(o1) === this.getRemarkHistoryIdentifier(o2) : o1 === o2;
  }

  addRemarkHistoryToCollectionIfMissing<Type extends Pick<IRemarkHistory, 'id'>>(
    remarkHistoryCollection: Type[],
    ...remarkHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const remarkHistories: Type[] = remarkHistoriesToCheck.filter(isPresent);
    if (remarkHistories.length > 0) {
      const remarkHistoryCollectionIdentifiers = remarkHistoryCollection.map(
        remarkHistoryItem => this.getRemarkHistoryIdentifier(remarkHistoryItem)!
      );
      const remarkHistoriesToAdd = remarkHistories.filter(remarkHistoryItem => {
        const remarkHistoryIdentifier = this.getRemarkHistoryIdentifier(remarkHistoryItem);
        if (remarkHistoryCollectionIdentifiers.includes(remarkHistoryIdentifier)) {
          return false;
        }
        remarkHistoryCollectionIdentifiers.push(remarkHistoryIdentifier);
        return true;
      });
      return [...remarkHistoriesToAdd, ...remarkHistoryCollection];
    }
    return remarkHistoryCollection;
  }

  protected convertDateFromClient<T extends IRemarkHistory | NewRemarkHistory | PartialUpdateRemarkHistory>(remarkHistory: T): RestOf<T> {
    return {
      ...remarkHistory,
      createdOn: remarkHistory.createdOn?.toJSON() ?? null,
      lastModified: remarkHistory.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRemarkHistory: RestRemarkHistory): IRemarkHistory {
    return {
      ...restRemarkHistory,
      createdOn: restRemarkHistory.createdOn ? dayjs(restRemarkHistory.createdOn) : undefined,
      lastModified: restRemarkHistory.lastModified ? dayjs(restRemarkHistory.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRemarkHistory>): HttpResponse<IRemarkHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRemarkHistory[]>): HttpResponse<IRemarkHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
