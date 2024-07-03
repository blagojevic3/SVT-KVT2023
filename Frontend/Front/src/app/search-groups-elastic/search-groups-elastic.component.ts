import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GroupIndex } from 'src/app/model/GroupIndex';
import { SearchGroupService } from '../service/search/search-group.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-search-groups-elastic',
  templateUrl: './search-groups-elastic.component.html',
  styleUrls: ['./search-groups-elastic.component.css']
})
export class SearchGroupsElasticComponent {
  searchGroups: FormGroup;
  groups: GroupIndex[];
  constructor(
    private formBuilder: FormBuilder,
    private elasticGroupService: SearchGroupService,
    private cdr: ChangeDetectorRef
  ) {
    this.groups = [];
    this.searchGroups = this.formBuilder.group({
      name: [''],
      description: [''],
      simple: [false],
      advanced: [false],
      phraze: [false],
      fuzzy: [false],
      rangeSearch: [false],
      minPostsRange: [''],
      maxPostsRange: ['']
    });
  }

  onSubmit() {
    const name = this.searchGroups.get('name').value;
    const description = this.searchGroups.get('description').value;
    const simple = this.searchGroups.get('simple').value;
    const advanced = this.searchGroups.get('advanced').value;
    const phraze = this.searchGroups.get('phraze').value;
    const fuzzy = this.searchGroups.get('fuzzy').value;
    const rangeSearch = this.searchGroups.get('rangeSearch').value;
    const minPostsRange = this.searchGroups.get('minPostsRange').value;
    const maxPostsRange = this.searchGroups.get('maxPostsRange').value;
    const pageable = { page: 0, size: 10 };

    if (simple) {
      if (name) {
        this.elasticGroupService.simpleSearch([name], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      } else if (description) {
        this.elasticGroupService.simpleSearch([description], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      }
    } else if (advanced) {
      if (name) {
        this.elasticGroupService.advancedSearch([name], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      } else if (description) {
        this.elasticGroupService.advancedSearch([description], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      }
    } else if (phraze) {
      if (name) {
        this.elasticGroupService.searchByNamePhrase(name).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      } else if (description) {
        this.elasticGroupService.searchByDescriptionPhrase(description).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      }
    } else if (fuzzy) {
      if (name) {
        this.elasticGroupService.searchByNameFuzzy(name).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      } else if (description) {
        this.elasticGroupService.searchByDescriptionFuzzy(description).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      }
    } else if (rangeSearch) {
      if (minPostsRange !== null && maxPostsRange !== null) {
        this.elasticGroupService.rangeSearch(minPostsRange, maxPostsRange, pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.groups = res;
            this.cdr.detectChanges(); // Trigger change detection
          },
        });
      } else {
        console.error('Please provide both minimum and maximum number of likes for range search.');
      }
    }
  
  }
}
