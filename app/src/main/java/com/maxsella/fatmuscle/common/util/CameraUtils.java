package com.maxsella.fatmuscle.common.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {

    public static Intent getTakePhotoIntent(Context context, File outputImagePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSdcard()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Uri uri = Uri.fromFile(outputImagePath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, outputImagePath.getAbsolutePath());
                Uri uri = context.getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        return intent;
    }

    public static Intent getSelectPhotoIntent() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        return intent;
    }

    private static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImageOnKitKatPath(Intent data, Context context) {
        String imagePath = null;
        Uri uri = data.getData();
        LogUtil.d("uri: "+uri);
        if (uri != null) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String authority = uri.getAuthority();
                LogUtil.d("docId: " + docId);
                if ("com.android.providers.media.documents".equals(authority)) {
                    String id = docId.split(":")[1];
                    Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{id};
                    imagePath = getImagePath(contentUri, selection, selectionArgs, context);
                    if (imagePath==null){
                        imagePath = getImagePathFromUri(context,uri);
                        LogUtil.d("13: " + imagePath);
                    }
                    LogUtil.d("imagePath1: " + imagePath);
                } else if ("com.android.providers.downloads.documents".equals(authority)) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                    imagePath = getImagePath(contentUri, null, null, context);
                    LogUtil.d("imagePath2: " + imagePath);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null, null, context);
                LogUtil.d("imagePath3: " + imagePath);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
                LogUtil.d("imagePath4: " + imagePath);
            }else {
                imagePath = getFilePathFromContentUri(uri,context);
                LogUtil.d("image1111: " + imagePath);
            }
        }
        LogUtil.d("imagePath: " + imagePath);
        return imagePath;
    }
    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     *      /storage/emulated/0/Pictures/1698218969798.jpg
     *      content://com.android.providers.media.documents/document/image%3A1000000650
     * @param context
     * @return
     * @throws IOException
     */

    private static File createTempFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private static String getImagePathFromUri(Context context, Uri uri) {
        String filePath = null;
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (columnIndex > -1) {
                    filePath = cursor.getString(columnIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @SuppressLint("Range")
    private static String getImagePath(Uri uri, String selection, String[] selectionArgs, Context context) {
        String path = null;
        try (Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex != -1) {
                    path = cursor.getString(columnIndex);
                } else {
                    // 如果获取不到路径，尝试从文件描述符中获取
                    try (ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r")) {
                        if (pfd != null) {
                            FileDescriptor fd = pfd.getFileDescriptor();
                            FileInputStream fis = new FileInputStream(fd);
                            path = context.getCacheDir() + File.separator + System.currentTimeMillis(); // 自定义路径
                            FileOutputStream fos = new FileOutputStream(path);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                fos.write(buffer, 0, length);
                            }
                            fis.close();
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return path;
    }


    public static void imgUpdateDirection(String filepath, Bitmap orc_bitmap, ImageView iv) {
        int digree = 0;
        try {
            ExifInterface exif = new ExifInterface(filepath);
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
            }
            if (digree != 0) {
                Matrix m = new Matrix();
                m.postRotate(digree);
                Bitmap rotatedBitmap = Bitmap.createBitmap(orc_bitmap, 0, 0, orc_bitmap.getWidth(), orc_bitmap.getHeight(), m, true);
                iv.setImageBitmap(rotatedBitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap compression(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        if (outputStream.toByteArray().length / 1024 > 1024) {
            outputStream.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        options.inJustDecodeBounds = false;
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        float height = 800f;
        float width = 480f;
        int zoomRatio = 1;
        if (outWidth > outHeight && outWidth > width) {
            zoomRatio = (int) (options.outWidth / width);
        } else if (outWidth < outHeight && outHeight > height) {
            zoomRatio = (int) (options.outHeight / height);
        }
        if (zoomRatio <= 0) {
            zoomRatio = 1;
        }
        options.inSampleSize = zoomRatio;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        return bitmap;
    }
}
