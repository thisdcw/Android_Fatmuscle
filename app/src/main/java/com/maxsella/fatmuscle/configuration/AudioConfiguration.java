package com.maxsella.fatmuscle.configuration;

import android.util.Log;

import com.maxsella.fatmuscle.common.util.LogUtil;

public final class AudioConfiguration {
    public static final int DEFAULT_AAC_PROFILE = 2;
    public static final int DEFAULT_ADTS = 0;
    public static final boolean DEFAULT_AEC = true;
    public static final int DEFAULT_AUDIO_ENCODING = 2;
    public static final int DEFAULT_CHANNEL_COUNT = 2;
    public static final int DEFAULT_FREQUENCY = 44100;
    public static final int DEFAULT_MAX_BPS = 64;
    public static final String DEFAULT_MIME = "audio/mp4a-latm";
    public static final int DEFAULT_MIN_BPS = 32;
    public final int aacProfile;
    public final int adts;
    public final boolean aec;
    public final int channelCount;
    public final int encoding;
    public final int frequency;
    public final int maxBps;
    public final String mime;
    public final int minBps;

    private AudioConfiguration(Builder builder) {
        this.minBps = builder.minBps;
        this.maxBps = builder.maxBps;
        int i = builder.frequency;
        this.frequency = i;
        this.encoding = builder.encoding;
        this.channelCount = builder.channelCount;
        this.adts = builder.adts;
        this.mime = builder.mime;
        this.aacProfile = builder.aacProfile;
        this.aec = builder.aec;
        LogUtil.e( "AudioConfiguration: " + i);
    }

    public static AudioConfiguration createDefault() {
        return new Builder().build();
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private int minBps = 32;
        private int maxBps = 64;
        private int frequency = AudioConfiguration.DEFAULT_FREQUENCY;
        private int encoding = 2;
        private int channelCount = 2;
        private int adts = 0;
        private String mime = AudioConfiguration.DEFAULT_MIME;
        private int aacProfile = 2;
        private boolean aec = true;

        public Builder setBps(int i, int i2) {
            this.minBps = i;
            this.maxBps = i2;
            return this;
        }

        public Builder setFrequency(int i) {
            this.frequency = i;
            return this;
        }

        public Builder setEncoding(int i) {
            this.encoding = i;
            return this;
        }

        public Builder setChannelCount(int i) {
            this.channelCount = i;
            return this;
        }

        public Builder setAdts(int i) {
            this.adts = i;
            return this;
        }

        public Builder setAacProfile(int i) {
            this.aacProfile = i;
            return this;
        }

        public Builder setMime(String str) {
            this.mime = str;
            return this;
        }

        public Builder setAec(boolean z) {
            this.aec = z;
            return this;
        }

        public AudioConfiguration build() {
            return new AudioConfiguration(this);
        }
    }
}
