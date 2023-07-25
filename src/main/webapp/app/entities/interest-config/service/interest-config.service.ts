import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterestConfig, NewInterestConfig } from '../interest-config.model';

export type PartialUpdateInterestConfig = Partial<IInterestConfig> & Pick<IInterestConfig, 'id'>;

type RestOf<T extends IInterestConfig | NewInterestConfig> = Omit<T, 'startDate' | 'endDate' | 'lastModified'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
};

export type RestInterestConfig = RestOf<IInterestConfig>;

export type NewRestInterestConfig = RestOf<NewInterestConfig>;

export type PartialUpdateRestInterestConfig = RestOf<PartialUpdateInterestConfig>;

export type EntityResponseType = HttpResponse<IInterestConfig>;
export type EntityArrayResponseType = HttpResponse<IInterestConfig[]>;

@Injectable({ providedIn: 'root' })
export class InterestConfigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interest-configs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(interestConfig: NewInterestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interestConfig);
    return this.http
      .post<RestInterestConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(interestConfig: IInterestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interestConfig);
    return this.http
      .put<RestInterestConfig>(`${this.resourceUrl}/${this.getInterestConfigIdentifier(interestConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(interestConfig: PartialUpdateInterestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interestConfig);
    return this.http
      .patch<RestInterestConfig>(`${this.resourceUrl}/${this.getInterestConfigIdentifier(interestConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestInterestConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInterestConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterestConfigIdentifier(interestConfig: Pick<IInterestConfig, 'id'>): number {
    return interestConfig.id;
  }

  compareInterestConfig(o1: Pick<IInterestConfig, 'id'> | null, o2: Pick<IInterestConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterestConfigIdentifier(o1) === this.getInterestConfigIdentifier(o2) : o1 === o2;
  }

  addInterestConfigToCollectionIfMissing<Type extends Pick<IInterestConfig, 'id'>>(
    interestConfigCollection: Type[],
    ...interestConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interestConfigs: Type[] = interestConfigsToCheck.filter(isPresent);
    if (interestConfigs.length > 0) {
      const interestConfigCollectionIdentifiers = interestConfigCollection.map(
        interestConfigItem => this.getInterestConfigIdentifier(interestConfigItem)!
      );
      const interestConfigsToAdd = interestConfigs.filter(interestConfigItem => {
        const interestConfigIdentifier = this.getInterestConfigIdentifier(interestConfigItem);
        if (interestConfigCollectionIdentifiers.includes(interestConfigIdentifier)) {
          return false;
        }
        interestConfigCollectionIdentifiers.push(interestConfigIdentifier);
        return true;
      });
      return [...interestConfigsToAdd, ...interestConfigCollection];
    }
    return interestConfigCollection;
  }

  protected convertDateFromClient<T extends IInterestConfig | NewInterestConfig | PartialUpdateInterestConfig>(
    interestConfig: T
  ): RestOf<T> {
    return {
      ...interestConfig,
      startDate: interestConfig.startDate?.toJSON() ?? null,
      endDate: interestConfig.endDate?.toJSON() ?? null,
      lastModified: interestConfig.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restInterestConfig: RestInterestConfig): IInterestConfig {
    return {
      ...restInterestConfig,
      startDate: restInterestConfig.startDate ? dayjs(restInterestConfig.startDate) : undefined,
      endDate: restInterestConfig.endDate ? dayjs(restInterestConfig.endDate) : undefined,
      lastModified: restInterestConfig.lastModified ? dayjs(restInterestConfig.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInterestConfig>): HttpResponse<IInterestConfig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestInterestConfig[]>): HttpResponse<IInterestConfig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
