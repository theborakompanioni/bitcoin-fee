
```shell
curl --request GET https://bitcoinsapi.com/api/v1/fees/recommended
```
```json
{
  "data": {
    "recommendation": "Fees are very low. 1.0 sat/vB should confirm within a day.",
    "estimates": {
      "1": 2.248,
      "3": 2.129,
      "6": 1.232,
      "25": 1.0,
      "144": 1.0
    }
  },
  "meta": {
    "timestamp": "2009-01-03T21:21:21.123456+00:00",
    "request_id": null,
    "node_height": 931337,
    "chain": "main",
    "syncing": false,
    "cached": true,
    "cache_age_seconds": 7,
    "max_blocks": null
  }
}
```
