import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service.service';
import { UserService } from 'src/app/service';
@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  postList: any[]
  user:any;
  constructor(
    private postService:PostService,
    private router:Router,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.getPosts()
    this.getUserInfo()
  }

  getPosts(){
    this.postService.getPosts().subscribe((posts) => (this.postList= posts))
  }
  getUserInfo() {
    this.userService.getMyInfo().subscribe((user) => {
      this.user = user;
    });
  }

}
