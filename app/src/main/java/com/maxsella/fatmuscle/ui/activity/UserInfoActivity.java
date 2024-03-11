package com.maxsella.fatmuscle.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopupext.listener.CommonPickerListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.CommonPickerPopup;
import com.lxj.xpopupext.popup.TimePickerPopup;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.databinding.ActivityUserInfoBinding;
import com.maxsella.fatmuscle.databinding.DialogModifyHeadBinding;
import com.maxsella.fatmuscle.databinding.FragmentProfileBinding;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.CameraUtils;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.common.util.PermissionUtils;
import com.maxsella.fatmuscle.db.bean.User;
import com.maxsella.fatmuscle.ui.fragment.ProfileFragment;
import com.maxsella.fatmuscle.viewmodel.UserInfoViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class UserInfoActivity extends BaseActivity {

    private ActivityUserInfoBinding userInfoBinding;

    private UserInfoViewModel userInfoViewModel = new UserInfoViewModel();

    private static User user1;
    private AlertDialog dialog;
    //用于保存拍照图片的uri
    private Uri mCameraUri;
    /**
     * 打开相册请求码
     */
    protected static int SELECT_PHOTO_CODE = 2000;

    /**
     * 打开相机请求码
     */
    protected static int TAKE_PHOTO_CODE = 2001;
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 123;
    /**
     * 页面权限请求 结果启动器
     */
    private ActivityResultLauncher<String[]> permissionActivityResultLauncher;

    /**
     * 拍照活动结果启动器
     */
    private ActivityResultLauncher<Uri> takePictureActivityResultLauncher;
    /**
     * 常规使用 通过意图进行跳转
     */
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void initView() {
        userInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        userInfoViewModel.setLifecycleOwner(this);
        userInfoViewModel.user.observe(this, user -> {
            user1 = user;
            LogUtil.d("user: " + user);
            userInfoBinding.setViewModel(userInfoViewModel);
        });
        register();
        initClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void register() {
        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // 从外部存储管理页面返回
                        if (!isStorageManager()) {
                            showMsg("未打开外部存储管理开关，无法打开相册，抱歉");
                            return;
                        }
                        if (!hasPermission(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                            permissionActivityResultLauncher.launch(new String[]{
                                    PermissionUtils.READ_EXTERNAL_STORAGE}
                            );
                            return;
                        }
                        // 打开相册
                        openAlbum();
                    }
                }
        );

        takePictureActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        if (mCameraUri != null) {
                            LogUtil.d("mCameraUri 不为空");
                            modifyAvatar(mCameraUri.toString());
                        }
                    }
                }
        );

        permissionActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                (Map<String, Boolean> result) -> {
                    if (result.containsKey(PermissionUtils.CAMERA)) {
                        // 相机权限
                        if (!result.get(PermissionUtils.CAMERA)) {
                            showMsg("您拒绝了相机权限，无法打开相机，抱歉。");
                            return;
                        }
                        takePicture();
                    } else if (result.containsKey(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                        // 文件读写权限
                        if (!result.get(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                            showMsg("您拒绝了读写文件权限，无法打开相册，抱歉。");
                            return;
                        }
                        openAlbum();
                    }
                }
        );

        // 请求写外部存储权限
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 修改头像
     *
     * @param imagePath
     */
    private void modifyAvatar(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            // 保存到数据表中
            LogUtil.d("modifyAvatar: " + imagePath);
            user1.setHead_img(imagePath);
            userInfoViewModel.updateUser(user1);
        } else {
            showMsg("图片获取失败");
        }
    }

    private void initClick() {
        userInfoBinding.ivHead.setOnClickListener(v -> {
            showModifyHeadDialog();
        });
        userInfoBinding.lltNickname.setOnClickListener(v -> {
            navTo(EditNicknameActivity.class);
        });
        userInfoBinding.lltBirth.setOnClickListener(v -> {
            initDatePicker(this, user1.getBirth());
        });
        userInfoBinding.lltSex.setOnClickListener(v -> {
            initSexPicker(this, user1.getSex());
        });
        userInfoBinding.lltHeight.setOnClickListener(v -> {
            initHeightPicker(this, user1.getHeight());
        });
        userInfoBinding.lltWeight.setOnClickListener(v -> {
            initWeightPicker(this, user1.getWeight());
        });
    }


    private void showModifyHeadDialog() {
        DialogModifyHeadBinding binding = DialogModifyHeadBinding.inflate(LayoutInflater.from(this));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        binding.tvCameraPhoto.setOnClickListener(v -> {
            cameraPhoto();
        });
        binding.tvAlbumSelection.setOnClickListener(v -> {
            albumSelection();
        });
        binding.tvClose.setOnClickListener(v -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        builder.setView(binding.getRoot());
        dialog = builder.create();
        dialog.show();
    }


    /**
     * 相册拍照
     */
    private void cameraPhoto() {
        if (dialog != null) {
            dialog.dismiss();
        }
        LogUtil.d("cameraPhoto: 相册拍照");
        if (!isAndroid6()) {
            //打开相机
            LogUtil.d("cameraPhoto: 打开相机");
            takePicture();
            return;
        }
        if (!hasPermission(PermissionUtils.CAMERA)) {
            //请求相机权限
            LogUtil.d("cameraPhoto: 请求相机权限");
            permissionActivityResultLauncher.launch(new String[]{PermissionUtils.CAMERA});
            return;
        }
        //打开相机
        takePicture();
    }

    /**
     * 新的拍照
     */
    private void takePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10及以上版本
            LogUtil.d("takePicture: Android 10及以上版本");
            mCameraUri = getContentResolver().insert(
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                    new ContentValues()
            );
        } else {
            // Android 9及以下版本
            LogUtil.d("takePicture: Android 9及以下版本");
            mCameraUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        }
        LogUtil.d("takePicture");
        takePictureActivityResultLauncher.launch(mCameraUri);
    }

    private void albumSelection() {
        if (dialog != null) {
            dialog.dismiss();
        }
        LogUtil.d("android 11");
        if (isAndroid11()) {
            // 请求打开外部存储管理
//             requestManageExternalStorage();
            openAlbum();
        } else {
            if (!isAndroid6()) {
                // 打开相册
                openAlbum();
                LogUtil.d("非android 6: 相册打开请求");
                return;
            }
            if (!hasPermission(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                // 请求文件存储权限
                LogUtil.d("android: 请求文件存储权限");
                permissionActivityResultLauncher.launch(new String[]{PermissionUtils.READ_EXTERNAL_STORAGE});
            } else {
                // 已有权限，可以打开相册
                LogUtil.d("android : 已有权限");
                openAlbum();
            }
        }
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 如果运行在Android 10及更高版本
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PHOTO_CODE);
        } else {
            // Android 9及以下版本
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_PHOTO_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            showMsg("未知原因");
            return;
        }
        if (requestCode == PermissionUtils.REQUEST_MANAGE_EXTERNAL_STORAGE_CODE) {
            // 从外部存储管理页面返回
            if (!isStorageManager()) {
                showMsg("未打开外部存储管理开关，无法打开相册，抱歉");
                return;
            }
            if (!hasPermission(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                requestPermission(PermissionUtils.READ_EXTERNAL_STORAGE);
                return;
            }
            // 打开相册
            openAlbum();
        } else if (requestCode == SELECT_PHOTO_CODE) {
            //相册中选择图片返回
            if (data != null) {
                String imagePath;
                imagePath = CameraUtils.getImageOnKitKatPath(data, this);
                LogUtil.d("img1" + imagePath);
                if (imagePath != null) {
                    LogUtil.d(imagePath);
                    modifyAvatar(imagePath);
                }
            }
        }
    }

    private void initSexPicker(Context context, String sex) {
        if (sex == null) {
            sex = "男";
        }
        int currentItem = sex.equals("男") ? 0 : 1;
        CommonPickerPopup popup = new CommonPickerPopup(context);
        ArrayList<String> list = new ArrayList<String>();
        list.add("男");
        list.add("女");
        popup.setPickerData(list)
                .setCurrentItem(currentItem);
        popup.setCommonPickerListener(new CommonPickerListener() {
            @Override
            public void onItemSelected(int index, String data) {
                LogUtil.d("选中的是 " + data);
                user1.setSex(data);
                userInfoViewModel.updateUser(user1);
            }

            @Override
            public void onCancel() {

            }
        });
        new XPopup.Builder(context)
                .asCustom(popup)
                .show();
    }

    private void initHeightPicker(Context context, String height) {
        int high = 120;
        if (height != null) {
            high = Integer.parseInt(height.replace("cm", ""));
        }
        int currentItem = 120;

        //50-250
        CommonPickerPopup popup = new CommonPickerPopup(context);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 50; i < 250; i++) {
            if (i == high) {
                currentItem = i;
            }
            list.add(i + "cm");
        }
        popup.setPickerData(list)
                .setCurrentItem(currentItem);
        popup.setCommonPickerListener(new CommonPickerListener() {
            @Override
            public void onItemSelected(int index, String data) {
                LogUtil.d("选中的是 " + data);
                user1.setHeight(data);
                userInfoViewModel.updateUser(user1);
            }

            @Override
            public void onCancel() {

            }
        });
        new XPopup.Builder(context)
                .asCustom(popup)
                .show();
    }

    private void initWeightPicker(Context context, String weight) {
        int wei = 110;
        if (weight != null) {
            wei = Integer.parseInt(weight.replace("kg", ""));
        }
        int currentItem = 110;
        //20-160
        CommonPickerPopup popup = new CommonPickerPopup(context);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 20; i < 160; i++) {
            if (i == wei) {
                currentItem = i;
            }
            list.add(i + "kg");
        }
        popup.setPickerData(list)
                .setCurrentItem(currentItem);
        popup.setCommonPickerListener(new CommonPickerListener() {
            @Override
            public void onItemSelected(int index, String data) {
                LogUtil.d("选中的是 " + data);
                user1.setWeight(data);
                userInfoViewModel.updateUser(user1);
            }

            @Override
            public void onCancel() {

            }
        });
        new XPopup.Builder(context)
                .asCustom(popup)
                .show();
    }

    private void initDatePicker(Context context, String date1) {

        Calendar date = Calendar.getInstance();
        if (date1 == null) {
            date.set(2020, 5, 12);
        } else {
            String[] split = date1.split("/");
            date.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
//        Calendar date2 = Calendar.getInstance();
//        date2.set(2020, 5, 1);
        TimePickerPopup popup = new TimePickerPopup(context)
                .setDefaultDate(date)  //设置默认选中日期
//                        .setYearRange(1990, 1999) //设置年份范围
//                        .setDateRange(date, date2) //设置日期范围
                .setTimePickerListener(new TimePickerListener() {
                    @Override
                    public void onTimeChanged(Date date) {
                        //时间改变
                    }

                    @Override
                    public void onTimeConfirm(Date date, View view) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        //点击确认时间
                        String birth = formatter.format(date);
                        LogUtil.d("选择的时间：" + birth);
                        user1.setBirth(birth);
                        userInfoViewModel.updateUser(user1);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

        new XPopup.Builder(context)
                .asCustom(popup)
                .show();
    }
}