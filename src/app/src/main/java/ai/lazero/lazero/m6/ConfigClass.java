package ai.lazero.lazero.m6;

public class ConfigClass {
    public int rate;
    public short channelConfig;
    public short audioFormat;
    public ConfigClass(int rate, short channelConfig, short audioFormat){
        this.audioFormat = audioFormat;
        this.rate = rate;
        this.channelConfig = channelConfig;
    }
}
