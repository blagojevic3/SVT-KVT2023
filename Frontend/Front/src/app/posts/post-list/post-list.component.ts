import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { takeUntil } from 'rxjs/operators';
import { PostService } from 'src/app/service/post.service.service';

interface DisplayMessage {
  msgType: string;
  msgBody: string;
}

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
  @Input() posts: any[];
  editing = false;
  form: FormGroup;
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  notification: DisplayMessage;
  returnUrl: string;

  constructor(
    private postService: PostService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {}

  deletePost(postId: number) {
    console.log(postId);
    this.postService.delete(postId).subscribe((posts) => {
      this.postService.getPosts();
    });
  }

  editPost(postId: number, postContent: string) {
    this.editing = true;
    this.route.params
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((params: DisplayMessage) => {
        this.notification = params;
      });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.form = this.formBuilder.group({
      id: postId,
      content: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
    });
    this.form.get('content').setValue(postContent);
  }

  onSubmit() {
    this.postService.edit(this.form.value).subscribe((result) => {});
  }

  likePost(postId: number) {
    const post = this.posts.find((p) => p.id === postId);
    if (post) {
      post.likes = post.likes ? post.likes + 1 : 1;
    }
  }

  dislikePost(postId: number) {
    const post = this.posts.find((p) => p.id === postId);
    if (post) {
      post.dislikes = post.dislikes ? post.dislikes + 1 : 1;
    }
  }

  heartPost(postId: number) {
    const post = this.posts.find((p) => p.id === postId);
    if (post) {
      post.hearts = post.hearts ? post.hearts + 1 : 1;
    }
  }
}