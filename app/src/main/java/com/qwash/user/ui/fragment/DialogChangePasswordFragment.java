package com.qwash.user.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.account.AccountService;
import com.qwash.user.api.model.account.ChangePasswordRespone;
import com.qwash.user.ui.widget.RobotoRegularEditText;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.ProgressDialogBuilder;
import com.qwash.user.utils.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogChangePasswordFragment extends DialogFragment {


    @BindView(R.id.verification)
    RobotoRegularEditText codeV;

    private Unbinder butterKnife;
    private ProgressDialogBuilder dialogProgress;
    private String password;

    public DialogChangePasswordFragment() {

    }

    @OnClick(R.id.btn_close)
    void Close() {
        dismiss();
    }

    @OnClick(R.id.btn_change_password)
    void ChangePassword() {
        String inputStr = codeV.getText().toString();
        if (!TextUtils.isNullOrEmpty(inputStr.toString().trim())) {
            ActionRequestChangePassword(inputStr);
        } else {
            Toast.makeText(getActivity(), getString(R.string.please_input_verification_code), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterKnife.unbind();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogProgress= new ProgressDialogBuilder(getActivity());

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_change_password, container);
        butterKnife = ButterKnife.bind(this, view);
       

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }



    private void ActionRequestChangePassword(String inputStr) {
        dialogProgress.show(getString(R.string.change_password_action), getString(R.string.please_wait));

        Map<String, String> params = new HashMap<>();

        params.put(Sample.USER_ID, Prefs.getUserId(getActivity()));
        params.put(Sample.PASSWORD, getPassword());
        params.put(Sample.CODE, inputStr);

        AccountService mService = ApiUtils.AccountService(getActivity());
        mService.getChangePasswordLink("Bearer " + Prefs.getToken(getActivity()),params).enqueue(new Callback<ChangePasswordRespone>() {
            @Override
            public void onResponse(Call<ChangePasswordRespone> call, Response<ChangePasswordRespone> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getActivity(),response.body().getMessages(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                } else {
                    int statusCode = response.code();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    codeV.setText("");
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordRespone> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}