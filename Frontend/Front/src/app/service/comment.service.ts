import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  createComment(postId: number, commentText: string) {
    const comment = { text: commentText };
    return this.apiService.post(`${this.config.comments_url}/${postId}/create`, JSON.stringify(comment));
  }

  deleteComment(commentId: number) {
    return this.apiService.delete(`${this.config.comments_url}/${commentId}`);
  }



  editComment(commentId: number, editedComment: any) {
    return this.apiService.put(`${this.config.comments_url}/${commentId}`, JSON.stringify(editedComment));
  }

  replyToComment(commentId: number, commentText: string) {
    const comment = { text: commentText };
    return this.apiService.post(`${this.config.comments_url}/${commentId}/reply`, JSON.stringify(comment));
  }

  getReplies(commentId: number) {
    return this.apiService.get(`${this.config.comments_url}/${commentId}/replies`);
  }

  getComments(postId: number) {
    return this.apiService.get(`${this.config.comments_url}/${postId}/all`);
  }
}