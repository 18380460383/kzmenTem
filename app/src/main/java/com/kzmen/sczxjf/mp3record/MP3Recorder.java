package com.kzmen.sczxjf.mp3record;

/**
 * Created by pjj18 on 2017/8/2.
 */

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>类功能描述：</b><div style="margin-left:40px;margin-top:-10px">
 * MP3实时录制功能,可暂停,注意因踩用Native开发,不能混淆
 */
public class MP3Recorder {
    private String filePath;
    private int sampleRate;
    private boolean isRecording = false;
    private boolean isPause = false;
    private Handler handler;
    public static File FPATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cardholder/");
    public static String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cardholder/";
    /**
     * 开始录音
     */
    public static final int MSG_REC_STARTED = 1;

    /**
     * 结束录音
     */
    public static final int MSG_REC_STOPPED = 2;

    /**
     * 暂停录音
     */
    public static final int MSG_REC_PAUSE = 3;

    /**
     * 继续录音
     */
    public static final int MSG_REC_RESTORE = 4;

    /**
     * 缓冲区挂了,采样率手机不支持
     */
    public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = -1;

    /**
     * 创建文件时扑街了
     */
    public static final int MSG_ERROR_CREATE_FILE = -2;

    /**
     * 初始化录音器时扑街了
     */
    public static final int MSG_ERROR_REC_START = -3;

    /**
     * 录紧音的时候出错
     */
    public static final int MSG_ERROR_AUDIO_RECORD = -4;

    /**
     * 编码时挂了
     */
    public static final int MSG_ERROR_AUDIO_ENCODE = -5;

    /**
     * 写文件时挂了
     */
    public static final int MSG_ERROR_WRITE_FILE = -6;

    /**
     * 没法关闭文件流
     */
    public static final int MSG_ERROR_CLOSE_FILE = -7;
    public String fileType;

    public MP3Recorder(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void setFilePath(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    /**
     * 开片
     */
    public void start() {
        if (isRecording) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                android.os.Process
                        .setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
                // 根据定义好的几个配置，来获取合适的缓冲大小
                final int minBufferSize = AudioRecord.getMinBufferSize(
                        sampleRate, AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                if (minBufferSize < 0) {
                    if (handler != null) {
                        handler.sendEmptyMessage(MSG_ERROR_GET_MIN_BUFFERSIZE);
                    }
                    return;
                }
                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, sampleRate,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);
                // 5秒的缓冲
                short[] buffer = new short[sampleRate * (16 / 8) * 1 * 5];
                byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];

                FileOutputStream output = null;
                try {
                    File file = createSDFile(filePath, fileType);
                    output = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    if (handler != null) {
                        handler.sendEmptyMessage(MSG_ERROR_CREATE_FILE);
                    }
                    return;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                MP3Recorder.init(sampleRate, 1, sampleRate, 32);
                isRecording = true; // 录音状态
                isPause = false; // 录音状态
                try {
                    try {
                        audioRecord.startRecording(); // 开启录音获取音频数据

                        if (mListener != null) {
                            mListener.wellPrepared();
                        }
                    } catch (IllegalStateException e) {
                        // 不给录音...
                        if (handler != null) {
                            handler.sendEmptyMessage(MSG_ERROR_REC_START);
                        }
                        return;
                    }

                    try {
                        // 开始录音
                        if (handler != null) {
                            handler.sendEmptyMessage(MSG_REC_STARTED);
                        }

                        int readSize = 0;
                        boolean pause = false;
                        while (isRecording) {
                            /*--暂停--*/
                            if (isPause) {
                                if (!pause) {
                                    handler.sendEmptyMessage(MSG_REC_PAUSE);
                                    pause = true;
                                }
                                continue;
                            }
                            if (pause) {
                                handler.sendEmptyMessage(MSG_REC_RESTORE);
                                pause = false;
                            }
                            /*--End--*/
                            /*--实时录音写数据--*/
                            readSize = audioRecord.read(buffer, 0,
                                    minBufferSize);
                            voiceLevel = getVoiceSize(readSize, buffer);
                            if (readSize < 0) {
                                if (handler != null) {
                                    handler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
                                }
                                break;
                            } else if (readSize == 0) {
                                ;
                            } else {
                                int encResult = MP3Recorder.encode(buffer,
                                        buffer, readSize, mp3buffer);
                                if (encResult < 0) {
                                    if (handler != null) {
                                        handler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                                    }
                                    break;
                                }
                                if (encResult != 0) {
                                    try {
                                        output.write(mp3buffer, 0, encResult);
                                    } catch (IOException e) {
                                        if (handler != null) {
                                            handler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                        }
                                        break;
                                    }
                                }
                            }
                            /*--End--*/
                        }
                        /*--录音完--*/
                        int flushResult = MP3Recorder.flush(mp3buffer);
                        if (flushResult < 0) {
                            if (handler != null) {
                                handler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                            }
                        }
                        if (flushResult != 0) {
                            try {
                                output.write(mp3buffer, 0, flushResult);
                            } catch (IOException e) {
                                if (handler != null) {
                                    handler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                }
                            }
                        }
                        try {
                            output.close();
                        } catch (IOException e) {
                            if (handler != null) {
                                handler.sendEmptyMessage(MSG_ERROR_CLOSE_FILE);
                            }
                        }
                        /*--End--*/
                    } finally {
                        audioRecord.stop();
                        audioRecord.release();
                    }
                } finally {
                    MP3Recorder.close();
                    isRecording = false;
                }
                if (handler != null) {
                    handler.sendEmptyMessage(MSG_REC_STOPPED);
                }
            }
        }.start();
    }

