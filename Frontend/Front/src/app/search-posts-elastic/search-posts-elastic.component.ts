import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import PostIndex from '../model/PostIndex';
import { SearchPostService } from '../service/search/search-post.service';

@Component({
  selector: 'app-search-posts-elastic',
  templateUrl: './search-posts-elastic.component.html',
  styleUrls: ['./search-posts-elastic.component.css']
})
export class SearchPostsElasticComponent {
  searchPosts: FormGroup;
  posts: PostIndex[];

  constructor(
    private formBuilder: FormBuilder,
    private elasticPostService: SearchPostService
  ) {
    this.posts = [];
    this.searchPosts = this.formBuilder.group({
      title: [''],
      content: [''],
      simple: [false],
      advanced: [false],
      phraze: [false],
      fuzzy: [false],
      rangeSearch: [false],
      minLikesRange: [''],
      maxLikesRange: ['']
    });
  }

  onSubmit() {
    const title = this.searchPosts.get('title').value;
    const content = this.searchPosts.get('content').value;
    const simple = this.searchPosts.get('simple').value;
    const advanced = this.searchPosts.get('advanced').value;
    const phraze = this.searchPosts.get('phraze').value;
    const fuzzy = this.searchPosts.get('fuzzy').value;
    const rangeSearch = this.searchPosts.get('rangeSearch').value;
    const minLikesRange = this.searchPosts.get('minLikesRange').value;
    const maxLikesRange = this.searchPosts.get('maxLikesRange').value;
    const pageable = { page: 0, size: 10 };

    if (simple) {
      if (title) {
        this.elasticPostService.simpleSearch([title], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      } else if (content) {
        this.elasticPostService.simpleSearch([content], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      }
    } else if (advanced) {
      if (title) {
        this.elasticPostService.advancedSearch([title], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      } else if (content) {
        this.elasticPostService.advancedSearch([content], pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      }
    } else if (phraze) {
      if (title) {
        this.elasticPostService.searchByTitlePhrase(title).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      } else if (content) {
        this.elasticPostService.searchByContentPhrase(content).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      }
    } else if (fuzzy) {
      if (title) {
        this.elasticPostService.searchByTitleFuzzy(title).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      } else if (content) {
        this.elasticPostService.searchByContentFuzzy(content).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      }
    } else if (rangeSearch) {
      if (minLikesRange !== null && maxLikesRange !== null) {
        this.elasticPostService.rangeSearch(minLikesRange, maxLikesRange, pageable).subscribe({
          next: (res) => {
            console.log(res);
            this.posts = res;
          },
        });
      } else {
        console.error('Please provide both minimum and maximum number of likes for range search.');
      }
    }
  }
}
