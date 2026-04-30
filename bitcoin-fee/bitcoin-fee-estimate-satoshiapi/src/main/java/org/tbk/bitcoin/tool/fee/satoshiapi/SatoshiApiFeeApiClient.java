package org.tbk.bitcoin.tool.fee.satoshiapi;

import org.tbk.bitcoin.tool.fee.satoshiapi.proto.RecommendedFeesResponse;

public interface SatoshiApiFeeApiClient {
    RecommendedFeesResponse feesRecommended();
}
