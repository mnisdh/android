package android.daehoshin.com.androidmemoorm;

import android.daehoshin.com.androidmemoorm.dao.PicNoteDAO;
import android.daehoshin.com.androidmemoorm.model.PicNote;
import android.daehoshin.com.androidmemoorm.util.FileUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class DrawActivity extends AppCompatActivity {
    private PicNoteDAO dao;

    FrameLayout stage;
    DrawView dv;
    SeekBar sbSize;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        init();
    }

    private void init(){
        dao = new PicNoteDAO(this);

        // drawview를 추가할 레이아웃 설정
        stage = (FrameLayout) findViewById(R.id.stage);

        // drawview를 생성해서 레이아웃에 추가
        dv = new DrawView(this);
        stage.addView(dv);

        etTitle = (EditText) findViewById(R.id.etTitle);

        // 사이즈 설정을 위한 seekbar 설정 / 리스너 연결
        sbSize = (SeekBar)findViewById(R.id.sbSize);
        sbSize.setOnSeekBarChangeListener(seekBarChangeListener);

        // 컬러 설정을 위한 라디오그룹 설정 / 리스너 연결
        RadioGroup rgColor =(RadioGroup) findViewById(R.id.rgColor);
        rgColor.setOnCheckedChangeListener(radioGroupCheckedChangeListener);
    }


    /**
     * 그림을 그린 stage를 캡쳐
     *
     * @param view
     */
    public void captureCanvas(View view){
        // 0. 드로잉 캐쉬를 먼저 지워준다
        stage.destroyDrawingCache();

        // 1. 다시 만든다
        stage.buildDrawingCache();

        // 2. 레이아웃에서 그려진 내용을 bitmap 형식으로 가져옴
        Bitmap bitmap = stage.getDrawingCache();

        String fileName = System.currentTimeMillis() + ".jpg";
        // 이미지 파일을 저장하고
        try {
            // /data/data/패키지/files 안에 저장
            FileUtil.write(this, fileName, bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "저장실패", Toast.LENGTH_LONG).show();
        }
        finally {
            // Native에 다 썻다고 알려준다
            bitmap.recycle();
        }

        // 데이터베이스에 경로도 저장
        PicNote picNote = new PicNote();
        picNote.setImagePath(fileName);
        picNote.setTitle(etTitle.getText().toString());
        picNote.setDatetime(System.currentTimeMillis());
        dao.create(picNote);

        //Intent intent = new Intent();
        //intent.putExtra("id", memo.getId());
        //setResult(RESULT_OK, intent);

        finish();
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 사이즈 변경 메소드 호출
            dv.setSize(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            int color = Color.BLACK;
            int size = sbSize.getProgress();

            switch (checkedId){
                case R.id.rbtBlack: color = Color.BLACK; break;
                case R.id.rbtCyan: color = Color.CYAN; break;
                case R.id.rbtMagenta: color = Color.MAGENTA; break;
                case R.id.rbtYellow: color = Color.YELLOW; break;
            }

            // 컬러 변경 메소드 호출
            dv.setColor(color, size);
        }
    };



}
