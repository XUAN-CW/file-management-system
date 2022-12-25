
package com.xuanchengwei.filemanagementsystem.entity.bt.qbit;

import lombok.Data;

@Data
public class ServerState {

    private long alltime_dl;
    private long alltime_ul;
    private int average_time_queue;
    private String connection_status;
    private int dht_nodes;
    private long dl_info_data;
    private int dl_info_speed;
    private int dl_rate_limit;
    private long free_space_on_disk;
    private String global_ratio;
    private int queued_io_jobs;
    private boolean queueing;
    private String read_cache_hits;
    private String read_cache_overload;
    private int refresh_interval;
    private int total_buffers_size;
    private int total_peer_connections;
    private int total_queued_size;
    private long total_wasted_session;
    private long up_info_data;
    private int up_info_speed;
    private int up_rate_limit;
    private boolean use_alt_speed_limits;
    private String write_cache_overload;
}