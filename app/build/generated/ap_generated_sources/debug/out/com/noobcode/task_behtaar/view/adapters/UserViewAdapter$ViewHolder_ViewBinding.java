// Generated code from Butter Knife. Do not modify!
package com.noobcode.task_behtaar.view.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.noobcode.task_behtaar.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private UserViewAdapter.ViewHolder target;

  @UiThread
  public UserViewAdapter$ViewHolder_ViewBinding(UserViewAdapter.ViewHolder target, View source) {
    this.target = target;

    target.userId = Utils.findRequiredViewAsType(source, R.id.userid, "field 'userId'", TextView.class);
    target.id = Utils.findRequiredViewAsType(source, R.id.id, "field 'id'", TextView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.body = Utils.findRequiredViewAsType(source, R.id.body, "field 'body'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userId = null;
    target.id = null;
    target.title = null;
    target.body = null;
  }
}
