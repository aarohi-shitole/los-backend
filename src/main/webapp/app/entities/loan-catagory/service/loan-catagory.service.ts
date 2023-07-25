import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanCatagory, NewLoanCatagory } from '../loan-catagory.model';

export type PartialUpdateLoanCatagory = Partial<ILoanCatagory> & Pick<ILoanCatagory, 'id'>;

type RestOf<T extends ILoanCatagory | NewLoanCatagory> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestLoanCatagory = RestOf<ILoanCatagory>;

export type NewRestLoanCatagory = RestOf<NewLoanCatagory>;

export type PartialUpdateRestLoanCatagory = RestOf<PartialUpdateLoanCatagory>;

export type EntityResponseType = HttpResponse<ILoanCatagory>;
export type EntityArrayResponseType = HttpResponse<ILoanCatagory[]>;

@Injectable({ providedIn: 'root' })
export class LoanCatagoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-catagories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanCatagory: NewLoanCatagory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanCatagory);
    return this.http
      .post<RestLoanCatagory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanCatagory: ILoanCatagory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanCatagory);
    return this.http
      .put<RestLoanCatagory>(`${this.resourceUrl}/${this.getLoanCatagoryIdentifier(loanCatagory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanCatagory: PartialUpdateLoanCatagory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanCatagory);
    return this.http
      .patch<RestLoanCatagory>(`${this.resourceUrl}/${this.getLoanCatagoryIdentifier(loanCatagory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanCatagory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanCatagory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanCatagoryIdentifier(loanCatagory: Pick<ILoanCatagory, 'id'>): number {
    return loanCatagory.id;
  }

  compareLoanCatagory(o1: Pick<ILoanCatagory, 'id'> | null, o2: Pick<ILoanCatagory, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanCatagoryIdentifier(o1) === this.getLoanCatagoryIdentifier(o2) : o1 === o2;
  }

  addLoanCatagoryToCollectionIfMissing<Type extends Pick<ILoanCatagory, 'id'>>(
    loanCatagoryCollection: Type[],
    ...loanCatagoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanCatagories: Type[] = loanCatagoriesToCheck.filter(isPresent);
    if (loanCatagories.length > 0) {
      const loanCatagoryCollectionIdentifiers = loanCatagoryCollection.map(
        loanCatagoryItem => this.getLoanCatagoryIdentifier(loanCatagoryItem)!
      );
      const loanCatagoriesToAdd = loanCatagories.filter(loanCatagoryItem => {
        const loanCatagoryIdentifier = this.getLoanCatagoryIdentifier(loanCatagoryItem);
        if (loanCatagoryCollectionIdentifiers.includes(loanCatagoryIdentifier)) {
          return false;
        }
        loanCatagoryCollectionIdentifiers.push(loanCatagoryIdentifier);
        return true;
      });
      return [...loanCatagoriesToAdd, ...loanCatagoryCollection];
    }
    return loanCatagoryCollection;
  }

  protected convertDateFromClient<T extends ILoanCatagory | NewLoanCatagory | PartialUpdateLoanCatagory>(loanCatagory: T): RestOf<T> {
    return {
      ...loanCatagory,
      lastModified: loanCatagory.lastModified?.toJSON() ?? null,
      createdOn: loanCatagory.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanCatagory: RestLoanCatagory): ILoanCatagory {
    return {
      ...restLoanCatagory,
      lastModified: restLoanCatagory.lastModified ? dayjs(restLoanCatagory.lastModified) : undefined,
      createdOn: restLoanCatagory.createdOn ? dayjs(restLoanCatagory.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanCatagory>): HttpResponse<ILoanCatagory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanCatagory[]>): HttpResponse<ILoanCatagory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
