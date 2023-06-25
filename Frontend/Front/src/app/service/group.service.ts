import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';





@Injectable()
export class GroupService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient,
    private router: Router

  ) {
  }

  getGroups(): Observable<any[]> {
    return this.http.get<any[]>(this.config.group_url + "/all");
  }


  delete(groupId){
    return this.apiService.delete(this.config.group_url+ "/?id=" + groupId)
    .pipe(map(() => {
      console.log("Delete success");
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/groups']);}); 
    }))
  }
  add(group){
    return this.apiService.post(this.config.group_url + "/create", JSON.stringify(group))
  }
  edit(group){
    return this.apiService.put(this.config.group_url + "/edit", JSON.stringify(group))
    .pipe(map(() => {
      console.log("Edit success");
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/groups']);}); 
    }))
  }

}
