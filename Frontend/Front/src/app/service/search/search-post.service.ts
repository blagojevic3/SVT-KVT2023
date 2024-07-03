import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ConfigService } from '../config.service';
import { Observable } from 'rxjs'


@Injectable({
  providedIn: 'root'
})
export class SearchPostService {

  private headers = new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  });

  constructor(
    private http: HttpClient,
    private config: ConfigService
  ) { }



  simpleSearch(keywords: string[], pageable: any): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams({ fromObject: pageable })
    };

    return this.http.post(`${this.config.search_post_url}/simple`, { keywords }, options);
  }

  advancedSearch(keywords: string[], pageable: any): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams({ fromObject: pageable })
    };

    return this.http.post(`${this.config.search_post_url}/advanced`, { keywords }, options);
  }

  rangeSearch(min: number, max: number, pageable: any): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams({ fromObject: pageable })
    };

    return this.http.post(`${this.config.search_post_url}/range/${min}:${max}`, {}, options);
  }



  searchByTitleFuzzy(title: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('title', title)
    };

    return this.http.get(`${this.config.search_post_url}/fuzzy/title`, options);
  }

  searchByContentFuzzy(content: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('content', content)
    };

    return this.http.get(`${this.config.search_post_url}/fuzzy/content`, options);
  }

  searchByTitlePhrase(phrase: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('phrase', phrase)
    };

    return this.http.get(`${this.config.search_post_url}/phrase/title`, options);
  }

  searchByContentPhrase(phrase: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('phrase', phrase)
    };

    return this.http.get(`${this.config.search_post_url}/phrase/content`, options);
  }
}