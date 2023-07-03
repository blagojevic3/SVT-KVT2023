import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser;

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url)
      .pipe(map(user => {
        this.currentUser = user;
        return user;
      }));
  }

  getAll() {
    return this.apiService.get(this.config.users_url);
  }
  edit(user): Observable<any> {
    return this.http.put<any>(`${this.config.user_url}/edit`, user);
  }

  getCurrentUser() {
    return this.currentUser;
  }


}
