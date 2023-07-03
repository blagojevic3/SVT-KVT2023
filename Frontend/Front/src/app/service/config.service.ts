import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8080/api';
  private _user_url = this._api_url + '/users';

  get user_url():string{
      return this._user_url;
  }

  private _login_url = this._user_url + '/login';

  get login_url(): string {
    return this._login_url;
  }

  private _whoami_url = this._user_url + '/whoami';

  get whoami_url(): string {
    return this._whoami_url;
  }

  private _change_pass_url = this._user_url + '/password-change';

  get change_pass_url(): string {
    return this._change_pass_url;
  }

  private _users_url = this._user_url + '/all';

  get users_url(): string {
    return this._users_url;
  }

  private _group_url = this._api_url + '/groups';

  get group_url(): string {
    return this._group_url;
  }

  private _post_url = this._api_url + '/posts';

  get post_url(): string {
    return this._post_url;
  }
  
  //TODO: implementirati :)
  private _signup_url = this._user_url + '/signup';

  get signup_url(): string {
    return this._signup_url;
  }

  private _comments_url = this._api_url + '/comments';

  get comments_url(): string {
    return this._comments_url;
  }

  private _reactions_url = this._api_url+'/reactions';

  get reactions_url(): string {
    return this._reactions_url;
  }

}
