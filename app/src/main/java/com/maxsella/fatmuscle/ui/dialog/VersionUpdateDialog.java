package com.maxsella.fatmuscle.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.util.NavigationBarUtil;

public class VersionUpdateDialog extends Dialog {

    private Context mContext;
    private TextView tvNewVersion;
    private TextView tvDescription;
    private TextView tvIgnore;
    private Button btnUpdate;
    private Button btnClose;
    private ProgressBar pgbDownload;
    private boolean canIgnore;
    private String versionName;
    private int versionCode;
    private String description;

    public VersionUpdateDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        this.canIgnore = false;
        initView();
    }
    public VersionUpdateDialog(@NonNull Context context, boolean canIgnore, String versionName, int versionCode, String description) {
        super(context);
        this.mContext = context;
        this.canIgnore = canIgnore;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.description = description;
        initView();
    }
    protected VersionUpdateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void initView(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_version_update, null);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        getWindow().setBackgroundDrawableResource(R.color.transparent);

        tvNewVersion = view.findViewById(R.id.tv_new_version);
        tvDescription = view.findViewById(R.id.tv_description);
        tvIgnore = view.findViewById(R.id.tv_ignore);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnClose = view.findViewById(R.id.btn_close);
        pgbDownload = view.findViewById(R.id.pgb_download);

        tvNewVersion.setText("发现新版本V" + versionName);
        tvDescription.setText(description);
        if(!canIgnore){
            btnClose.setVisibility(View.GONE);
            tvIgnore.setVisibility(View.GONE);
        }
        tvIgnore.setOnClickListener(v -> {
            //TODO 此处保存忽略的版本
//            Config.saveIgnoreVersionCode(String.valueOf(versionCode));
            dismiss();
        });
        btnClose.setOnClickListener(v -> {
            dismiss();
        });
    }
    public void setWidth(int width) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width;
        getWindow().setAttributes(params);
    }

    public void setHeight(int height) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = height;
        getWindow().setAttributes(params);
    }
    @Override
    public void show() {
        NavigationBarUtil.focusNotAle(getWindow());
        try {
            super.show();
        } catch (Exception e){
            e.printStackTrace();
        }

        NavigationBarUtil.hideNavigationBar(getWindow());
        NavigationBarUtil.clearFocusNotAle(getWindow());
    }

    public void setOnUpdateClickListener(View.OnClickListener listener) {
        btnUpdate.setOnClickListener(listener);
    }

    public void setDownloadProgress(int progress){
        pgbDownload.setProgress(progress);
    }
}
