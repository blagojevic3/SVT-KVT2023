import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ReactionService {

  constructor(
    private config: ConfigService,
    private http: HttpClient,
    private userService: UserService,
  ) {}

  reactToPost(postId: number, reactionType: string, userId: number): Observable<any> {
    const url = `${this.config.reactions_url}/post/${postId}/${userId}`;
    const reaction = {
      reactionType,
    };
    return this.http.post(url, reaction);
  }
  
  reactToComment(commentId: number, reactionType: string, userId: number): Observable<any> {
    const url = `${this.config.reactions_url}/comment/${commentId}/${userId}`;
    const reaction = {
      reactionType,
    };
    return this.http.post(url, reaction);
  }

  unreact(reactionId: number): Observable<void> {
    const url = `${this.config.reactions_url}/${reactionId}`;
    return this.http.delete<void>(url);
  }
}