import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { GroupListContentComponent } from './groups/group-list-content/group-list-content.component';
import { AddGroupComponent } from './groups/add-group/add-group.component';
import { PostListContentComponent } from './posts/post-list-content/post-list-content.component';
import { AddPostComponent } from './posts/add-post/add-post.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'signup',
    component: SignUpComponent,
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent
  },
  {
    path: 'groups',
    component: GroupListContentComponent
  },
  {
    path: 'groups/create',
    component: AddGroupComponent
  },
  {
    path: 'posts',
    component: PostListContentComponent
  },
  {
    path: 'posts/create',
    component: AddPostComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
