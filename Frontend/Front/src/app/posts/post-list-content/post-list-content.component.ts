import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service.service';

@Component({
  selector: 'app-post-list-content',
  templateUrl: './post-list-content.component.html',
  styleUrls: ['./post-list-content.component.css']
})
export class PostListContentComponent implements OnInit {
  postList: any[]

  constructor(
    private postService:PostService,
    private router:Router
  ) { }

  ngOnInit() {
    this.getPosts()
  }

  getPosts(){
    this.postService.getPosts().subscribe((posts) => (this.postList= posts))
  }
  navigateTo(){
    this.router.navigate(['posts/create'])
  }

}
