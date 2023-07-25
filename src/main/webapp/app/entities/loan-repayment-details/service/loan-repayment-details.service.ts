import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanRepaymentDetails, NewLoanRepaymentDetails } from '../loan-repayment-details.model';

export type PartialUpdateLoanRepaymentDetails = Partial<ILoanRepaymentDetails> & Pick<ILoanRepaymentDetails, 'id'>;

type RestOf<T extends ILoanRepaymentDetails | NewLoanRepaymentDetails> = Omit<T, 'repaymentDate' | 'lastModified'> & {
  repaymentDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanRepaymentDetails = RestOf<ILoanRepaymentDetails>;

export type NewRestLoanRepaymentDetails = RestOf<NewLoanRepaymentDetails>;

export type PartialUpdateRestLoanRepaymentDetails = RestOf<PartialUpdateLoanRepaymentDetails>;

export type EntityResponseType = HttpResponse<ILoanRepaymentDetails>;
export type EntityArrayResponseType = HttpResponse<ILoanRepaymentDetails[]>;

@Injectable({ providedIn: 'root' })
export class LoanRepaymentDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-repayment-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanRepaymentDetails: NewLoanRepaymentDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanRepaymentDetails);
    return this.http
      .post<RestLoanRepaymentDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanRepaymentDetails: ILoanRepaymentDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanRepaymentDetails);
    return this.http
      .put<RestLoanRepaymentDetails>(`${this.resourceUrl}/${this.getLoanRepaymentDetailsIdentifier(loanRepaymentDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanRepaymentDetails: PartialUpdateLoanRepaymentDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanRepaymentDetails);
    return this.http
      .patch<RestLoanRepaymentDetails>(`${this.resourceUrl}/${this.getLoanRepaymentDetailsIdentifier(loanRepaymentDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanRepaymentDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanRepaymentDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanRepaymentDetailsIdentifier(loanRepaymentDetails: Pick<ILoanRepaymentDetails, 'id'>): number {
    return loanRepaymentDetails.id;
  }

  compareLoanRepaymentDetails(o1: Pick<ILoanRepaymentDetails, 'id'> | null, o2: Pick<ILoanRepaymentDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanRepaymentDetailsIdentifier(o1) === this.getLoanRepaymentDetailsIdentifier(o2) : o1 === o2;
  }

  addLoanRepaymentDetailsToCollectionIfMissing<Type extends Pick<ILoanRepaymentDetails, 'id'>>(
    loanRepaymentDetailsCollection: Type[],
    ...loanRepaymentDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanRepaymentDetails: Type[] = loanRepaymentDetailsToCheck.filter(isPresent);
    if (loanRepaymentDetails.length > 0) {
      const loanRepaymentDetailsCollectionIdentifiers = loanRepaymentDetailsCollection.map(
        loanRepaymentDetailsItem => this.getLoanRepaymentDetailsIdentifier(loanRepaymentDetailsItem)!
      );
      const loanRepaymentDetailsToAdd = loanRepaymentDetails.filter(loanRepaymentDetailsItem => {
        const loanRepaymentDetailsIdentifier = this.getLoanRepaymentDetailsIdentifier(loanRepaymentDetailsItem);
        if (loanRepaymentDetailsCollectionIdentifiers.includes(loanRepaymentDetailsIdentifier)) {
          return false;
        }
        loanRepaymentDetailsCollectionIdentifiers.push(loanRepaymentDetailsIdentifier);
        return true;
      });
      return [...loanRepaymentDetailsToAdd, ...loanRepaymentDetailsCollection];
    }
    return loanRepaymentDetailsCollection;
  }

  protected convertDateFromClient<T extends ILoanRepaymentDetails | NewLoanRepaymentDetails | PartialUpdateLoanRepaymentDetails>(
    loanRepaymentDetails: T
  ): RestOf<T> {
    return {
      ...loanRepaymentDetails,
      repaymentDate: loanRepaymentDetails.repaymentDate?.toJSON() ?? null,
      lastModified: loanRepaymentDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanRepaymentDetails: RestLoanRepaymentDetails): ILoanRepaymentDetails {
    return {
      ...restLoanRepaymentDetails,
      repaymentDate: restLoanRepaymentDetails.repaymentDate ? dayjs(restLoanRepaymentDetails.repaymentDate) : undefined,
      lastModified: restLoanRepaymentDetails.lastModified ? dayjs(restLoanRepaymentDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanRepaymentDetails>): HttpResponse<ILoanRepaymentDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanRepaymentDetails[]>): HttpResponse<ILoanRepaymentDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
