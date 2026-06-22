# Homebrew OpenJDK 21 (install: brew install openjdk@21)
BREW_PREFIX := $(shell brew --prefix openjdk@21 2>/dev/null)
ifneq ($(BREW_PREFIX),)
export JAVA_HOME := $(BREW_PREFIX)/libexec/openjdk.jdk/Contents/Home
export PATH := $(JAVA_HOME)/bin:$(PATH)
endif

.DEFAULT_GOAL := build-run

setup:
	cd app &&  ./gradlew wrapper --gradle-version 8.13

clean:
	cd app &&  ./gradlew clean

build:
	cd app &&  ./gradlew clean build

install:
	cd app && ./gradlew  clean installDist --no-daemon

run-dist:
	cd app && ./build/install/app/bin/app
run:
	cd app && ./gradlew run

test:
	cd app && ./gradlew test

report:
	cd app && ./gradlew jacocoTestReport

lint:
	cd app && ./gradlew spotlessApply

update-deps:
	cd app && ./gradlew refreshVersions

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