import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ConfigService } from '../config.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class SearchGroupService {

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

    return this.http.post(`${this.config.search_group_url}/simple`, { keywords }, options);
  }

  advancedSearch(keywords: string[], pageable: any): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams({ fromObject: pageable })
    };

    return this.http.post(`${this.config.search_group_url}/advanced`, { keywords }, options);
  }

  rangeSearch(min: number, max: number, pageable: any): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams({ fromObject: pageable })
    };

    return this.http.post(`${this.config.search_group_url}/range/${min}:${max}`, {}, options);
  }

  searchByNameFuzzy(name: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('name', name)
    };

    return this.http.get(`${this.config.search_group_url}/fuzzy/name`, options);
  }

  searchByDescriptionFuzzy(description: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('description', description)
    };

    return this.http.get(`${this.config.search_group_url}/fuzzy/description`, options);
  }

  searchByNamePhrase(phrase: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('phrase', phrase)
    };

    return this.http.get(`${this.config.search_group_url}/phrase/name`, options);
  }

  searchByDescriptionPhrase(phrase: string): Observable<any> {
    const options = {
      headers: this.headers,
      params: new HttpParams().set('phrase', phrase)
    };

    return this.http.get(`${this.config.search_group_url}/phrase/description`, options);
  }
  

}