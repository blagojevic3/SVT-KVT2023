import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchGroupsElasticComponent } from './search-groups-elastic.component';

describe('SearchGroupsElasticComponent', () => {
  let component: SearchGroupsElasticComponent;
  let fixture: ComponentFixture<SearchGroupsElasticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchGroupsElasticComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchGroupsElasticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
