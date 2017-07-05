package android2.levelup.ru.loginscreennormal;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static android.R.attr.editable;
import static io.reactivex.Observable.create;

public class MainActivity extends AppCompatActivity {

    EditText etLogin, etPassword;
    Button button;
    String login;
    String password;
    TextWatcher watcher;

    boolean loginIsEmpty;
    boolean passwordIsEmpty;
    final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = (EditText) findViewById(R.id.et_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        button = (Button) findViewById(R.id.button);
        findViewById(R.id.button).setOnClickListener(v -> showToast());

//        RxView.clicks(findViewById(R.id.button)).subscribe(o -> showToast());

        Observable.combineLatest(
                RxTextView.textChanges(etLogin)
                        .map(text -> text.toString().trim())
                .doOnNext(text -> Log.d(TAG, text)),
                RxTextView.textChanges(etPassword)
                        .map(text -> text.toString().trim()),
                (login, password) -> !login.isEmpty() && !password.isEmpty()
        ).subscribe(button::setEnabled,
                throwable -> Log.w(TAG, throwable));

//        Observable.just(0, 1, 2, 3, 4, 5, 6)
//                .observeOn(Schedulers.computation())
//                .map(integer -> integer * 10)
//                .filter(integer -> integer != 0)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        integer -> Log.d(TAG, "new value " + integer),
//                        throwable -> Log.w(TAG, throwable));




        button.setEnabled(false);

        button.setOnClickListener(v -> showToast());
//        watcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                button.setEnabled(false);
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if(etLogin.getText().toString().equals(" ")) etLogin.setText("");
//                if(etPassword.getText().toString().equals(" ")) etPassword.setText("");
//
//                if(etLogin.getText().toString().trim().isEmpty() || etPassword.getText().toString().isEmpty()){
//                    button.setEnabled(false);
//                }else {
//                    button.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };
//
//        etLogin.addTextChangedListener(watcher);
//        etPassword.addTextChangedListener(watcher);







//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(MainActivity.this, "Click!", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void showToast() {
        Toast.makeText(this, "Hello, lambda!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Привет!");
    }
}
