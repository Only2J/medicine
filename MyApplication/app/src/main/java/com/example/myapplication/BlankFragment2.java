package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BlankFragment2 extends Fragment implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1314;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    String VOICE_NAME="xiaoyan";
    RecyclerView recyclerView;
    Button button;
    Button button1;
    Button button2;
    EditText editText;
    List<Data> list = new ArrayList<>();
    List<String> datas = new ArrayList<>();
    List<String> data = new ArrayList<>();
    MyAdapter myAdapter;
    Spinner spinner;
    ConstraintLayout constraintLayout_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        control_init();//控件初始化




        constraintLayout_info = getActivity().findViewById(R.id.constraintLayout);

//recycleview适配器
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);


        //spinner适配器设置
        MyAdapter_spinner adapter_spinner = new MyAdapter_spinner(getContext());
        spinner.setAdapter(adapter_spinner);
        adapter_spinner.setDatas(data);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VOICE_NAME = datas.get(position);
                final Data data1 = new Data();
                data1.setContent(data.get(position)+"为您服务，请输入您的问题。");
                speekText(data1);
                data1.setType(0);
                list.add(data1);

                if(list.get(list.size()-2).getContent().equals(data1.getContent())){
                    list.remove(list.size()-1);
                }
                myAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(myAdapter.getItemCount()-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        SpeechUtility.createUtility(getContext(), SpeechConstant.APPID + "=5f24232a");//接入appid

        //初始化数据
        final Data data = new Data();
        data.setContent("小燕为您服务，请输入您的问题。");
        speekText(data);
        data.setType(0);
        list.add(data);

        if(list.size()!=1){
            list.remove(list.size()-1);
        }



        //数据更新
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                //获取后台数据
                Data b = new Data();
                b.setType(0);
                b.setContent(msg.getData().getString("data"));
                list.add(b);
                speekText(b);
                myAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(myAdapter.getItemCount()-1);
            }
        };

        //监听事件，数据的发送
        button = getActivity().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入的数据
                Data a = new Data();
                a.setType(1);
                a.setContent(editText.getText() + "");
                list.add(a);
                myAdapter.notifyDataSetChanged();



                //同步数据
                final String url = "https://zyytcm.xyz:8000/answerquestion/?info=" + editText.getText() + "";
                editText.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                            Request request = new Request.Builder()
                                    .url(url)//请求接口。如果需要传参拼接到接口后面。
                                    .build();//创建Request 对象=
                            Response response = null;
                            response = client.newCall(request).execute();//得到Response 对象
                            if (response.isSuccessful()) {
                                Log.d("kwwl", "response.code()==" + response.code());
                                Log.d("kwwl", "response.message()==" + response.message());
                                //Log.d("kwwl","res=="+response.body().string());//获取网页内的主体数据
                                //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。

                                //返回主线程
                                Message message = handler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", response.body().string());
                                message.setData(bundle);
                                handler.sendMessage(message);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }


    private void control_init() {

        if(data.isEmpty()&&datas.isEmpty()) {
            datas.add("xiaoyan");//小燕
            data.add("小燕");
            datas.add("aisjiuxu");//许久
            data.add("许久");
            datas.add("aisxping");//小萍
            data.add("小萍");
            datas.add("aisjinger");//小婧
            data.add("小婧");
            datas.add("aisbabyxu");//小宝
            data.add("小宝");
            datas.add("x2_yifei");//一菲
            data.add("一菲");
        }
        spinner = getActivity().findViewById(R.id.spinner);
        button1 = getActivity().findViewById(R.id.button3);
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        button = getActivity().findViewById(R.id.button2);
        editText = getActivity().findViewById(R.id.editTextTextPersonName2);
        button1.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3://语音识别（把声音转文字）
                startSpeechDialog();
                break;

        }
    }

    private void s_y_select(){

    }



    //recycleview适配器
    class MyAdapter extends RecyclerView.Adapter<MyviewHoder> {
        @NonNull
        @Override
        public MyviewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getContext(), R.layout.item, null);
            MyviewHoder myviewHoder = new MyviewHoder(view);
            return myviewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyviewHoder holder, int position) {
            Data data = list.get(position);
            Log.i("cs",data.getContent()+"   "+data.getType());
            if (data.getType() == 0) {
                holder.textView0.setText(data.getContent());
                holder.constraintLayout.setVisibility(View.VISIBLE);
                holder.constraintLayout1.setVisibility(View.INVISIBLE);
            } else {
                holder.textView1.setText(data.getContent());
                holder.constraintLayout.setVisibility(View.INVISIBLE);
                holder.constraintLayout1.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //recycleview的hoder
    class MyviewHoder extends RecyclerView.ViewHolder {
        TextView textView0;
        TextView textView1;
        LinearLayout constraintLayout;
        LinearLayout constraintLayout1;

        public MyviewHoder(@NonNull View itemView) {
            super(itemView);
            textView0 = itemView.findViewById(R.id.robots);
            textView1 = itemView.findViewById(R.id.people);
            constraintLayout=itemView.findViewById(R.id.constraintLayout4);
            constraintLayout1=itemView.findViewById(R.id.constraintLayout5);
        }
    }


    private void speekText(Data b) {
        //1. 创建 SpeechSynthesizer 对象 , 第二个参数： 本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, VOICE_NAME); // 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "30");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "70");// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant. TTS_AUDIO_PATH, "./sdcard/iflytek.pcm" );
//3.开始合成

        mTts.startSpeaking(b.getContent(), new MySynthesizerListener());

    }

    class MySynthesizerListener implements SynthesizerListener {

        @Override
        public void onSpeakBegin() {
            showTip(" 开始播放 ");
        }

        @Override
        public void onSpeakPaused() {
            showTip(" 暂停播放 ");
        }

        @Override
        public void onSpeakResumed() {
            showTip(" 继续播放 ");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }


        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                showTip("播放完成 ");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    }

    private void startSpeechDialog() {
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(getContext(), new MyInitListener());
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");// 设置中文
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4. 显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
            showTip(result);
            System.out.println(" 没有解析的 :" + result);

            String text = JsonParser.parseIatResult(result);//解析过后的
            System.out.println(" 解析后的 :" + text);

            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
//--------------------------------------------------合并时只需要改下面一行代码---------------------------------------------
            //
            editText.setText(resultBuffer.toString());// 设置输入框的文本------------------------------------

            editText.setSelection(editText.length());//把光标定位末尾
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败 ");
            }

        }
    }

    /**
     * 语音识别
     */
    private void startSpeech() {
        //1. 创建SpeechRecognizer对象，第二个参数： 本地识别时传 InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(getContext(), null); //语音识别器
        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");// 设置普通话
        //3. 开始听写
        mIat.startListening(mRecoListener);
    }


    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        // 听写结果回调接口 (返回Json 格式结果，用户可参见附录 13.1)；
