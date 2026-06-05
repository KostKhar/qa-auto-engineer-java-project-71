### Hexlet tests and linter status:
[![hexlet-check](https://github.com/KostKhar/qa-auto-engineer-java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/KostKhar/qa-auto-engineer-java-project-71/actions/workflows/hexlet-check.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cicdpiplinetohell_qa-auto-engineer-java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=cicdpiplinetohell_qa-auto-engineer-java-project-71)


## Утилита `gendiff`:

Консольная утилита `gendiff`. 
Поддержка форматов: `.json` и `.yaml`.

Формат командной строки утилиты:
```java
gendiff [-hV] [-f=<formatName>] <filepath1> <filepath2>
```

Возможны как абсолютные, так и относительные пути к файлам.

```java
-h  --help      вывод справки по утилите
-V --version   версия утилиты
<filepath1>     путь к первому файлу
<filepath2>     путь ко второму файлу
-f, --format    выбор формата вывода информации
```


```bash
  make
```

## Setup

```bash
  make build
```

## Run

```bash
  make run
```

## Run tests

```bash
  make test
```

## Run checkstyle

```bash
  make lint
```

## Check update dependencies and plugins

```bash
  make update
```

```bash
  make stylish-formatter-play
```

```bash
  make json-formatter-play
```

```bash
  make plain-formatter-play
```

```bash
  make yml-formatter-play
```


## asdf

On *nix and macOS to manage Java versions we recommend using asdf https://github.com/asdf-vm/asdf.

[![Hexlet Ltd. logo](https://raw.githubusercontent.com/Hexlet/assets/master/images/hexlet_logo128.png)](https://hexlet.io/?utm_source=github&utm_medium=link&utm_campaign=java-package)

This repository is created and maintained by the team and the community of Hexlet, an educational project. [Read more about Hexlet](https://hexlet.io/?utm_source=github&utm_medium=link&utm_campaign=java-package).

See most active contributors on [hexlet-friends](https://friends.hexlet.io/).