import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoanApplications, NewLoanApplications } from '../loan-applications.model';

export type PartialUpdateLoanApplications = Partial<ILoanApplications> & Pick<ILoanApplications, 'id'>;

type RestOf<T extends ILoanApplications | NewLoanApplications> = Omit<T, 'mortgageDate' | 'lastModified'> & {
  mortgageDate?: string | null;
  lastModified?: string | null;
};

export type RestLoanApplications = RestOf<ILoanApplications>;

export type NewRestLoanApplications = RestOf<NewLoanApplications>;

export type PartialUpdateRestLoanApplications = RestOf<PartialUpdateLoanApplications>;

export type EntityResponseType = HttpResponse<ILoanApplications>;
export type EntityArrayResponseType = HttpResponse<ILoanApplications[]>;

@Injectable({ providedIn: 'root' })
export class LoanApplicationsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/loan-applications');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loanApplications: NewLoanApplications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanApplications);
    return this.http
      .post<RestLoanApplications>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(loanApplications: ILoanApplications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanApplications);
    return this.http
      .put<RestLoanApplications>(`${this.resourceUrl}/${this.getLoanApplicationsIdentifier(loanApplications)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(loanApplications: PartialUpdateLoanApplications): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanApplications);
    return this.http
      .patch<RestLoanApplications>(`${this.resourceUrl}/${this.getLoanApplicationsIdentifier(loanApplications)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLoanApplications>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLoanApplications[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoanApplicationsIdentifier(loanApplications: Pick<ILoanApplications, 'id'>): number {
    return loanApplications.id;
  }

  compareLoanApplications(o1: Pick<ILoanApplications, 'id'> | null, o2: Pick<ILoanApplications, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoanApplicationsIdentifier(o1) === this.getLoanApplicationsIdentifier(o2) : o1 === o2;
  }

  addLoanApplicationsToCollectionIfMissing<Type extends Pick<ILoanApplications, 'id'>>(
    loanApplicationsCollection: Type[],
    ...loanApplicationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loanApplications: Type[] = loanApplicationsToCheck.filter(isPresent);
    if (loanApplications.length > 0) {
      const loanApplicationsCollectionIdentifiers = loanApplicationsCollection.map(
        loanApplicationsItem => this.getLoanApplicationsIdentifier(loanApplicationsItem)!
      );
      const loanApplicationsToAdd = loanApplications.filter(loanApplicationsItem => {
        const loanApplicationsIdentifier = this.getLoanApplicationsIdentifier(loanApplicationsItem);
        if (loanApplicationsCollectionIdentifiers.includes(loanApplicationsIdentifier)) {
          return false;
        }
        loanApplicationsCollectionIdentifiers.push(loanApplicationsIdentifier);
        return true;
      });
      return [...loanApplicationsToAdd, ...loanApplicationsCollection];
    }
    return loanApplicationsCollection;
  }

  protected convertDateFromClient<T extends ILoanApplications | NewLoanApplications | PartialUpdateLoanApplications>(
    loanApplications: T
  ): RestOf<T> {
    return {
      ...loanApplications,
      mortgageDate: loanApplications.mortgageDate?.toJSON() ?? null,
      lastModified: loanApplications.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLoanApplications: RestLoanApplications): ILoanApplications {
    return {
      ...restLoanApplications,
      mortgageDate: restLoanApplications.mortgageDate ? dayjs(restLoanApplications.mortgageDate) : undefined,
      lastModified: restLoanApplications.lastModified ? dayjs(restLoanApplications.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLoanApplications>): HttpResponse<ILoanApplications> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLoanApplications[]>): HttpResponse<ILoanApplications[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
