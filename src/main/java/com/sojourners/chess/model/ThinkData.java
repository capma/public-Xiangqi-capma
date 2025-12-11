package com.sojourners.chess.model;

import com.sojourners.chess.board.ChessBoard;

import java.util.List;

/**
 * 思考细节数据显示
 */
public class ThinkData {

    private Integer depth;

    private Integer score;

    private Integer mate;

    private Long nps;

    private Long time;

    private List<String> detail;

    private String title;

    private String body;

    private Boolean isValid;
    
    private Boolean isFromBackgroundAnalysis;
    
    private Integer displayScore; // Display score after inversion (for UI)

    public ThinkData() {

    }

    public void generate(boolean redGo, boolean isReverse, ChessBoard board) {
        // 生成title
        StringBuilder sb = new StringBuilder();
        // Add background analysis indicator if applicable
        if (isFromBackgroundAnalysis != null && isFromBackgroundAnalysis) {
            sb.append("[Suy nghĩ nền] ");
        }
        // sb.append("深度: ").append(depth).append("  ");
        sb.append("Độ sâu: ").append(depth != null ? depth : "?").append("  ");
        boolean f = false;
        // Calculate display score without mutating original score
        Integer scoreToDisplay = score;
        if (scoreToDisplay == null) {
            // sb.append("绝杀: ");
            sb.append("Chiếu bí: ");
            scoreToDisplay = mate;
            f = true;
        } else {
            // sb.append("分数: ");
            sb.append("Điểm: ");
        }
        // Calculate inverted score for display only (don't mutate original)
        if (scoreToDisplay != null) {
            if (redGo && isReverse || !redGo && !isReverse) {
                displayScore = -scoreToDisplay;
            } else {
                displayScore = scoreToDisplay;
            }
            // sb.append(displayScore).append(f ? "步  " : "  ");
            sb.append(displayScore).append(f ? "nước  " : "  ");
        }
        if (nps != null) {
            sb.append("NPS: ").append(nps / 1000).append("K  ");
        }
        if (time != null) {
            // sb.append("时间: ").append(String.format("%.1fs", time / 1000D));
            sb.append("Thời gian: ").append(String.format("%.1fs", time / 1000D));
        }
        title = sb.toString();
        // 生成body
        body = board.translate(detail);
        // 是否有效（处理分析模式下null数据）
        isValid = !body.contains("null");
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getMate() {
        return mate;
    }

    public void setMate(Integer mate) {
        this.mate = mate;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getNps() {
        return nps;
    }

    public void setNps(Long nps) {
        this.nps = nps;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

    public Boolean getIsFromBackgroundAnalysis() {
        return isFromBackgroundAnalysis;
    }

    public void setIsFromBackgroundAnalysis(Boolean isFromBackgroundAnalysis) {
        this.isFromBackgroundAnalysis = isFromBackgroundAnalysis;
    }
    
    public Integer getDisplayScore() {
        // Return displayScore if available, otherwise return score or mate
        if (displayScore != null) {
            return displayScore;
        }
        if (score != null) {
            return score;
        }
        // If score is null but mate is set, return mate (mate in N moves)
        if (mate != null) {
            return mate;
        }
        return null;
    }
}
