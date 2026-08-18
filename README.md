[![Release](https://jitpack.io/v/umjammer/vavi-math.svg)](https://jitpack.io/#umjammer/vavi-math)
[![Java CI](https://github.com/umjammer/vavi-math/actions/workflows/maven.yml/badge.svg)](https://github.com/umjammer/vavi-math/actions/workflows/maven.yml)
[![CodeQL](https://github.com/umjammer/vavi-math/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/umjammer/vavi-math/actions/workflows/codeql-analysis.yml)
![Java](https://img.shields.io/badge/Java-17-b07219)

# vavi-math

3.<sup>1<sup>4<sup>1<sup>5<sup>9<sup>2<sup>6<sup>5<sup>3</sup></sup></sup></sup></sup></sup></sup></sup></sup> math library

  * Rational
  * Combination Generator
  * Permutation Generator
  * Reverse Polish Notation
  * Memoization

## Install

* https://jitpack.io/#umjammer/vavi-math

## Usage

### memoization

install jar by pom.xml

```xml
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-install-plugin</artifactId>
        <version>3.1.1</version>
        <executions>
          <execution>
            <id>install-library</id>
            <phase>install</phase>
            <goals>
              <goal>install-file</goal>
            </goals>
            <configuration>
              <groupId>com.github.umjammer.vavi-math</groupId>
              <artifactId>vavi-memoization</artifactId>
              <version>${project.version}</version>
              <packaging>jar</packaging>
              <file>${project.build.directory}/vavi-memoization-${project.version}.jar</file>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

java runtime option

```shell
 $ java -javaagent ${project.build.directory}/vavi-memoization-${project.version}.jar ...
```

## References

 * https://github.com/kimwalisch/primesieve

## TODO

 * ~~make prime generator fast as gnu coreutil factor or python~~