    public void stop() {
        isRecording = false;
    }

    public void pause() {
        isPause = true;
    }

    public void restore() {
        isPause = false;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public boolean isPaus() {
        if (!isRecording) {
            return false;
        }
        return isPause;
    }

    // 获得声音的level
    public int getVoiceSize(int r, short[] buffer) {
        if (isRecording) {
            try {
                long v = 0;
                // 将 buffer 内容取出，进行平方和运算
                for (int i = 0; i < buffer.length; i++) {
                    v += buffer[i] * buffer[i];
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean = v / (double) r;
                double volume = 10 * Math.log10(mean);
                return (((int) volume / 10) - 1);
            } catch (Exception e) {
                // TODO Auto-generated catch block

            }
        }

        return 1;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File createSDFile(String fileName, String type) throws IOException {
        /*File file = new File(fileName);
        if (!isFileExists(file))
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.createNewFile();
            }*/
        FPATH.mkdirs();//创建文件夹
        File file = new File(LOCAL_PATH, fileName + "." + type);
        file.createNewFile();
        return file;
    }

    private int voiceLevel;

    public int getVoiceLevel() {
        return voiceLevel;
    }

    public interface AudioStageListener {
        void wellPrepared();
    }

    public AudioStageListener mListener;

    public void setOnAudioStageListener(AudioStageListener listener) {
        mListener = listener;
    }

    /**
     * 录音状态管理
     */
    public void setHandle(Handler handler) {
        this.handler = handler;
    }

    /*--以下为Native部分--*/
    static {
        System.loadLibrary("mp3lame");
    }

    /**
     * 初始化录制参数
     */
    public static void init(int inSamplerate, int outChannel,
                            int outSamplerate, int outBitrate) {
        init(inSamplerate, outChannel, outSamplerate, outBitrate, 7);
    }

    /**
     * 初始化录制参数 quality:0=很好很慢 9=很差很快
     */
    public native static void init(int inSamplerate, int outChannel,
                                   int outSamplerate, int outBitrate, int quality);

    /**
     * 音频数据编码(PCM左进,PCM右进,MP3输出)
     */
    public native static int encode(short[] buffer_l, short[] buffer_r,
                                    int samples, byte[] mp3buf);

    /**
     * 据说录完之后要刷干净缓冲区
     */
    public native static int flush(byte[] mp3buf);

    /**
     * 结束编码
     */
    public native static void close();
}