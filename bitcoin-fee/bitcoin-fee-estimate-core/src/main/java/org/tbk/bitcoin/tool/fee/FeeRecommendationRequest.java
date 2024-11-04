package org.tbk.bitcoin.tool.fee;

import org.tbk.bitcoin.tool.fee.util.MoreBitcoin;

import java.time.Duration;
import java.util.Optional;

public interface FeeRecommendationRequest {

    Duration getDurationTarget();

    Optional<Confidence> getDesiredConfidence();

    default long getBlockTarget() {
        return Math.max(1, getDurationTarget().dividedBy(MoreBitcoin.averageBlockDuration()));
    }

    default boolean isNextBlockTarget() {
        return getBlockTarget() == 1;
    }

    default boolean isTargetDurationZeroOrLess() {
        return Duration.ZERO.compareTo(getDurationTarget()) >= 0;
    }

    interface Confidence {
        double getConfidenceValue();
    }
}
