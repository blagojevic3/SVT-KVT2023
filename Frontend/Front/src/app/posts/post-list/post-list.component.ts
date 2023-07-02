import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { takeUntil } from 'rxjs/operators';
import { PostService } from 'src/app/service/post.service.service';
import { CommentService } from 'src/app/service/comment.service';

interface DisplayMessage {
  msgType: string;
  msgBody: string;
}

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit, OnDestroy {
  @Input() posts: any[];
  editing = false;
  form: FormGroup;
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  notification: DisplayMessage;
  returnUrl: string;

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.getPosts();
  }

  getPosts() {
    this.postService.getPosts().subscribe(posts => {
      this.posts = posts.map(post => {
        return { ...post, commentText: '', comments: [] };
      });
    });
  }

  deletePost(postId: number) {
    console.log(postId);
    this.postService.delete(postId).subscribe(() => {
      this.getPosts();
    });
  }

  editPost(postId: number, postContent: string) {
    this.editing = true;
   
  }

  submitComment(post: any) {
    if (post.commentText.trim() !== '') {
      this.commentService.createComment(post.id, post.commentText).subscribe(() => {
        this.commentService.getComments(post.id).subscribe(comments => {
          post.comments = comments; 
          post.commentText = ''; 
        });
      });
    }
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}