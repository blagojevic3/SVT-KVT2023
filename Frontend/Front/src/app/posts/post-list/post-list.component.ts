import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { takeUntil } from 'rxjs/operators';
import { PostService } from 'src/app/service/post.service.service';
import { CommentService } from 'src/app/service/comment.service';
import { ReactionService } from 'src/app/service/reaction.service';
import { UserService } from "src/app/service";

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
  currentUser: any;

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private reactionService: ReactionService,
    private userService: UserService,
  ) {}

  ngOnInit() {
    this.getPosts();
    this.currentUser = this.userService.getCurrentUser();
  }

  getPosts() {
    this.postService.getPosts().subscribe(posts => {
      this.posts = posts.map(post => {
        return {
          ...post,
          commentText: '',
          comments: [], // Initialize comments to an empty array
          likes: post.likes || 0,
          dislikes: post.dislikes || 0,
          hearts: post.hearts || 0
        };
      });
  
      // Retrieve comments for each post
      this.posts.forEach(post => {
        this.commentService.getComments(post.id).subscribe(comments => {
          post.comments = comments;
        });
      });
    });
  }

  deletePost(postId: number) {
    console.log(postId);
    this.postService.delete(postId).subscribe(() => {
      this.getPosts();
    });
  }

  editPost(postId, postContent) {
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
    this.form.get("content").setValue(postContent);
  }

  submitComment(post: any) {
    if (post.commentText.trim() !== '') {
      this.commentService.createComment(post.id, post.commentText).subscribe(comment => {
        post.comments.push(comment);
        post.commentText = '';
      });
    }
  }

  likePost(postId: number) {
    const userId = this.userService.getCurrentUser().id; // Get the user's ID
    this.reactionService.reactToPost(postId, 'LIKE', userId).subscribe(() => {
      const post = this.posts.find(p => p.id === postId);
      if (post) {
        post.likes += 1;
      }
    });
  }
  
  dislikePost(postId: number) {
    const userId = this.userService.getCurrentUser().id; // Get the user's ID
    this.reactionService.reactToPost(postId, 'DISLIKE', userId).subscribe(() => {
      const post = this.posts.find(p => p.id === postId);
      if (post) {
        post.dislikes += 1;
      }
    });
  }
  
  heartPost(postId: number) {
    const userId = this.userService.getCurrentUser().id; // Get the user's ID
    this.reactionService.reactToPost(postId, 'HEART', userId).subscribe(() => {
      const post = this.posts.find(p => p.id === postId);
      if (post) {
        post.hearts += 1;
      }
    });
  }
  
  unreact(reactionId: number) {
    this.reactionService.unreact(reactionId).subscribe(() => {
      // Handle success or update the UI if needed
    });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  onSubmit() {
    this.postService.edit(this.form.value).subscribe((result) => {
    });
  }
}