package com.mohammad.relief.data.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Milestone {

    ZERO("0", "0 Day", 0, "🏅"),
    ONE_DAY("1", "1 Day", 1, "🏅"),
    THREE_DAYS("2", "3 Days", 3, "🏅"),
    ONE_WEEK("3", "1 Week", 7, "⭐"),
    TWO_WEEKS("4", "2 Weeks", 14, "⭐"),
    ONE_MONTH("5", "1 Month", 30, "🏆"),
    TWO_MONTHS("6", "2 Months", 60, "🏆"),
    THREE_MONTHS("7", "3 Months", 90, "👑"),
    SIX_MONTHS("8", "6 Months", 180, "👑"),
    ONE_YEAR("9", "1 Year", 365, "💎");
    private final String id;
    private final String label;
    private final int days;
    private final String icon;


    /**
     * Logic to find the current milestone based on days completed.
     */
    public static Milestone getLatestAchieved(long daysCompleted) {
        Milestone latest = null;
        for (Milestone m : Milestone.values()) {
            if (daysCompleted >= m.days) {
                latest = m;
            } else {
                break;
            }
        }
        
        return latest;
    }
    public static Milestone getNextMilestone(long daysCompleted) {
        for (Milestone m : Milestone.values()) {
            if (m.days > daysCompleted) {
                return m;
            }
        }
        return null; // Already reached the max (1 Year)
    }

}
