import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  user: any = {};

  constructor(private userService: UserService) {}

  ngOnInit() {
    // Load the current user's information
    this.userService.getMyInfo().subscribe((user: any) => {
      this.user = user;
    });
  }

  // saveChanges() {
  //   // Update the current user's information
  //   this.userService.edit(this.user).subscribe((updatedUser: any) => {
  //     // Handle the response as needed
  //   });
  // }

  saveChanges() {
    this.userService.edit(this.user).subscribe(
      (updatedUser: any) => {
        // Handle the response as needed
        console.log("User updated:", updatedUser);
      },
      (error: any) => {
        // Handle the error if the update fails
        console.error("Failed to update user:", error);
      }
    );
  }
}