package com.example.nanchen.aiyaschoolpush.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.nanchen.aiyaschoolpush.CropOption;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.adapter.CommonAdapter;
import com.example.nanchen.aiyaschoolpush.adapter.ViewHolder;
import com.example.nanchen.aiyaschoolpush.utils.UIUtil;
import com.example.nanchen.aiyaschoolpush.view.LinearLayoutListItemView;
import com.example.nanchen.aiyaschoolpush.view.OnLinearLayoutListItemClickListener;
import com.example.nanchen.aiyaschoolpush.view.SelectDialog;
import com.example.nanchen.aiyaschoolpush.view.SelectDialog.SelectDialogListener;
import com.example.nanchen.aiyaschoolpush.view.TitleView;
import com.example.nanchen.aiyaschoolpush.view.UserInfoView;
import com.example.nanchen.aiyaschoolpush.view.UserInfoView.OnItemClickListener;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * 我的宝贝
 */
public class ChildInfoActivity extends ActivityBase {

    private TitleView mTitleBar;
    private LinearLayoutListItemView mItemName;
    private UserInfoView mInfoImage;
    private LinearLayoutListItemView mItemClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info);

        bindView();
    }

    private void bindView() {
        mTitleBar = (TitleView) findViewById(R.id.child_info_title);
        mInfoImage = (UserInfoView) findViewById(R.id.child_info_image);
        mItemName = (LinearLayoutListItemView) findViewById(R.id.child_info_name);
        mItemClass = (LinearLayoutListItemView) findViewById(R.id.child_info_class);

        mTitleBar.setTitle("我的宝贝");
        mTitleBar.setLeftButtonAsFinish(this);

        mInfoImage.setHeight(60);
        mInfoImage.setHeadImageSize(50);
        mInfoImage.setBackgroundImage(R.drawable.background_linearlayout_listitem);
        mInfoImage.setUserNameText("孩子头像");

        mInfoImage.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                showCrouton("你点击了孩子头像");
                selectPhoto();
            }
        });

        mItemName.setRightText("测试者孩子");
        mItemClass.setRightText("成都七中");

        mItemName.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                View view = LayoutInflater.from(ChildInfoActivity.this).inflate(R.layout.view_dialog_edit,null);
                final EditText mEditText = (EditText) view.findViewById(R.id.view_dialog_edit);
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ChildInfoActivity.this);
                dialogBuilder.withTitle("设置孩子名字")                                  //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")                                  //def
                        .withMessage(null)
                        .withDividerColor("#11000000")                              //def
                        .withDialogColor(getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
                        .withIcon(getResources().getDrawable(R.mipmap.icon))
                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                        .withDuration(700)                                          //def
                        .withEffect(Effectstype.Shake)                                         //def Effectstype.Slidetop
                        .withButton1Text("确定")                                      //def gone
                        .withButton2Text("取消")                                  //def gone
                        .setCustomView(view,mItemName.getContext())         //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showCrouton("保存成功");
                                mItemName.setRightText(mEditText.getText().toString().trim());
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

        mItemClass.setOnLinearLayoutListItemClickListener(new OnLinearLayoutListItemClickListener() {
            @Override
            public void onLinearLayoutListItemClick(Object object) {
                View view = LayoutInflater.from(ChildInfoActivity.this).inflate(R.layout.view_dialog_edit,null);
                final EditText mEditText = (EditText) view.findViewById(R.id.view_dialog_edit);
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ChildInfoActivity.this);
                dialogBuilder.withTitle("设置孩子班级")                                  //.withTitle(null)  no title
                        .withMessage(null)
                        .withDialogColor(getResources().getColor(R.color.main_bg_color1))                               //def  | withDialogColor(int resid)                               //def
                        .withIcon(getResources().getDrawable(R.mipmap.icon))
                        .withButton1Text("确定")                                      //def gone
                        .withButton2Text("取消")                                  //def gone
                        .setCustomView(view,mItemName.getContext())         //.setCustomView(View or ResId,context)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showCrouton("保存成功");
                                mItemClass.setRightText(mEditText.getText().toString().trim());
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    private void showCrouton(String desc){
        Crouton.makeText(this,desc, Style.ALERT,R.id.child_info_toast).show();
    }


    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;

    private void getIconFromPhoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    private void selectPhoto() {
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");
        showDialog(new SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        mTempUri = PhotoUtil.camera(RegisterActivity2.this);
                        getIconFromCamera();
                        break;
                    case 1:
//                        PhotoUtil.getIconFromPhoto(RegisterActivity2.this);
                        getIconFromPhoto(); // 从系统相册获取
                        break;
                    default:
                        break;
                }
            }
        },list);

    }

    private Uri imgUri; // 由于android手机的图片基本都会很大，所以建议用Uri，而不用Bitmap

    /**
     * 调用系统相机拍照
     */
    private void getIconFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "avatar_"+String.valueOf(System.currentTimeMillis())+".png"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent,PHOTO_PICKED_FROM_CAMERA);
    }

    private SelectDialog showDialog(SelectDialogListener listener, List<String> list){
        SelectDialog dialog = new SelectDialog(this,
                R.style.transparentFrameWindowStyle,listener,list);
        if (canUpdateUI()){
            dialog.show();
        }
        return dialog;
    }


    /**
     * 尝试裁剪图片
     */
    private void doCrop(){
        final ArrayList<CropOption> cropOptions = new ArrayList<>();
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,0);
        int size = list.size();
        if (size == 0){
            UIUtil.showToast(this,"当前不支持裁剪图片!");
            return;
        }
        intent.setData(imgUri);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("scale",true);
        intent.putExtra("return-data",true);

        // only one
        if (size == 1){
            Intent intent1 = new Intent(intent);
            ResolveInfo res = list.get(0);
            intent1.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
            startActivityForResult(intent1,CROP_FROM_CAMERA);
        }else {
            // 很多可支持裁剪的app
            for (ResolveInfo res : list) {
                CropOption co = new CropOption();
                co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                co.appIntent = new Intent(intent);
                co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName,res.activityInfo.name));
                cropOptions.add(co);
            }

            CommonAdapter<CropOption> adapter = new CommonAdapter<CropOption>(this,cropOptions,R.layout.layout_crop_selector) {
                @Override
                public void convert(ViewHolder holder, CropOption item) {
                    holder.setImageDrawable(R.id.iv_icon,item.icon);
                    holder.setText(R.id.tv_name,item.title);
                }
            };

            AlertDialog.Builder builder = new Builder(this);
            builder.setTitle("choose a app");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(cropOptions.get(which).appIntent,CROP_FROM_CAMERA);
                }
            });
            builder.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (imgUri != null){
                        getContentResolver().delete(imgUri,null,null);
                        imgUri = null;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_FROM_CAMERA:
                doCrop();
                break;
            case PHOTO_PICKED_FROM_FILE:
                imgUri = data.getData();
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                if (data != null){
                    setCropImg(data);
                }
                break;
            default:
                break;
        }
    }

    private void setCropImg(Intent picData){
        Bundle bundle = picData.getExtras();
        if (bundle != null){
            Bitmap mBitmap = bundle.getParcelable("data");
            mInfoImage.setHeadImage(mBitmap);
            saveBitmap(Environment.getExternalStorageDirectory() + "/crop_"
                    +System.currentTimeMillis() + ".png",mBitmap);
        }
    }

    private void saveBitmap(String fileName,Bitmap bitmap){
        File file = new File(fileName);
        FileOutputStream fout = null;
        try {
            file.createNewFile();
            fout = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG,100,fout);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fout != null;
                fout.close();
                UIUtil.showToast(ChildInfoActivity.this,"保存成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
