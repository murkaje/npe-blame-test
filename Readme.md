### Integration tests for npe-blame-agent

To run the tests
```
mvn test -Djava.version=8 -Dnpeagent=/path/to/[lib]npeagent.[so|dll|dylib]
```

The default values are  
```
java.version=10
npeagent=../npe-blame-agent/target/libnpeagent.so
```