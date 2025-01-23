[![Build](https://github.com/theborakompanioni/bitcoin-fee/actions/workflows/build.yml/badge.svg)](https://github.com/theborakompanioni/bitcoin-fee/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/release/theborakompanioni/bitcoin-fee.svg?maxAge=3600)](https://github.com/theborakompanioni/bitcoin-fee/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.theborakompanioni/bitcoin-fee-starter.svg?maxAge=3600)](https://search.maven.org/#search|g%3A%22io.github.theborakompanioni%22)
[![License](https://img.shields.io/github/license/theborakompanioni/bitcoin-fee.svg?maxAge=2592000)](https://github.com/theborakompanioni/bitcoin-fee/blob/master/LICENSE)


bitcoin-fee
===

A generalized and extensible interface of multiple Bitcoin Fee Recommendation APIs.
The following providers are available out of the box:
- [x] Bitcoin Core JSON-RPC Api (`estimatestmartfee`)
- [x] bitcoiner.live API
- [x] Bitgo API
- [x] Bitcore API
- [x] Blockchain.info API (deprecated - will be removed as it is not compatible with "block target" recommendations)
- [x] Blockchair API
- [x] BlockCypher API
- [x] Blockstream.info API
- [x] mempool.space API


## Table of Contents

- [Install](#install)
- [Example](#example)
- [Development](#development)
- [Contributing](#contributing)
- [Resources](#resources)
- [License](#license)


## Install

[Download](https://search.maven.org/#search|g%3A%22io.github.theborakompanioni%22) from Maven Central.

### Gradle
```groovy
repositories {
    mavenCentral()
}
```

```groovy
implementation "io.github.theborakompanioni:bitcoin-fee-starter:${bitcoinFeeVersion}"
```

### Maven
```xml
<dependency>
    <groupId>io.github.theborakompanioni</groupId>
    <artifactId>bitcoin-fee-starter</artifactId>
    <version>${bitcoinFeeVersion}</version>
</dependency>
```

## Example
Start the example application with
```shell
./gradlew -p bitcoin-fee/bitcoin-fee-example-application bootRun --args="--spring.profiles.active=development --debug"
```

Then visit `http://localhost:8080` in your browser.

## Development

### Requirements
- java >=21

### Build
```shell script
./gradlew build -x test
```
 
### Test
```shell script
./gradlew test integrationTest e2eTest --rerun-tasks
```

### Dependency Verification
Gradle is used for checksum and signature verification of dependencies.

```shell script
# write metadata for dependency verification
./gradlew --write-verification-metadata pgp,sha256 --export-keys
# update buildscript dependency locks
./gradlew dependencies --write-locks
```

See Gradle Userguides [Verifying dependencies](https://docs.gradle.org/current/userguide/dependency_verification.html)
and [Locking dependency versions](https://docs.gradle.org/current/userguide/dependency_locking.html)
for more information.

### Checkstyle
[Checkstyle](https://github.com/checkstyle/checkstyle) with adapted [google_checks](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)
is used for checking Java source code for adherence to a Code Standard.

```shell script
# check for code standard violations with checkstyle
./gradlew checkstyleMain --rerun-tasks
```

### SpotBugs
[SpotBugs](https://spotbugs.github.io/) is used for static code analysis.

```shell script
# invoke static code analysis with spotbugs
./gradlew spotbugsMain --rerun-tasks
```


## Contributing
All contributions and ideas are always welcome. For any question, bug or feature request, 
please create an [issue](https://github.com/theborakompanioni/bitcoin-fee/issues). 
Before you start, please read the [contributing guidelines](contributing.md).


## Resources

- Bitcoin: https://bitcoin.org/en/getting-started
- Spring Boot (GitHub): https://github.com/spring-projects/spring-boot
- Protocol Buffers: https://developers.google.com/protocol-buffers


## License

The project is licensed under the Apache License. See [LICENSE](LICENSE) for details.