//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见 Demo中JsonParser 类；
//isLast等于true 时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.e(TAG, results.getResultString());
            System.out.println(results.getResultString());
            showTip(results.getResultString());
        }

        // 会话发生错误回调接口
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
            // 获取错误码描述
            Log.e(TAG, "error.getPlainDescription(true)==" + error.getPlainDescription(true));
        }

        // 开始录音
        public void onBeginOfSpeech() {
            showTip(" 开始录音 ");
        }

        //volume 音量值0~30， data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(" 声音改变了 ");
        }

        // 结束录音
        public void onEndOfSpeech() {
            showTip(" 结束录音 ");

        }

        // 扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    private void showTip(String data) {

    }




    public static class MyAdapter_spinner extends BaseAdapter {
        List<String> datas = new ArrayList<>();
        Context mContext;
        public MyAdapter_spinner(Context context) {
            this.mContext = context;
        }

        public void setDatas(List<String> datas) {
            this.datas = datas;
            notifyDataSetChanged();

        }
        @Override
        public int getCount() {
            return datas==null?0:datas.size();
        }
        @Override
        public Object getItem(int position) {
            return datas==null?null:datas.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHodler hodler = null;
            if (convertView == null) {
                hodler = new ViewHodler();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
                hodler.mTextView = (TextView) convertView;
                convertView.setTag(hodler);

            } else {
                hodler = (ViewHodler) convertView.getTag();
            }

            hodler.mTextView.setText(datas.get(position));

            return convertView;

        }



        private static class ViewHodler{

            TextView mTextView;

        }
    }
}