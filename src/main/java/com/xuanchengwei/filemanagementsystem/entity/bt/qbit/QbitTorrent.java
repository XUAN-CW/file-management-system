package com.xuanchengwei.filemanagementsystem.entity.bt.qbit;

import lombok.Data;

@Data
public class QbitTorrent {

    private long added_on;
    private long amount_left;
    private boolean auto_tmm;
    private double availability;
    private String category;
    private long completed;
    private Long completion_on;
    private String content_path;
    private Long dl_limit;
    private Long dlspeed;
    private String download_path;
    private long downloaded;
    private long downloaded_session;
    private long eta;
    private boolean f_l_piece_prio;
    private boolean force_start;
    private String infohash_v1;
    private String infohash_v2;
    private long last_activity;
    private String magnet_uri;
    private Long max_ratio;
    private Long max_seeding_time;
    private String name;
    private Long num_complete;
    private Long num_incomplete;
    private Long num_leechs;
    private Long num_seeds;
    private Long priority;
    private double progress;
    private double ratio;
    private Long ratio_limit;
    private String save_path;
    private Long seeding_time;
    private Long seeding_time_limit;
    private long seen_complete;
    private boolean seq_dl;
    private long size;
    private String state;
    private boolean super_seeding;
    private String tags;
    private long time_active;
    private long total_size;
    private String tracker;
    private Long trackers_count;
    private Long up_limit;
    private long uploaded;
    private long uploaded_session;
    private long upspeed;
}