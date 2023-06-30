import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service.service';
@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
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

}
