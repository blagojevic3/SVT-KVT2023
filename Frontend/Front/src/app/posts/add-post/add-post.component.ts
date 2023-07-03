import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { GroupService } from "src/app/service";
import { Router } from "@angular/router";
import { PostService } from "src/app/service/post.service.service";
import { UserService } from "src/app/service";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  post = {};
  form: FormGroup;
  currentUser: any;

  constructor(
    private postService: PostService,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService:UserService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      content: [
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(64),
        ]),
      ]
    });

    this.currentUser = this.userService.getCurrentUser(); 
  }

  onSubmit() {
    console.log(this.form.value);
    this.form.value.userId = this.currentUser.id; 

    this.postService.add(this.form.value).subscribe((result) => {
      this.router.navigate(["/posts"]);
    });
  }
}