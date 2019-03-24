package com.socbb.utils;

/**
 * Snowflake java 实现
 * create by socbb on 2019/3/16 10:55.
 */
public class Snowflake {

    private final long twepoch;
    private final long workerIdBits;
    private final long datacenterIdBits;
    private final long maxWorkerId;
    private final long maxDatacenterId;
    private final long sequenceBits;
    private final long workerIdShift;
    private final long datacenterIdShift;
    private final long timestampLeftShift;
    private final long sequenceMask;
    private long workerId;
    private long datacenterId;
    private long sequence;
    private long lastTimestamp;

    public Snowflake(long workerId, long datacenterId) {
        this.twepoch = 1288834974657L;
        this.workerIdBits = 5L;
        this.datacenterIdBits = 5L;
        this.maxWorkerId = 31L;
        this.maxDatacenterId = 31L;
        this.sequenceBits = 12L;
        this.workerIdShift = 12L;
        this.datacenterIdShift = 17L;
        this.timestampLeftShift = 22L;
        this.sequenceMask = 4095L;
        this.sequence = 0L;
        this.lastTimestamp = -1L;
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 31L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException("机器编号应 >=0 && <= 31");
            }
        } else {
            throw new IllegalArgumentException("数据中心编号应 >=0 && <= 31");
        }
    }

    public synchronized long nextId() {
        long timestamp = this.genTime();
        if (timestamp < this.lastTimestamp) {
            throw new IllegalStateException(String.format("时钟向后移动。拒绝在%d毫秒内生成id", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1288834974657L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
        }
    }

    /**
     * 比较当前时间戳和下一个时间戳，如果下一个时间戳等于或小于当前时间戳，则循环获取下个时间戳
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for (timestamp = this.genTime(); timestamp <= lastTimestamp; timestamp = this.genTime()) {
        }
        return timestamp;
    }

    private long genTime() {
        return System.currentTimeMillis();
    }

}
