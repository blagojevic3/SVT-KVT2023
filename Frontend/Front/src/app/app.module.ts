import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CardComponent } from './card/card.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { UserMenuComponent } from './user-menu/user-menu.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';

import {AngularMaterialModule} from './angular-material/angular-material.module';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ApiService} from './service/api.service';
import {AuthService} from './service/auth.service';
import {UserService} from './service/user.service';
import {ConfigService} from './service/config.service';
import {GroupService} from './service/group.service';
import { PostService } from './service/post.service.service';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptor/TokenInterceptor';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { GroupListComponent } from './groups/group-list/group-list.component';
import { GroupListContentComponent } from './groups/group-list-content/group-list-content.component';
import { AddGroupComponent } from './groups/add-group/add-group.component';
import { AddPostComponent } from './posts/add-post/add-post.component';
import { PostListComponent } from './posts/post-list/post-list.component';
import { PostListContentComponent } from './posts/post-list-content/post-list-content.component';

@NgModule({
  declarations: [
    AppComponent,
    CardComponent,
    HomeComponent,
    HeaderComponent,
    UserMenuComponent,
    LoginComponent,
    SignUpComponent,
    ChangePasswordComponent,
    GroupListComponent,
    GroupListContentComponent,
    AddGroupComponent,
    AddPostComponent,
    PostListComponent,
    PostListContentComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NoopAnimationsModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [ 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    GroupService,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    PostService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
