### Integration tests for npe-blame-agent

To run the tests
```
mvn test -Djava.version=8 -Dnpeagent=/path/to/[lib]npeblame.[so|dll|dylib]
```

The properties have the following default values and can be omitted if they are acceptable (when both projects are cloned to same top level folder).
```
java.version=11
npeagent=../npe-blame-agent/target/libnpeblame.so (name/ext based on detected OS in maven profile)
```

Note that some tests are expected to fail as a few corner-cases are unimplemented