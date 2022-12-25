
package com.xuanchengwei.filemanagementsystem.entity.bt.qbit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainData {

    private boolean fullUpdate;
    private int rid;
    private ServerState serverState;
    private List<String> tags;
    private Map<String, QbitTorrent> torrents;
}