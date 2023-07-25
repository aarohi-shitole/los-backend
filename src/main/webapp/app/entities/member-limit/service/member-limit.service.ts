import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberLimit, NewMemberLimit } from '../member-limit.model';

export type PartialUpdateMemberLimit = Partial<IMemberLimit> & Pick<IMemberLimit, 'id'>;

type RestOf<T extends IMemberLimit | NewMemberLimit> = Omit<T, 'dtLimitSanctioned' | 'dtLimitExpired' | 'lastModified'> & {
  dtLimitSanctioned?: string | null;
  dtLimitExpired?: string | null;
  lastModified?: string | null;
};

export type RestMemberLimit = RestOf<IMemberLimit>;

export type NewRestMemberLimit = RestOf<NewMemberLimit>;

export type PartialUpdateRestMemberLimit = RestOf<PartialUpdateMemberLimit>;

export type EntityResponseType = HttpResponse<IMemberLimit>;
export type EntityArrayResponseType = HttpResponse<IMemberLimit[]>;

@Injectable({ providedIn: 'root' })
export class MemberLimitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-limits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberLimit: NewMemberLimit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLimit);
    return this.http
      .post<RestMemberLimit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(memberLimit: IMemberLimit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLimit);
    return this.http
      .put<RestMemberLimit>(`${this.resourceUrl}/${this.getMemberLimitIdentifier(memberLimit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(memberLimit: PartialUpdateMemberLimit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberLimit);
    return this.http
      .patch<RestMemberLimit>(`${this.resourceUrl}/${this.getMemberLimitIdentifier(memberLimit)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMemberLimit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMemberLimit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberLimitIdentifier(memberLimit: Pick<IMemberLimit, 'id'>): number {
    return memberLimit.id;
  }

  compareMemberLimit(o1: Pick<IMemberLimit, 'id'> | null, o2: Pick<IMemberLimit, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberLimitIdentifier(o1) === this.getMemberLimitIdentifier(o2) : o1 === o2;
  }

  addMemberLimitToCollectionIfMissing<Type extends Pick<IMemberLimit, 'id'>>(
    memberLimitCollection: Type[],
    ...memberLimitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberLimits: Type[] = memberLimitsToCheck.filter(isPresent);
    if (memberLimits.length > 0) {
      const memberLimitCollectionIdentifiers = memberLimitCollection.map(
        memberLimitItem => this.getMemberLimitIdentifier(memberLimitItem)!
      );
      const memberLimitsToAdd = memberLimits.filter(memberLimitItem => {
        const memberLimitIdentifier = this.getMemberLimitIdentifier(memberLimitItem);
        if (memberLimitCollectionIdentifiers.includes(memberLimitIdentifier)) {
          return false;
        }
        memberLimitCollectionIdentifiers.push(memberLimitIdentifier);
        return true;
      });
      return [...memberLimitsToAdd, ...memberLimitCollection];
    }
    return memberLimitCollection;
  }

  protected convertDateFromClient<T extends IMemberLimit | NewMemberLimit | PartialUpdateMemberLimit>(memberLimit: T): RestOf<T> {
    return {
      ...memberLimit,
      dtLimitSanctioned: memberLimit.dtLimitSanctioned?.format(DATE_FORMAT) ?? null,
      dtLimitExpired: memberLimit.dtLimitExpired?.format(DATE_FORMAT) ?? null,
      lastModified: memberLimit.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMemberLimit: RestMemberLimit): IMemberLimit {
    return {
      ...restMemberLimit,
      dtLimitSanctioned: restMemberLimit.dtLimitSanctioned ? dayjs(restMemberLimit.dtLimitSanctioned) : undefined,
      dtLimitExpired: restMemberLimit.dtLimitExpired ? dayjs(restMemberLimit.dtLimitExpired) : undefined,
      lastModified: restMemberLimit.lastModified ? dayjs(restMemberLimit.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMemberLimit>): HttpResponse<IMemberLimit> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMemberLimit[]>): HttpResponse<IMemberLimit[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
