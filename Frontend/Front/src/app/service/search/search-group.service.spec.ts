import { TestBed } from '@angular/core/testing';
import { SearchGroupService } from './search-group.service';



describe('SearchGroupService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchGroupService = TestBed.get(SearchGroupService);
    expect(service).toBeTruthy();
  });
});
