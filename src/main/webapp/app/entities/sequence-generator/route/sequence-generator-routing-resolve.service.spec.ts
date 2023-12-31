import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISequenceGenerator } from '../sequence-generator.model';
import { SequenceGeneratorService } from '../service/sequence-generator.service';

import { SequenceGeneratorRoutingResolveService } from './sequence-generator-routing-resolve.service';

describe('SequenceGenerator routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SequenceGeneratorRoutingResolveService;
  let service: SequenceGeneratorService;
  let resultSequenceGenerator: ISequenceGenerator | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SequenceGeneratorRoutingResolveService);
    service = TestBed.inject(SequenceGeneratorService);
    resultSequenceGenerator = undefined;
  });

  describe('resolve', () => {
    it('should return ISequenceGenerator returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenceGenerator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSequenceGenerator).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenceGenerator = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSequenceGenerator).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISequenceGenerator>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenceGenerator = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSequenceGenerator).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
