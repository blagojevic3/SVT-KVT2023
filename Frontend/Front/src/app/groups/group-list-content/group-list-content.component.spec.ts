import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListContentComponent } from './group-list-content.component';

describe('GroupListContentComponent', () => {
  let component: GroupListContentComponent;
  let fixture: ComponentFixture<GroupListContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupListContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupListContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
