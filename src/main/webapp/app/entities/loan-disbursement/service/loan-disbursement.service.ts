import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanDisbursement, NewLoanDisbursement } from '../loan-disbursement.model';

export type PartialUpdateLoanDisbursement = Partial<ILoanDisbursement> & Pick<ILoanDisbursement, 'id'>;

type RestOf<T extends ILoanDisbursement | NewLoanDisbursement> = Omit<T, 'dtDisbDate' | 'lastModified'> & {
  dtDisbDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanDisbursement = RestOf<ILoanDisbursement>;

export type NewRestLoanDisbursement = RestOf<NewLoanDisbursement>;

export type PartialUpdateRestLoanDisbursement = RestOf<PartialUpdateLoanDisbursement>;

export type EntityResponseType = HttpResponse<ILoanDisbursement>;
export type EntityArrayResponseType = HttpResponse<ILoanDisbursement[]>;

@Injectable({ providedIn: 'root' })
export class LoanDisbursementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-disbursements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanDisbursement: NewLoanDisbursement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursement);
    return this.http
      .post<RestLoanDisbursement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanDisbursement: ILoanDisbursement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursement);
    return this.http
      .put<RestLoanDisbursement>(`${this.resourceUrl}/${this.getLoanDisbursementIdentifier(loanDisbursement)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanDisbursement: PartialUpdateLoanDisbursement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanDisbursement);
    return this.http
      .patch<RestLoanDisbursement>(`${this.resourceUrl}/${this.getLoanDisbursementIdentifier(loanDisbursement)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanDisbursement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanDisbursement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanDisbursementIdentifier(loanDisbursement: Pick<ILoanDisbursement, 'id'>): number {
    return loanDisbursement.id;
  }

  compareLoanDisbursement(o1: Pick<ILoanDisbursement, 'id'> | null, o2: Pick<ILoanDisbursement, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanDisbursementIdentifier(o1) === this.getLoanDisbursementIdentifier(o2) : o1 === o2;
  }

  addLoanDisbursementToCollectionIfMissing<Type extends Pick<ILoanDisbursement, 'id'>>(
    loanDisbursementCollection: Type[],
    ...loanDisbursementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanDisbursements: Type[] = loanDisbursementsToCheck.filter(isPresent);
    if (loanDisbursements.length > 0) {
      const loanDisbursementCollectionIdentifiers = loanDisbursementCollection.map(
        loanDisbursementItem => this.getLoanDisbursementIdentifier(loanDisbursementItem)!
      );
      const loanDisbursementsToAdd = loanDisbursements.filter(loanDisbursementItem => {
        const loanDisbursementIdentifier = this.getLoanDisbursementIdentifier(loanDisbursementItem);
        if (loanDisbursementCollectionIdentifiers.includes(loanDisbursementIdentifier)) {
          return false;
        }
        loanDisbursementCollectionIdentifiers.push(loanDisbursementIdentifier);
        return true;
      });
      return [...loanDisbursementsToAdd, ...loanDisbursementCollection];
    }
    return loanDisbursementCollection;
  }

  protected convertDateFromClient<T extends ILoanDisbursement | NewLoanDisbursement | PartialUpdateLoanDisbursement>(
    loanDisbursement: T
  ): RestOf<T> {
    return {
      ...loanDisbursement,
      dtDisbDate: loanDisbursement.dtDisbDate?.toJSON() ?? null,
      lastModified: loanDisbursement.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanDisbursement: RestLoanDisbursement): ILoanDisbursement {
    return {
      ...restLoanDisbursement,
      dtDisbDate: restLoanDisbursement.dtDisbDate ? dayjs(restLoanDisbursement.dtDisbDate) : undefined,
      lastModified: restLoanDisbursement.lastModified ? dayjs(restLoanDisbursement.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanDisbursement>): HttpResponse<ILoanDisbursement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanDisbursement[]>): HttpResponse<ILoanDisbursement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
