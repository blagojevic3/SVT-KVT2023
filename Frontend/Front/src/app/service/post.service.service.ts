import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ConfigService } from './config.service';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class PostService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient,
    private router: Router
  ) {}

  getPosts(): Observable<any[]> {
    return this.http.get<any[]>(this.config.post_url + "/all");
  }

  delete(postId): Observable<void> {
    return this.apiService.delete(`${this.config.post_url}/?id=${postId}`)
      .pipe(map(() => {
        console.log("Delete success");
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/posts']);
        });
      }));
  }

  add(post): Observable<any> {
    return this.apiService.post(`${this.config.post_url}/create`, JSON.stringify(post));
  }

  edit(post): Observable<void> {
    return this.apiService.put(`${this.config.post_url}/edit`, JSON.stringify(post))
      .pipe(map(() => {
        console.log("Edit success");
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/posts']);
        });
      }));
  }
}
