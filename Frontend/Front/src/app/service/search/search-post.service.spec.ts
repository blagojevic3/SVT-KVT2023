import { TestBed } from '@angular/core/testing';
import { SearchPostService } from './search-post.service';




describe('SearchPostService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchPostService = TestBed.get(SearchPostService);
    expect(service).toBeTruthy();
  });
});
