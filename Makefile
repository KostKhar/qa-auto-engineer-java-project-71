# Homebrew OpenJDK 21 (install: brew install openjdk@21)
BREW_PREFIX := $(shell brew --prefix openjdk@21 2>/dev/null)
ifneq ($(BREW_PREFIX),)
export JAVA_HOME := $(BREW_PREFIX)/libexec/openjdk.jdk/Contents/Home
export PATH := $(JAVA_HOME)/bin:$(PATH)
endif

.DEFAULT_GOAL := build-run

setup:
	./gradlew wrapper --gradle-version 9.2.1

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	app/build/install/qa-auto-engineer-java-project-71.app/bin/qa-auto-engineer-java-project-71.app

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew spotlessApply

update-deps:
	./gradlew refreshVersions

json-formatter-play:
	asciinema play ./app/src/main/resources/jsonDiff.cast

plain-formatter-play:
	asciinema play ./app/src/main/resources/plainDiff.cast

stylish-formatter-play:
	asciinema play ./app/src/main/resources/stylishDiff.cast

yml-formatter-play:
	asciinema play ./app/src/main/resources/ymlDiff.cast


build-run: build run

.PHONY: build